package server.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import server.Application;
import utils.RandomString;

public class ApiXmlServlet extends HttpServlet
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7398921844101678963L;
    public ApiXmlServlet(){}
    public ApiXmlServlet(String greeting)
    {
    	
    }
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	System.out.println("Api xml servelt");
    	
    	QueryBuilder query = new QueryBuilder(request.getParameterMap());
           
    	Application app = new Application();
        response.setContentType("text/xml");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(app.getResult(query.getQuery(),query.getStyle()));
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
    	System.out.println("Api xml servelt");
    	
    	String from =  request.getParameterMap().get("redirectTo")[0];
    	QueryBuilder query = new QueryBuilder(request.getParameterMap());
           
    	//response.setContentType("text/xml");
        //response.setStatus(HttpServletResponse.SC_OK);
                Application app = new Application();
                if( request.getParameterMap().containsKey("alterTable"))
                {
                	app.alter(query.doAlter());
                }
                else if( request.getParameterMap().containsKey("ToRemove"))
                {
                	app.alter(query.doAlter());
                }
                else
                {
                	app.insert(query.postQuery());
                }
                
                System.out.println("from"+from);
                response.sendRedirect(from+"#!");

           
               
       // response.getWriter().println(app.insert(query.postQuery()));
    }
}