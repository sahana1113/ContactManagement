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

public class ServletUserRegister extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	BeanUserDetails user=new BeanUserDetails();
        user.setUsermail(request.getParameter("email"));
        user.setUsername(request.getParameter("username"));
        user.setGender(request.getParameter("gender"));
        user.setBirthday(request.getParameter("birthday"));
        user.setPhonenumber(request.getParameter("phone"));
        user.setPassword(request.getParameter("password"));
        BeanMail mail=new BeanMail();
        mail.setAltMail(request.getParameter("email"));
        BeanPhone phone=new BeanPhone();
        phone.setAltPhone(request.getParameter("phone"));
        DaoRegisterLogin rld=new DaoRegisterLogin();
        try {
              if(rld.UserDetailsRegister(user))
            {
            	  rld.allMailInsert(mail);
                  rld.credentialsInsert(user);
                  rld.allPhoneInsert(phone);
                  rld.defaultGroup(user);
            	response.sendRedirect("login.jsp");
            }
            else
            {
            	response.getWriter().println("Registration unsuccessful!");
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
