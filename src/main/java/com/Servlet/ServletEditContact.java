package com.Servlet;
import java.io.IOException;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bean.BeanCategory;
import com.Bean.BeanContactDetails;
import com.Dao.DaoRegisterLogin;

public class ServletEditContact extends HttpServlet {
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
        String[] list1=request.getParameterValues("categoryContact");
        if(list1!=null && list1.length!=0) {
    			List<BeanCategory>list=new ArrayList<>();
    			for(String s:list1)
    			{
    				list.add(new BeanCategory(s));
    				System.out.println(s);
    			}
    			user.setCategory(list);
        }
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

