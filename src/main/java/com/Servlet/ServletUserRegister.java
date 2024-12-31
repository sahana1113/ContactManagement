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
        user.setCreated_time(System.currentTimeMillis()/1000);
        user.setUpdated_time(System.currentTimeMillis()/1000);
        BeanMail mail=new BeanMail(request.getParameter("email"));
        BeanPhone phone=new BeanPhone(request.getParameter("phone"));
        mail.setIs_primary(true);
        phone.setIs_primary(true);
        mail.setCreated_time((System.currentTimeMillis() / 1000));
        phone.setCreated_time((System.currentTimeMillis() / 1000));
        mail.setUpdated_time((System.currentTimeMillis() / 1000));
        phone.setUpdated_time((System.currentTimeMillis() / 1000));
        DaoRegisterLogin rld=new DaoRegisterLogin();
        try {
              if(rld.UserDetailsRegister(user))
            {
            	  mail.setUser_id(user.getUser_id());

            	  phone.setUser_id(user.getUser_id());
            	  rld.allMailInsert(mail);
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
