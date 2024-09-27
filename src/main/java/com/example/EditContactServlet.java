package com.example;
import java.io.IOException;
import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
@WebServlet("/update2")
public class EditContactServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
   	
   	 String action=request.getParameter("action");
   	 if(action!=null && action.length()!=0)
   	 {
   		session.setAttribute("contact", action);
   		response.sendRedirect("contactDetails.jsp");
   		return;
   	 }
   	 String edit=request.getParameter("edit");
   	 if(edit!=null && edit.length()!=0)
   	 {
   		 session.setAttribute("edit", edit);
   		 response.sendRedirect("editContact.jsp");
   		 return;
   	 }
   	 String delete=request.getParameter("delete");
   	 if(delete!=null &&delete.length()!=0)
   	 {
   		 session.setAttribute("delete", delete);
   		 response.sendRedirect("deleteContact.jsp");
   		 return;
   	 }
   	 int contactid=(int) request.getSession().getAttribute("cont_Id");
   	int userid=(int) request.getSession().getAttribute("user_id");
    	ContactDetailsBean user=new ContactDetailsBean();
        user.setContactname(request.getParameter("username"));
        user.setGender(request.getParameter("gender"));
        user.setBirthday(request.getParameter("birthday"));
        user.setContactmail(request.getParameter("primaryEmail"));
        user.setPhonenumber(request.getParameter("primaryPhone"));
        user.setContact_id(contactid);
        String[] list=request.getParameterValues("categoryContact");
        user.setCategory(Arrays.asList(list));
        RegisterLoginDao rld=new RegisterLoginDao();
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

