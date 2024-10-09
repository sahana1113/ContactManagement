package com.Servlet;
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

import com.Bean.BeanUserDetails;
import com.Dao.DaoRegisterLogin;

import javax.servlet.annotation.WebServlet;
//@WebServlet("/update")
public class ServletEditUser extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	 HttpSession session = request.getSession(false);
    	int userId = (int) request.getAttribute("user_id");

        String action = request.getParameter("action");
        DaoRegisterLogin rld = new DaoRegisterLogin();
        BeanUserDetails user = new BeanUserDetails();
        user.setUser_id(userId);

        try {
            if ("deleteEmail".equals(action)) {
                user.setAltmail(request.getParameter("altEmail"));
                rld.deleteAltMail(user);
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("deletePhone".equals(action)) {
                user.setAltphone(request.getParameter("altPhone"));
                rld.deleteAltPhone(user);
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("addEmail".equals(action)) {
                user.setAltmail(request.getParameter("newAltEmail"));
                rld.addAltMail(user);
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("addPhone".equals(action)) {
                user.setAltphone(request.getParameter("newAltPhone"));
                rld.addAltPhone(user); 
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("updateUserDetails".equals(action)){
                
                user.setUsername(request.getParameter("username"));
                user.setGender(request.getParameter("gender"));
                user.setBirthday(request.getParameter("birthday"));
                user.setUsermail(request.getParameter("primaryEmail"));
                user.setPhonenumber(request.getParameter("primaryPhone"));
                if(user.getUsermail().length()!=0)
        		{
        			rld.updatePrimaryMail(user);
        		}
        		if(user.getPhonenumber().length()!=0)
        		{
        			rld.updatePrimaryPhone(user);
        		}
                if (rld.updateUserDetails(user)) {
                    response.sendRedirect("home.jsp");
                } else {
                    response.getWriter().println("Update unsuccessful!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}


