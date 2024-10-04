package com.example;
import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
//@WebServlet("/create")
public class CreateContactServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	
    	ContactDetailsBean contact=new ContactDetailsBean();
        contact.setContactmail(request.getParameter("email"));
        contact.setContactname(request.getParameter("username"));
        contact.setGender(request.getParameter("gender"));
        contact.setBirthday(request.getParameter("birthday"));
        contact.setPhonenumber(request.getParameter("phone"));
        contact.setLocation(request.getParameter("location"));
        String[] selectedCategories = request.getParameterValues("categories");
        contact.setCategory(Arrays.asList(selectedCategories));
        UserContactDao cd=new UserContactDao((int)session.getAttribute("user_id"));
        try {
              if(cd.contactDetailsRegister(contact))
            {
            	  response.sendRedirect("contacts.jsp");
            }
            else
            {
            	response.sendRedirect("createContact.jsp?error=Didn't create");
            }	
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
