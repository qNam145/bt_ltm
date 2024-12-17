package model.bo;

import java.io.File;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.Frame;

import model.bean.video;

public class VideoBO {
    public static video compressVideo(String inputFilePath, String outputFilePath) {
        FFmpegFrameGrabber frameGrabber = null;
        FFmpegFrameRecorder frameRecorder = null;
        video videoBean = new video();  // Create a video bean to return

        try {
            // Start the frame grabber to read the video
            frameGrabber = new FFmpegFrameGrabber(inputFilePath);
            frameGrabber.start();

            // Setup the frame recorder to compress the video
            frameRecorder = new FFmpegFrameRecorder(outputFilePath, frameGrabber.getImageWidth(), frameGrabber.getImageHeight());
            frameRecorder.setVideoCodec(avcodec.AV_CODEC_ID_H264); // Use H.264 codec for compression
            frameRecorder.setFormat("mp4"); // Output format to MP4
            frameRecorder.setFrameRate(frameGrabber.getFrameRate()); // Set the same frame rate as the input video
            frameRecorder.setVideoBitrate(2000000); // Adjust bitrate for compression

            // Start recording the video with compression
            frameRecorder.start();

            // Process each frame and record it
            Frame frame;
            while ((frame = frameGrabber.grabFrame()) != null) {
                frameRecorder.record(frame);
            }

            // Stop the recorder and grabber after processing
            frameRecorder.stop();
            frameGrabber.stop();

            // Set the properties for the video object
            File inputFile = new File(inputFilePath);
            File outputFile = new File(outputFilePath);
            
            videoBean.setName(inputFile.getName()); // Set video name (input file name)
            videoBean.setFileLocation(outputFilePath); // Set the location of the compressed file
            videoBean.setFilesize(outputFile.length()); // Set the size of the compressed video file
            videoBean.setIsDone(true); // Mark the compression as done
            videoBean.setDesc("Compression successful");

            System.out.println("Video compression completed successfully!");

        } catch (Exception e) {
            // Set failure status on error
            videoBean.setIsDone(false);
            videoBean.setDesc("Error during video compression: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (frameRecorder != null) frameRecorder.close();
                if (frameGrabber != null) frameGrabber.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return videoBean;  // Return the video object with its properties
    }
}
