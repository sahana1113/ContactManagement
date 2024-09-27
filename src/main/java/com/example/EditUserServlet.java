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
import javax.servlet.annotation.WebServlet;
@WebServlet("/update")
public class EditUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
   	int userid=(int) request.getSession().getAttribute("user_id");
    	UserDetailsBean user=new UserDetailsBean();
        user.setUsername(request.getParameter("username"));
        user.setGender(request.getParameter("gender"));
        user.setBirthday(request.getParameter("birthday"));
        user.setAltmail(request.getParameter("altEmail"));
        user.setAltphone(request.getParameter("altPhone"));
        user.setUser_id(userid);
        RegisterLoginDao rld=new RegisterLoginDao();
        try {
              if(rld.updateUserDetails(user))
            {
            	  if(user.getAltmail().length()!=0)
            	  {
            		  rld.addAltMail(user);
            	  }
            	  if(user.getAltphone().length()!=0)
            	  {
            		  rld.addAltPhone(user);
            	  }
            	response.sendRedirect("home.jsp");
            }
            else
            {
            	response.getWriter().println("Updation unsuccessful!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

