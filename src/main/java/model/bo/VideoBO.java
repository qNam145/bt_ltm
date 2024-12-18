package model.bo;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;


import model.bean.video;
import model.dao.VideoDAO;

public class VideoBO {
    private static VideoDAO videodao = new VideoDAO();
    public  video compressVideo(String inputFilePath,String name, String outputFilePath) throws IOException, InterruptedException, IOException {
        video videoBean = new video(name,false,inputFilePath,"",0);
        // FFmpeg command to compress the video
        String[] command = {
                "ffmpeg",
                "-i", inputFilePath+name,     // Input file
                "-vcodec", "libx265",    // Video codec
                "-crf", "28",            // Constant Rate Factor (adjust the value as needed)
                outputFilePath           // Output file
        };

        // Use ProcessBuilder to run the FFmpeg command
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();


        // Read and print the output of the FFmpeg process
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }
        // Wait for the process to complete
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("FFmpeg process failed with exit code " + exitCode);
        }

        //Delete the original file
        File file = new File(inputFilePath+name);
        file.delete();

        videoBean.setIsDone(true);
        videoBean.setFileLocation(outputFilePath);
        videoBean.setFilesize(new File(outputFilePath).length());
        return videoBean;
    }
    public ArrayList<video> getAllVideo(String username) throws IOException , SQLException,ClassNotFoundException {
        return videodao.GetAllVideo(username);
    }
    public void addVideo(video vid,String username) throws ClassNotFoundException, SQLException {
        videodao.AddVideo(vid,username);
    }
    public void updateVideo(video vid,String username) throws ClassNotFoundException, SQLException {
        videodao.UpdateVideo(vid,username);
    }
}
