package com.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bean.BeanMail;
import com.Bean.BeanPhone;
import com.Bean.BeanUserDetails;
import com.Dao.DaoRegisterLogin;
import com.Session.SessionData;

public class ServletPhone extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userId = (int) request.getAttribute("user_id");
        String action = request.getParameter("action");
        String newPhone=request.getParameter("altPhone");
        DaoRegisterLogin rld = new DaoRegisterLogin();
        BeanPhone phone=new BeanPhone();
        phone.setUser_id(userId);
        BeanUserDetails userObj=SessionData.getUserData().get(userId);
        try {
        	if ("deletePhone".equals(action)) {
            	phone.setAltPhone(newPhone);
                rld.deleteAltPhone(phone);
                userObj.getAltPhone().removeIf(phones ->phones.getAltPhone().equals(phone.getAltPhone()));
                response.sendRedirect("EditDetails.jsp");
                return;
            }  else if ("addPhone".equals(action)) {
            	phone.setIs_primary(false);
            	phone.setCreated_time(System.currentTimeMillis() / 1000);
            	phone.setUpdated_time(System.currentTimeMillis() / 1000);
                phone.setAltPhone(request.getParameter("newAltPhone"));
                rld.allPhoneInsert(phone);
                userObj.getAltPhone().add(phone);
                response.sendRedirect("EditDetails.jsp");
                return;
            }  else if("editPhone".equals(action)) {
                int phoneId= Integer.parseInt(request.getParameter("id"));
            	phone.setPhone_id(phoneId);
            	phone.setAltPhone(newPhone);
            	rld.alterPhone(phone);
            	for(BeanPhone obj:userObj.getAltPhone())
            	{
            		if(obj.getPhone_id()==phoneId)
            		{
            			obj.setAltPhone(newPhone);
            		}
            	}
            	response.sendRedirect("myDetails.jsp");
            }        
    } catch (Exception e) {
        e.printStackTrace();
    }
	}

}
