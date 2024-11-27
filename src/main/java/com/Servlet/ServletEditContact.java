package com.Servlet;
import java.io.IOException;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bean.BeanContactDetails;
import com.Dao.DaoRegisterLogin;

/**
 * Servlet that handles the editing of an existing contact's details.
 *
 * @author Sahana
 * @version 1.0
 */
public class ServletEditContact extends HttpServlet {
	 /**
     * Processes the request to update an existing contact based on user 
     * input from the form.
     *
     * <p>This method retrieves updated contact details from the request, 
     * applies the changes, and redirects the user accordingly.</p>
     *
     * @param request  The HttpServletRequest object that contains the request 
     *                 data.
     * @param response The HttpServletResponse object used to send a response 
     *                 to the client.
     * @throws ServletException If an error occurs during the request processing.
     * @throws IOException If an input or output error is detected when the 
     *                     servlet handles the request.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
   	 int contactid=Integer.parseInt(request.getParameter("contactId"));
   	int userid=(int) request.getAttribute("user_id");
    	BeanContactDetails user=new BeanContactDetails();
        user.setName(request.getParameter("username"));
        user.setGender(request.getParameter("gender"));
        user.setBirthday(request.getParameter("birthday"));
        user.setMail(request.getParameter("primaryEmail"));
        user.setPhonenumber(request.getParameter("primaryPhone"));
        user.setContact_id(contactid);
        String[] list=request.getParameterValues("categoryContact");
        if(list!=null && list.length!=0)
        user.setCategory(Arrays.asList(list));
        DaoRegisterLogin rld=new DaoRegisterLogin(userid);
        try {
              if(rld.updateContactDetails(user))
            {
            	response.sendRedirect("contacts.jsp");
            }
            else
            {
            	response.getWriter().println("Updation unsuccessful!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

