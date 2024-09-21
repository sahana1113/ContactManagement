package com.example;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class UserLogin extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		String useremail=request.getParameter("email");
		String password=request.getParameter("password");
		RegisterLoginDao rld=new RegisterLoginDao();
		try {
			int user_id=rld.validateLogin(useremail, password);
			//response.getWriter().println(user_id);
		if(user_id!=-1)
		{
			HttpSession session = request.getSession();
	        session.setAttribute("user_id", user_id);
	        session.setMaxInactiveInterval(30 * 60);	
			response.sendRedirect("home.jsp");
		}
		else {
            response.sendRedirect("login.jsp?error=Invalid Username or Password");
		}
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		
	}
}

