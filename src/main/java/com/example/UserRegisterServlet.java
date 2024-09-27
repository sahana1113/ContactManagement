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
import javax.servlet.annotation.WebServlet;
@WebServlet("/register")
public class UserRegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	UserDetailsBean user=new UserDetailsBean();
        user.setUsermail(request.getParameter("email"));
        user.setUsername(request.getParameter("username"));
        user.setGender(request.getParameter("gender"));
        user.setBirthday(request.getParameter("birthday"));
        user.setPhonenumber(request.getParameter("phone"));
        user.setPassword(request.getParameter("password"));
        RegisterLoginDao rld=new RegisterLoginDao();
        try {
              if(rld.UserDetailsRegister(user))
            {
            	response.sendRedirect("login.jsp");
            }
            else
            {
            	response.getWriter().println("Registration unsuccessful!");
            }
            rld.allMailInsert(user);
            rld.credentialsInsert(user);
            rld.allPhoneInsert(user);
            response.getWriter().println("Registration successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
