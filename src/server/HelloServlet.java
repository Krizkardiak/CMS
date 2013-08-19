package server;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloServlet extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7398921844101678963L;
	private String greeting="Hello World";
    public HelloServlet(){}
    public HelloServlet(String greeting)
    {
        this.greeting=greeting;
    }
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	System.out.println("Servlet Hello servelt");
    	Application app = new Application();
        response.setContentType("text/xml");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(app.getResult("SELECT num, addr FROM streetaddr ORDER BY num","result"));
        //response.getWriter().println("session=" + request.getSession(true).getId());
    }
}