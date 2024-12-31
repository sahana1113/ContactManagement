package com.Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.Bean.*;
import com.Dao.DaoRegisterLogin;

public class ServletCreateCategory extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getAttribute("user_id");
        String categoryName = request.getParameter("categoryName");

        String[] contactIds = request.getParameterValues("contactIds");

        DaoRegisterLogin rld = new DaoRegisterLogin(userId);
		int categoryId=0;
		try {
			BeanCategory obj=new BeanCategory(categoryName);
			obj.setCreated_time(System.currentTimeMillis()/1000);
			obj.setUpdated_time(System.currentTimeMillis()/1000);
			categoryId = rld.insertCategoryByName(obj);
		} catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}

		if (contactIds != null && contactIds.length > 0) {
		    for (String contactId : contactIds) {
		    	System.out.println(contactId);
		        rld.insertCategoryById(Integer.parseInt(contactId),categoryId);
		    }
		    
		}

		response.sendRedirect("categoryDetails.jsp?id="+categoryId);
    }
}

