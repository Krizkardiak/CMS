package server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import server.servlet.ApiXmlServlet;


 
public class HelloWorld
{
 
    public static void main(String[] args) throws Exception
    {
        Server server = new Server(8080);
        
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setServer(server);
        
        context.addServlet(new ServletHolder(new FileServlet()),"/*");
       
        context.addServlet(new ServletHolder(new LoginServlet()),"/login/*");
        
        context.addServlet(new ServletHolder(new ApiXmlServlet()),"/api/xml/*");
        server.setHandler(context);
 
        server.start();
        server.join();
    }
}