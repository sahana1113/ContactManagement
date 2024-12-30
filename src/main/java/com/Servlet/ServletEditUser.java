package com.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bean.BeanMail;
import com.Bean.BeanPhone;
import com.Bean.BeanUserDetails;
import com.Dao.DaoRegisterLogin;
import com.Dao.DaoUserContact;
import com.Session.SessionData;

public class ServletEditUser extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	int userId = (int) request.getAttribute("user_id");

        String action = request.getParameter("action");
        DaoRegisterLogin rld = new DaoRegisterLogin();
        BeanMail mail=new BeanMail();
        BeanPhone phone=new BeanPhone();
        BeanUserDetails obj=new BeanUserDetails(userId);
        mail.setUser_id(userId);
        phone.setUser_id(userId);
        BeanUserDetails userObj=SessionData.getUserData().get(userId);
        boolean check=false;
        String username=request.getParameter("username");
        String gender=request.getParameter("gender");
        String birthday=request.getParameter("birthday");
        try {
            if ("deleteEmail".equals(action)) {
            	mail.setAltMail(request.getParameter("altEmail"));
                rld.deleteAltMail(mail);
                userObj.getAltMail().removeIf(users -> users.getAltMail().equals(mail.getAltMail()));
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("deletePhone".equals(action)) {
            	phone.setAltPhone(request.getParameter("altPhone"));
                rld.deleteAltPhone(phone);
                userObj.getAltPhone().removeIf(phones ->phones.getAltPhone().equals(phone.getAltPhone()));
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("addEmail".equals(action)) {
            	
                mail.setAltMail(request.getParameter("newAltEmail"));
                rld.addAltMail(mail);
                userObj.getAltMail().add(mail);
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("addPhone".equals(action)) {
                phone.setAltPhone(request.getParameter("newAltPhone"));
                rld.addAltPhone(phone); 
                userObj.getAltPhone().add(phone);
                response.sendRedirect("EditDetails.jsp");
                return;
            } else if ("updateUserDetails".equals(action)){
                if(userObj.getUsername()==null || (userObj.getUsername()!=null &&!userObj.getUsername().equals(username))) 
                {
                	obj.setUsername(username);
                	userObj.setUsername(username);
                	check=true;
                }
                if(userObj.getGender()==null || (userObj.getGender()!=null && !userObj.getGender().equals(gender))) {  
                	obj.setGender(gender);
                	userObj.setGender(gender);
                	check=true;
                }
                if(userObj.getBirthday()==null || (userObj.getBirthday()!=null &&!userObj.getBirthday().equals(birthday))) {
                	obj.setBirthday(birthday);
                	userObj.setBirthday(birthday);
                	check=true;
                }
                
                if(!userObj.getUsermail().equals(request.getParameter("primaryEmail")) && request.getParameter("primaryEmail").length()!=0)
        		{
                    obj.setUsermail(request.getParameter("primaryEmail"));
        			if(!rld.updatePrimaryMail(obj))
        			{
        				response.getWriter().println("Update unsuccessful!");
        			}
        		}
        		if(!userObj.getPhonenumber().equals(request.getParameter("primaryPhone")) && request.getParameter("primaryPhone").length()!=0)
        		{
        			obj.setPhonenumber(request.getParameter("primaryPhone"));
        			if(!rld.updatePrimaryPhone(obj))
        			{
        				response.getWriter().println("Update unsuccessful!");
        			}
        		}
                if (check) {
                	if(!rld.updateUserDetails(obj))
                        response.getWriter().println("Update unsuccessful!");
                    }
                }
           SessionData.getDataFromDB(userId);
                response.sendRedirect("home.jsp");          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
