package com.Servlet;
import org.apache.log4j.Logger;
import java.io.IOException;

import java.time.LocalDateTime;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Dao.DaoRegisterLogin;
import com.Dao.DaoSession;

public class ServletUserLogin extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ServletUserLogin.class.getName()); 
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
			LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(30);
            boolean sessionCreated = sessionDAO.createSession(sessionId, user_id, expiryTime);

            if (sessionCreated) {
            	logger.info("User logged in: user_id=" + user_id);
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
        return java.util.UUID.randomUUID().toString(); 
    }
}

