package controller;

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
    	String UPLOAD_DIR = getServletContext().getRealPath("/compressed");
    	String COMPRESSED_DIR = getServletContext().getRealPath("/uploads");
        try {
            // Step 1: Receive the video file
            Part filePart = request.getPart("videoFile");
            String fileName = filePart.getSubmittedFileName();
            String uploadPath = UPLOAD_DIR + fileName;

            // Step 2: Save uploaded video
            filePart.write(uploadPath);

            // Step 3: Handle compression logic
            String compressedFilePath = COMPRESSED_DIR + "compressed_" + fileName;

            video videoBean= videobo.compressVideo(uploadPath, compressedFilePath);

            // Step 4: Respond back to the client
            response.setContentType("text/html");
            response.getWriter().println("<h2>Video Compression Results</h2>");
            response.getWriter().println("<p><strong>Status:</strong> " + videoBean.getIsDone() + "</p>");
            response.getWriter().println("<p><strong>Original File Path:</strong> " + uploadPath + "</p>");
            response.getWriter().println("<p><strong>Compressed File Path:</strong> " + videoBean.getFileLocation() + "</p>");
            response.getWriter().println("<p><strong>Compressed File Size:</strong> " + videoBean.getFilesize() + " bytes</p>");

        } catch (Exception e) {
            // Handle the exception and send an error response
            try {
                response.setContentType("text/html");
                response.getWriter().println("<h2>Error occurred while compressing the video</h2>");
                response.getWriter().println("<p>Error Message: " + e.getMessage() + "</p>");
            } catch (IOException ioException) {
                ioException.printStackTrace();  // Handle any error while writing the response
            }
            e.printStackTrace();  // Log the error for debugging
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
    	doPost(request,response);
    }

}
