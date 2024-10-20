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

/**
 * Servlet that handles user login functionality. It validates user credentials 
 * and creates a session upon successful login.
 *
 * @author Sahana
 * @version 1.0
 */
public class ServletUserLogin extends HttpServlet {
    private static final Logger logger = Logger.getLogger(ServletUserLogin.class.getName()); // Initialize Logger
	/**
     * Processes the login request by validating user credentials. If the credentials 
     * are valid, it creates a session and redirects the user to the home page. 
     * If the login fails, it redirects the user back to the login page.
     *
     * <p>This method retrieves the email and password from the request, 
     * validates them using DaoRegisterLogin, generates a session ID, 
     * and creates a session in the database. If successful, a session cookie 
     * is created and added to the response.</p>
     *
     * @param request  The HttpServletRequest object that contains the 
     *                 request data.
     * @param response The HttpServletResponse object used to send a 
     *                 response to the client.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException If an input or output error is detected while 
     *                     handling the request.
     */
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
	/**
     * Generates a unique session ID using a randomly generated UUID.
     *
     * @return A unique session ID as a String.
     */
	private String generateSessionId() {
        return java.util.UUID.randomUUID().toString(); // Generate a random unique ID
    }
}

