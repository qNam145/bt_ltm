package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.bean.video;
import model.bo.VideoBO;

@WebServlet("/CompressVideo")
@MultipartConfig
public class uploadVideo extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static VideoBO videobo = new VideoBO();


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String UPLOAD_DIR = getServletContext().getRealPath("/uploads/");
        String COMPRESSED_DIR = getServletContext().getRealPath("/compressed/");
        if (!new File(UPLOAD_DIR).exists()) {
            new File(UPLOAD_DIR).mkdir();
        }
        if (!new File(COMPRESSED_DIR).exists()) {
            new File(COMPRESSED_DIR).mkdir();
        }
        try {
            String username = (String) request.getSession().getAttribute("username");
            Part filePart = request.getPart("videoFile");
            String fileName = filePart.getSubmittedFileName();

            filePart.write( UPLOAD_DIR + fileName);

            String compressedFilePath = COMPRESSED_DIR + fileName;

            video vid = new video(fileName,false,UPLOAD_DIR,"File is being processed",filePart.getSize());
            videobo.addVideo(vid,username);

            ProcessVideo process = new ProcessVideo(vid,compressedFilePath,username);
            Thread thread = new Thread(process);
            thread.start();

            response.sendRedirect(request.getContextPath() + "/CompressVideo");

        } catch (Exception e) {
            try {
                response.setContentType("text/html");
                response.getWriter().println("<h2>Error occurred while compressing the video</h2>");
                response.getWriter().println("<p style=\"color:red\">Error Message: " + e.getMessage() + "</p>");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        if(request.getSession().getAttribute("username")!=null){
            try {
                String username = (String) request.getSession().getAttribute("username");
                request.setAttribute("videos", videobo.getAllVideo(username));
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                response.sendRedirect(request.getContextPath() + "/auth");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
class ProcessVideo implements Runnable{
    private video video;
    private String outputFilePath;
    private String username;
    public ProcessVideo (video vid,String outputFilePath,String username){
        this.video=vid;
        this.outputFilePath = outputFilePath;
        this.username = username;
    }
    @Override
    public void run() {
        try {
            VideoBO videobo = new VideoBO();
            videobo.compressVideo(video.getFileLocation(),video.getName(),outputFilePath);
            video.setFileLocation(outputFilePath);
            video.setIsDone(true);
            video.setDesc("File is compressed");
            videobo.updateVideo(video,username);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}