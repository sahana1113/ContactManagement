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

import com.Dao.DaoSession;
/**
 * A servlet filter that checks for a valid user SESSION before allowing access 
 * to protected resources. If no valid SESSION is found, the user is redirected 
 * to the login page.
 *
 *  @author Sahana
 *  @version 1.0
 */
public class FilterSession implements Filter {
    private static final Logger logger = Logger.getLogger(FilterSession.class);
	 /**
     * Initializes the filter. This method is called once when the filter is 
     * instantiated.
     *
     * @param filterConfig the filter configuration object used by the servlet 
     *                     container to pass information to the filter.
     * @throws ServletException if an error occurs during filter initialization.
     */
    public void init(FilterConfig filterConfig) throws ServletException {}
    /**
     * Checks if a valid SESSION exists for the user. If a valid SESSION is found, 
     * the request is forwarded to the next filter or servlet in the chain. 
     * Otherwise, the user is redirected to the login page.
     *
     * @param request the ServletRequest object that contains the client's request.
     * @param response the ServletResponse object that contains the filter's response.
     * @param chain the filter chain to pass the request and response to the next entity.
     * @throws IOException if an input or output error occurs during the processing of 
     *                     the request.
     * @throws ServletException if the request could not be handled.
     */
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
    /**
     * Destroys the filter. This method is called once when the filter is taken out 
     * of service.
     */
    public void destroy() {}
}
