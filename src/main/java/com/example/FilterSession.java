package com.example;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Dao.DaoSession;

public class FilterSession implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String login = req.getContextPath() + "/login.jsp";
        String loginServlet = req.getContextPath() + "/login";
        String registerServlet = req.getContextPath() + "/register";
        String index = req.getContextPath() + "/index.jsp"; 
        String register = req.getContextPath() + "/register.jsp"; 
        
        Cookie[] cookies = req.getCookies();
        String session_id=null;
        int user_id=0;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("SESSIONID".equals(cookie.getName())) {
                    session_id = cookie.getValue();
                    break;
                }
            }
        }
        DaoSession sessionDAO = DaoSession.getInstance(session_id);

        boolean isValidSession = false;
        if (session_id!=null) {
        	user_id=sessionDAO.validateSession(session_id,cookies);
            if(user_id!=0)
			{
				 isValidSession = true;
			}
        }
      //  boolean loggedIn = (session != null && session.getAttribute("user_id") != null);
        boolean loginReq = req.getRequestURI().equals(login);
        boolean loginSer = req.getRequestURI().equals(loginServlet);
        boolean regSer = req.getRequestURI().equals(registerServlet);
        boolean indexReq = req.getRequestURI().equals(index);
        boolean regReq = req.getRequestURI().equals(register);
        if (isValidSession || loginReq|| indexReq || regReq || loginSer || regSer) {
        	req.setAttribute("user_id", user_id);
            chain.doFilter(request, response); 
        } else {
            res.sendRedirect("login.jsp"); 
        }
    }
    public void destroy() {}
}
