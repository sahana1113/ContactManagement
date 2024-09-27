package com.example;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SessionFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);
        String login = req.getContextPath() + "/login.jsp";
        String loginServlet = req.getContextPath() + "/login";
        String registerServlet = req.getContextPath() + "/register";
        String index = req.getContextPath() + "/index.jsp"; 
        String register = req.getContextPath() + "/register.jsp"; 
        boolean loggedIn = (session != null && session.getAttribute("user_id") != null);
        boolean loginReq = req.getRequestURI().equals(login);
        boolean loginSer = req.getRequestURI().equals(loginServlet);
        boolean regSer = req.getRequestURI().equals(registerServlet);
        boolean indexReq = req.getRequestURI().equals(index);
        boolean regReq = req.getRequestURI().equals(register);
        if (loggedIn || loginReq|| indexReq || regReq || loginSer || regSer) {
            chain.doFilter(request, response); 
        } else {
            res.sendRedirect("login.jsp"); 
        }
    }
    public void destroy() {}
}