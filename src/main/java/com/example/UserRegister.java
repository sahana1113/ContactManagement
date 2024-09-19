package com.example;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserRegister extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usermail = request.getParameter("email");
        String username = request.getParameter("username");
        String gender=request.getParameter("gender");
        String birthday=request.getParameter("birthday");
        int phonenumber=Integer.parseInt(request.getParameter("phone"));
        String password = request.getParameter("password");

        try {
          //  Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ContactManagement", "root", "root");
            PreparedStatement pst = con.prepareStatement("INSERT INTO userDetails (username,usermail,gender,phonenumber,birthday) VALUES (?, ?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, username);
            pst.setString(2, usermail);
            pst.setString(3, gender);
            pst.setInt(4,phonenumber);      
            pst.setString(5, birthday);
            pst.executeUpdate();
            ResultSet key=pst.getGeneratedKeys();
            int userid=0;
            if(key.next())
            {
            	userid=key.getInt(1);
            }
            PreparedStatement pst2 = con.prepareStatement("INSERT INTO credentials(user_id,password) values(?,?)");
            pst2.setInt(1,userid);
            pst2.setString(2, password);
            pst2.executeUpdate();
            PreparedStatement pst3 = con.prepareStatement("INSERT INTO all_mail(user_id,user_email,is_primary) values(?,?,true)");
            pst3.setInt(1, userid);
            pst3.setString(2, usermail);
            pst3.executeUpdate();
            response.getWriter().println("Registration successful!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
