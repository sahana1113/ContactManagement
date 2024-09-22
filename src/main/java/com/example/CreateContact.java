package com.example;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateContact extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
    	 if (session == null || session.getAttribute("user_id") == null) {
             response.sendRedirect("login.jsp?error=SessionExpired");
             return;
         }
    	ContactDetailsBean contact=new ContactDetailsBean();
        contact.setContactmail(request.getParameter("email"));
        contact.setContactname(request.getParameter("username"));
        contact.setGender(request.getParameter("gender"));
        contact.setBirthday(request.getParameter("birthday"));
        contact.setPhonenumber(request.getParameter("phone"));
        contact.setLocation(request.getParameter("location"));
        ContactDao cd=new ContactDao((int)session.getAttribute("user_id"));
        try {
              if(cd.contactDetailsRegister(contact))
            {
                  List<ContactDetailsBean> contactList = cd.display();
                  request.setAttribute("contactList", contactList);
                  request.getRequestDispatcher("home.jsp").forward(request, response);
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
