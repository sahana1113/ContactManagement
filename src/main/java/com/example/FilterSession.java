package com.example;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.log4j.Logger;
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

import com.Bean.BeanUserDetails;
import com.Dao.DaoSession;
import com.Dao.DaoUserContact;
import com.Session.SessionData;

public class FilterSession implements Filter {
    private static final Logger logger = Logger.getLogger(FilterSession.class);
	
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
        	try {
				user_id=sessionDAO.validateSession(session_id,cookies);
			} catch (Exception e) {
				e.printStackTrace();
			}
            if(user_id!=0)
			{
				 isValidSession = true;
			}
        }
        if(session_id!=null && user_id!=0)
        {
        	logger.info(" "+ user_id+" "+session_id+": "+req.getRemoteAddr() + " - - [" + new java.util.Date() + "] \"" +
                    req.getMethod() + " " + req.getRequestURI() + " HTTP/1.1\"");
        }
        else
        {
        	logger.info(req.getRemoteAddr() + " - - [" + new java.util.Date() + "] \"" +
                    req.getMethod() + " " + req.getRequestURI() + " HTTP/1.1\"");
        }
        String requestedUri = req.getRequestURI();
        boolean loginReq = requestedUri.equals(login);
        boolean loginSer = requestedUri.equals(loginServlet);
        boolean regSer = requestedUri.equals(registerServlet);
        boolean indexReq = requestedUri.equals(index);
        boolean regReq = requestedUri.equals(register);
    	req.setAttribute("user_id", user_id);
    	if(isValidSession && SessionData.getUserData().get(user_id)==null)
    	{
    		System.out.print("Added");
    		try {
				SessionData.getDataFromDB(user_id);
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
        if(isValidSession && (loginReq || requestedUri.equals("/")))
        {
        	res.sendRedirect("home.jsp");
            return;
        }
        if (isValidSession || loginReq|| indexReq || regReq || loginSer || regSer) {
            chain.doFilter(request, response); 
        } else {
            res.sendRedirect("login.jsp"); 
        }
    }
    public void destroy() {}
}
