package com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bean.BeanMail;
import com.Bean.BeanUserDetails;
import com.Dao.DaoRegisterLogin;
import com.Session.SessionData;

public class ServletMail extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = (int) request.getAttribute("user_id");
        String action = request.getParameter("action");
        String newMail=request.getParameter("altEmail");
        DaoRegisterLogin rld = new DaoRegisterLogin();
        BeanMail mail=new BeanMail();
        BeanUserDetails obj=new BeanUserDetails(userId);
        mail.setUser_id(userId);
        BeanUserDetails userObj=SessionData.getUserData().get(userId);
        try {
            if ("deleteEmail".equals(action)) {
            	mail.setAltMail(newMail);
                rld.deleteAltMail(mail);
                userObj.getAltMail().removeIf(users -> users.getAltMail().equals(mail.getAltMail()));
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("addEmail".equals(action)) {
            	mail.setIs_primary(false);
            	mail.setCreated_time(System.currentTimeMillis() / 1000);
            	mail.setUpdated_time(System.currentTimeMillis() / 1000);
                mail.setAltMail(request.getParameter("newAltEmail"));
                rld.allMailInsert(mail);
                userObj.getAltMail().add(mail);
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if("editEmail".equals(action)) {
                int emailId= Integer.parseInt(request.getParameter("id"));
            	mail.setEmail_id(emailId);
            	mail.setAltMail(newMail);
            	rld.alterMail(mail);
            	for(BeanMail mail1:userObj.getAltMail())
            	{
            		if(mail1.getEmail_id()==emailId)
            		{
            			mail1.setAltMail(newMail);
            		}
            	}
            	response.sendRedirect("myDetails.jsp");
            }
    } catch (Exception e) {
        e.printStackTrace();
    }
	}

}
