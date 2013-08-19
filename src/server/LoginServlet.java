package server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import database.DerbyDb;

public class LoginServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5730805303907310404L;
	 DerbyDb db;
	

    public void init() {
        DerbyDb db  = DerbyDb.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        System.out.println("username = "+username);
        String password = request.getParameter("password");
        
        if (username.equalsIgnoreCase("admin")) {
            System.out.println("--- MATCH");
        	request.getSession().setAttribute("user", username);
            response.sendRedirect("/pages/index.html");
        } else {
            request.setAttribute("error", "Unknown user, please try again");
            response.sendRedirect("/pages/login.html");
        }
    }

}