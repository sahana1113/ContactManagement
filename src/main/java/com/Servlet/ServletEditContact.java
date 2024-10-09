package com.Servlet;
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

import com.Bean.BeanContactDetails;
import com.Dao.DaoRegisterLogin;

import javax.servlet.annotation.WebServlet;
//@WebServlet("/update2")
public class ServletEditContact extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	HttpSession session = request.getSession(false);
   	 int contactid=(int) request.getSession().getAttribute("cont_Id");
   	int userid=(int) request.getAttribute("user_id");
    	BeanContactDetails user=new BeanContactDetails();
        user.setContactname(request.getParameter("username"));
        user.setGender(request.getParameter("gender"));
        user.setBirthday(request.getParameter("birthday"));
        user.setContactmail(request.getParameter("primaryEmail"));
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
