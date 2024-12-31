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
    	BeanContactDetails contact=new BeanContactDetails();
        contact.setName(request.getParameter("username"));
        contact.setGender(request.getParameter("gender"));
        contact.setBirthday(request.getParameter("birthday"));
        contact.setMail(request.getParameter("primaryEmail"));
        contact.setPhonenumber(request.getParameter("primaryPhone"));
        contact.setUpdated_time((System.currentTimeMillis() / 1000));
        contact.setContact_id(contactid);
        String[] list1=request.getParameterValues("categoryContact");
        if(list1!=null && list1.length!=0) {
    			List<BeanCategory>list=new ArrayList<>();
    			for(String s:list1)
    			{
    				list.add(new BeanCategory(s));
    				System.out.println(s);
    			}
    			contact.setCategory(list);
        }
        DaoRegisterLogin rld=new DaoRegisterLogin(userid);
        try {
              if(rld.updateContactDetails(contact))
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

