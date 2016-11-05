package com.team.serverdown;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.servlet.RequestDispatcher;

@WebServlet("/atbupload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
		maxFileSize = 1024 * 1024 * 50, // 50 MB
		maxRequestSize = 1024 * 1024 * 100) // 100 MB

public class atbupload extends HttpServlet {

	private static final long serialVersionUID = 205242440643911308L;

	/**
	 * Directory where uploaded files will be saved, its relative to
	 * the web application directory.
	 */
	private static final String UPLOAD_DIR = "uploads";
	private static final DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
		System.out.println(uploadFilePath);
		System.out.println(applicationPath);
		// creates the save directory if it does not exists
		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}
		System.out.println("Upload File Directory=" + fileSaveDir.getAbsolutePath());
		
		if (request.getParameter("btnFileHistory") != null) {

			File[] listOfFiles = fileSaveDir.listFiles();
			String message = "";
			if(listOfFiles.length > 0){
				for (int i = 0; i < listOfFiles.length; i++) {
					if (listOfFiles[i].isFile()) {
						BasicFileAttributes attr = Files.readAttributes(Paths.get(listOfFiles[i].getAbsolutePath()), BasicFileAttributes.class);
						message += "<p>File "+ (i + 1) + ": " + listOfFiles[i].getName() + " Created On: " + df.format(attr.creationTime().toMillis());
					}
				}	
			}
			else {
				message += "No Files On The Server Yet!!!";
			}
			
	        request.setAttribute("message", message);
	        request.getRequestDispatcher("filehistory.jsp").forward(request, response);
		}
		else {
			try {
				String fileName = "";
				// Get all the parts from request and write it to the file on server
				for (Part part : request.getParts()) {
					fileName = getFileName(part);
					String[] parseFileName = fileName.split("\\\\"); // for Windows system to get the filename without the path information
					fileName = parseFileName[parseFileName.length - 1];
					
					boolean fileNameExists = new File(fileSaveDir, fileName).exists();
					if(fileNameExists) {
						int dotPos = fileName.lastIndexOf('.');
						if(dotPos != -1){
							fileName = fileName.substring(0, dotPos) + "-new" + fileName.substring(dotPos);
						} else {
							fileName += "new";
						}
						
					}
					
					part.write(uploadFilePath + File.separator + fileName);

				}

				System.out.println("FileName = " + fileName);
				System.out.println(applicationPath);

				String srcFile = uploadFilePath + File.separator + fileName;
				System.out.println("Just got srcFile from uploadFilePath  + File.separator + fileName; = ");

				System.out.println(srcFile);

				RequestDispatcher rs = request.getRequestDispatcher("confirm.html");
				rs.forward(request, response);
				System.out.println("Sent to the confirm page");

			} catch (Exception e) {
				RequestDispatcher sr = request.getRequestDispatcher("failure.html");
				sr.forward(request, response);
				System.out.println("File Upload Failed");

				e.printStackTrace();
			}
		}
    }
	
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("doget");
		String uid = request.getParameter("uid");
		String lat = request.getParameter("lat");
		String lng = request.getParameter("long");
		System.out.println(uid);
		System.out.println(lat);
		System.out.println(lng);
			
		if(uid == null || lat == null || lng == null){
			RequestDispatcher rs = request.getRequestDispatcher("http_get_info.html");
			rs.forward(request, response);
			System.out.println("Sent to the Information page");
		}
		else {
			try {
				String applicationPath = request.getServletContext().getRealPath("");
				//File outputFile = new File(getServletContext().getRealPath(""));
				String uploadFilePath = applicationPath + File.separator + UPLOAD_DIR;
				File fileSaveDir = new File(uploadFilePath);
				if (!fileSaveDir.exists()) {
					fileSaveDir.mkdirs();
				}
				File outputFile = new File(uploadFilePath + File.separator + "HTTPGET-Inputs.txt");
				outputFile.createNewFile(); // if file already exists will do nothing
				
				FileWriter fout = new FileWriter(outputFile, true); // append
				fout.write("User ID:" + uid + ", " + "Latitude:" + lat + ", " + "Longitute:" + lng + ", Submitted On: "
						+ Calendar.getInstance().getTime() + "\n");
				fout.close();
				
				RequestDispatcher rs = request.getRequestDispatcher("confirm.html");
				rs.forward(request, response);
				System.out.println("Sent to the Information page");
				
				
				System.out.println("Data Submitted Successfully");
			} catch (Exception e) {
				System.out.println("Something Went Wrong While Getting Paramters From User");
			}
		}
	}
	
	

    /**
     * Utility method to get file name from HTTP header content-disposition
     */
	private String getFileName(Part part) {
		System.out.println("*** Debugging: Starting getFileName Function.");
		String contentDisp = part.getHeader("content-disposition");
		System.out.println("content-disposition header= " + contentDisp);
		String[] tokens = contentDisp.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf("=") + 2, token.length() - 1);
			}
		}
		return "";
	}
}


