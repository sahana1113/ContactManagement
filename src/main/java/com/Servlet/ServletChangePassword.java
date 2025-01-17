package com.Servlet;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bean.BeanAudit;
import com.Bean.BeanMail;
import com.Bean.BeanUserDetails;
import com.Dao.DaoRegisterLogin;
import com.Dao.DaoUserContact;
import com.Query.Enum.Credentials;
import com.Session.SessionData;


public class ServletChangePassword extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
   	 
   	    int userid=(int) request.getAttribute("user_id");
    	BeanUserDetails user=SessionData.getUserData().get(userid);
        String curr=(request.getParameter("currentPassword"));
        String newPass=(request.getParameter("newPassword"));
        String cnfmPass=(request.getParameter("confirmPassword"));
        
        BeanMail mail=new BeanMail();
		mail.setAltMail(user.getUsermail());
		
        DaoRegisterLogin rld=new DaoRegisterLogin();
        BeanUserDetails user1=new BeanUserDetails();
		try {
			user1 = rld.validateLogin(mail, curr);
		} catch (Exception e) {
			e.printStackTrace();
		}
        try {
        	if(user1!=null && user1.getUser_id()>0)
        	{
        		if(newPass.length()!=0 && cnfmPass.length()!=0)
        		{
        			if(newPass.equals(cnfmPass))
        			{
        				user.setPassword(cnfmPass);
        				user.setUpdated_time(System.currentTimeMillis()/1000);
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

