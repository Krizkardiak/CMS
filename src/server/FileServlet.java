package server;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;


public class FileServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4318076321320387970L;
	String FileName=null;
	/**
	 * 
	 */
	public FileServlet(){}


	@SuppressWarnings("resource")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		String filePath = request.getRequestURI();

        response.setStatus(HttpServletResponse.SC_OK);

		File root = new File("C:\\Programming\\cms\\test\\");

		File file = new File(root,filePath);
		
		

		//MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
		//String mimeType = mimeTypesMap.getContentType(file);
		///log(mimeType,file.getPath(),filePath);
		
		response.setContentType(getContentType(filePath));
		String result = new Scanner(file).useDelimiter("\\A").next();
		
		
		
		response.getWriter().println(result);
	}
	
	
	private void log(String mimetype, String  filePath, String resquest)
	{
		System.out.println("Servlet - Resource - mime type:"+mimetype+" file path: "+filePath+" uri: "+resquest);
	}
	
	private String getContentType(String fileName) {
        String extension[] = { // File Extensions
            "txt", //0 - plain text
            "htm", //1 - hypertext
            "jpg", //2 - JPEG image
            "png", //2 - png image
            "gif", //3 - gif image
            "pdf", //4 - adobe pdf
            "doc", //5 - Microsoft Word
            "docx",
            "css",//6 - css stylesheet
            "xml",//7 - Xml file
            "xsl",//7 - XSL file
            "js", //8 Javascript
        }; // you can add more
        String mimeType[] = { // mime types
            "text/plain", //0 - plain text
            "text/html", //1 - hypertext
            "image/jpg", //2 - image
            "image/png", //2 - image
            "image/gif", //3 - image
            "application/pdf", //4 - Adobe pdf
            "application/msword", //5 - Microsoft Word
            "application/msword", //5 - Microsoft Word
            "text/css",//6 - css stylesheet
            "text/xml", //7 - Xml file
            "application/xml", //7 - Xml file
            "text/javascript" //javascript
            
        }, // you can add more
                contentType = "text/html";    // default type
        // dot + file extension
        int dotPosition = fileName.lastIndexOf('.');
        // get file extension
        String fileExtension =
                fileName.substring(dotPosition + 1);
        // match mime type to extension
        for (int index = 0; index < mimeType.length; index++) {
            if (fileExtension.equalsIgnoreCase( extension[index])) {
                contentType = mimeType[index];
                break;
            }
        }
        System.out.println(fileName+" "+contentType);
        return contentType;

	}
}





