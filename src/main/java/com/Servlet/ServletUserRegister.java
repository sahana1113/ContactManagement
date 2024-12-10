package com.Servlet;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bean.BeanMail;
import com.Bean.BeanPhone;
import com.Bean.BeanUserDetails;
import com.Dao.DaoRegisterLogin;

/**
 * Servlet that handles user registration functionality. It collects user details
 * from the request and registers the user by storing their information in the database.
 *
 * @author Sahana
 * @version 1.0
 */
public class ServletUserRegister extends HttpServlet {
	 /**
     * Processes the registration request by collecting user details 
     * from the request and registering the user in the database.
     * If registration is successful, the user is redirected to the 
     * login page; otherwise, an error message is displayed.
     *
     * <p>This method retrieves the user's email, username, gender, 
     * birthday, altPhone number, and password from the request, and 
     * attempts to register the user using the DaoRegisterLogin class.</p>
     *
     * @param request  The HttpServletRequest object that contains the 
     *                 request data.
     * @param response The HttpServletResponse object used to send a 
     *                 response to the client.
     * @throws ServletException If an error occurs during request processing.
     * @throws IOException If an input or output error is detected while 
     *                     handling the request.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	BeanUserDetails user=new BeanUserDetails();
        user.setUsermail(request.getParameter("email"));
        user.setUsername(request.getParameter("username"));
        user.setGender(request.getParameter("gender"));
        user.setBirthday(request.getParameter("birthday"));
        user.setPhonenumber(request.getParameter("phone"));
        user.setPassword(request.getParameter("password"));
        BeanMail mail=new BeanMail();
        mail.setAltMail(request.getParameter("email"));
        BeanPhone phone=new BeanPhone();
        phone.setAltPhone(request.getParameter("phone"));
        DaoRegisterLogin rld=new DaoRegisterLogin();
        try {
              if(rld.UserDetailsRegister(user))
            {
            	  rld.allMailInsert(mail);
                  rld.credentialsInsert(user);
                  rld.allPhoneInsert(phone);
                  rld.defaultGroup(user);
            	response.sendRedirect("login.jsp");
            }
            else
            {
            	response.getWriter().println("Registration unsuccessful!");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
