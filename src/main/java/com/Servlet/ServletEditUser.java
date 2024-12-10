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
 * Servlet that handles editing user details, including adding, 
 * deleting, and updating contact information.
 *
 * @author Sahana
 * @version 1.0
 */
public class ServletEditUser extends HttpServlet {
	 /**
     * Processes the request to edit user details based on user input 
     * from the form.
     *
     * <p>This method can handle various actions such as adding or 
     * deleting email and altPhone numbers, as well as updating primary 
     * user information. It redirects the user to the appropriate page 
     * based on the action performed.</p>
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
    	int userId = (int) request.getAttribute("user_id");

        String action = request.getParameter("action");
        DaoRegisterLogin rld = new DaoRegisterLogin();
        BeanUserDetails user=new BeanUserDetails();
        user.setUser_id(userId);
        BeanMail mail=new BeanMail();
        BeanPhone phone=new BeanPhone();
        mail.setUser_id(userId);
        phone.setUser_id(userId);

        try {
            if ("deleteEmail".equals(action)) {
            	mail.setAltMail(request.getParameter("altEmail"));
                rld.deleteAltMail(mail);
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("deletePhone".equals(action)) {
            	phone.setAltPhone(request.getParameter("altPhone"));
                rld.deleteAltPhone(phone);
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("addEmail".equals(action)) {
            	
                mail.setAltMail(request.getParameter("newAltEmail"));
                rld.addAltMail(mail);
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("addPhone".equals(action)) {
                phone.setAltPhone(request.getParameter("newAltPhone"));
                rld.addAltPhone(phone); 
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("updateUserDetails".equals(action)){
                
                user.setUsername(request.getParameter("username"));
                user.setGender(request.getParameter("gender"));
                user.setBirthday(request.getParameter("birthday"));
                user.setUsermail(request.getParameter("primaryEmail"));
                user.setPhonenumber(request.getParameter("primaryPhone"));
                if(user.getUsermail().length()!=0)
        		{
        			rld.updatePrimaryMail(user);
        		}
        		if(user.getPhonenumber().length()!=0)
        		{
        			rld.updatePrimaryPhone(user);
        		}
                if (rld.updateUserDetails(user)) {
                    response.sendRedirect("home.jsp");
                } else {
                    response.getWriter().println("Update unsuccessful!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}


