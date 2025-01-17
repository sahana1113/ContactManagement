package com.Servlet;

import java.io.IOException;
import java.util.Iterator;

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
        BeanUserDetails userObj = null;
        BeanMail auditMail = null;
        userObj=SessionData.getUserData().get(userId);
    
        try {
            if ("deleteEmail".equals(action)) {
            	auditMail=BeanMail.findByEmail(userObj.getAltMail(), newMail);
                rld.deleteAltMail(auditMail);
                userObj.getAltMail().removeIf(users -> users.getAltMail().equals(newMail));
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("addEmail".equals(action)) {
            	mail.setIs_primary(false);
            	mail.setCreated_time(System.currentTimeMillis() / 1000);
            	mail.setUpdated_time(System.currentTimeMillis() / 1000);
                mail.setAltMail(request.getParameter("newAltEmail"));
                int k=rld.allMailInsert(mail);
                mail.setEmail_id(k);
                userObj.getAltMail().add(mail);
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if("editEmail".equals(action)) {
                int emailId= Integer.parseInt(request.getParameter("id"));
            	auditMail=BeanMail.findByEmailId(userObj.getAltMail(), emailId);

            	mail.setEmail_id(emailId);
            	mail.setAltMail(newMail);
            	mail.setUpdated_time(System.currentTimeMillis()/1000);
            	SessionData.addAuditCache("all_mail"+emailId, auditMail);
            	rld.alterMail(mail);
            	SessionData.getAuditCache().remove("all_mail"+emailId);
            	Iterator<BeanMail> iterator = userObj.getAltMail().iterator();
            	while (iterator.hasNext()) {
            	    BeanMail mail1 = iterator.next();
            	    if (mail1.getEmail_id()==emailId) {
            	        iterator.remove();  
            	        userObj.getAltMail().add(mail); 
            	        break;  
            	    }
            	}
            	response.sendRedirect("myDetails.jsp");
            }
    } catch (Exception e) {
        e.printStackTrace();
    }
	}

}
