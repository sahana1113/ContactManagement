package com.Servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bean.BeanUserDetails;
import com.Dao.DaoRegisterLogin;
import com.Dao.DaoUserContact;


public class ServletChangePassword extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
   	 
   	int userid=(int) request.getAttribute("user_id");
    	BeanUserDetails user=new BeanUserDetails();
        String curr=(request.getParameter("currentPassword"));
        user.setUsermail(request.getParameter("primaryEmail"));
        user.setPhonenumber(request.getParameter("primaryPhone"));
        String newPass=(request.getParameter("newPassword"));
        String cnfmPass=(request.getParameter("confirmPassword"));
        user.setUser_id(userid);
        
        DaoRegisterLogin rld=new DaoRegisterLogin();
        DaoUserContact cd=new DaoUserContact(userid);
        try {
        	if(rld.validateLogin(cd.getUsermail(), curr)>0)
        	{
        		if(newPass.length()!=0 && cnfmPass.length()!=0)
        		{
        			if(newPass.equals(cnfmPass))
        			{
        				user.setPassword(cnfmPass);
        				rld.changePassword(user);
        			}
        			else
            		{
            			response.getWriter().println("Both passwords do not match!!!");
            		}
        		}
        		
        		response.sendRedirect("home.jsp");
        		
        	}
        	else
        	{
        		response.getWriter().println("Current passwords do not match!!!");
        	}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

