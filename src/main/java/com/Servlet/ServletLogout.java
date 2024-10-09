package com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Dao.DaoSession;

import javax.servlet.annotation.WebServlet;
//@WebServlet("/logout")
public class ServletLogout extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        DaoSession ses=new DaoSession();
        Cookie[] cookies = request.getCookies();
        String session_id=null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSIONID".equals(cookie.getName())) {
                    session_id = cookie.getValue();
                    cookie.setMaxAge(0); 
                    cookie.setPath("/");  
                    response.addCookie(cookie); 
                    break;
                }
            }
        }
        if(request.getAttribute("user_id")!=null) {
        	ses.invalidateSession(session_id);
        }
        response.sendRedirect("login.jsp");
    }
    
}

