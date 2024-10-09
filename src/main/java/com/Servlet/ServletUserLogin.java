package com.Servlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Dao.DaoRegisterLogin;
import com.Dao.DaoSession;

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
//@WebServlet("/login")
public class ServletUserLogin extends HttpServlet {
	protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		String useremail=request.getParameter("email");
		String password=request.getParameter("password");
		DaoSession sessionDAO = new DaoSession();
		DaoRegisterLogin rld=new DaoRegisterLogin();
		try {
			int user_id=rld.validateLogin(useremail, password);
		if(user_id!=-1)
		{
			String sessionId = generateSessionId();
			LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(3);
            boolean sessionCreated = sessionDAO.createSession(sessionId, user_id, expiryTime);

            if (sessionCreated) {
                Cookie sessionCookie1 = new Cookie("SESSIONID", sessionId);
                sessionCookie1.setMaxAge(60 * 60 * 24);
                sessionCookie1.setHttpOnly(true); 
                response.addCookie(sessionCookie1);
                response.sendRedirect("home.jsp");
            } else {
                response.sendRedirect("login.jsp?error=Session Creation Failed");
            }
		}
		else {
            response.sendRedirect("login.jsp");
		}
		}
		catch (Exception e) {
            e.printStackTrace();
        }
		
	}
	private String generateSessionId() {
        return java.util.UUID.randomUUID().toString(); // Generate a random unique ID
    }
}

