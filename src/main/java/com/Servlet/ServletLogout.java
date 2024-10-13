package com.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Dao.DaoSession;

/**
 * Servlet that handles user logout by invalidating the user's session 
 * and clearing the session cookie.
 *
 * @author Sahana
 * @version 1.0
 */
public class ServletLogout extends HttpServlet {
    /**
     * Processes the logout request by invalidating the user session 
     * and removing the session cookie. The user is then redirected 
     * to the login page.
     *
     * <p>This method checks for the SESSIONID cookie, invalidates the 
     * corresponding session if the user is logged in, and sets the 
     * cookie's maximum age to zero to delete it.</p>
     *
     * @param request  The HttpServletRequest object that contains the 
     *                 request data.
     * @param response The HttpServletResponse object used to send a 
     *                 response to the client.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException If an input or output error is detected while 
     *                     handling the request.
     */
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

