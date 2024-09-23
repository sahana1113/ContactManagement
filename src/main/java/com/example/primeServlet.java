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

public class primeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
   	 if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp?error=SessionExpired");
            return;
        }
   	int userid=(int) request.getSession().getAttribute("user_id");
    	UserDetailsBean user=new UserDetailsBean();
        String curr=(request.getParameter("currentPassword"));
        user.setUsermail(request.getParameter("primaryEmail"));
        user.setPhonenumber(request.getParameter("primaryPhone"));
        String newPass=(request.getParameter("newPassword"));
        String cnfmPass=(request.getParameter("confirmPassword"));
        user.setUser_id(userid);
        
        RegisterLoginDao rld=new RegisterLoginDao();
        UserContactDao cd=new UserContactDao(userid);
        try {
        	if(rld.validateLogin(cd.getUsermail(), curr)>0)
        	{
        		if(newPass.length()!=0 && cnfmPass.length()!=0)
        		{
        			if(newPass.equals(cnfmPass))
        			{
        				user.setPassword(cnfmPass);
        				rld.changePassword(user);
        			}
        			else
            		{
            			response.getWriter().println("Both passwords do not match!!!");
            		}
        		}
        		if(user.getUsermail().length()!=0)
        		{
        			rld.updatePrimaryMail(user);
        		}
        		if(user.getPhonenumber().length()!=0)
        		{
        			rld.updatePrimaryPhone(user);
        		}
        		response.sendRedirect("home.jsp?id="+ userid);
        		
        	}
        	else
        	{
        		response.getWriter().println("Current passwords do not match!!!");
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

