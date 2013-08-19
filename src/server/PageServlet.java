package server;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

public class PageServlet extends HttpServlet
{
    /**
	 * 
	 */
	private File root=new File("C:\\Programming\\cms\\test\\");
	private static final long serialVersionUID = 7398921844101678963L;
	private String greeting="Hello World";
    public PageServlet(){}
   
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	System.out.println("Servlet Page");
        response.setStatus(HttpServletResponse.SC_OK);
        String filePath = request.getRequestURI();
		
		System.out.println("Page Servlet with  path :"+filePath);
        String result = FileUtils.readFileToString(new File(root,request.getRequestURI()));
        response.getWriter().println(result);
       // response.getWriter().println("session=" + request.getSession(true).getId());
    }
}