package com.Servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.Bean.*;
import com.Dao.DaoRegisterLogin;
/**
 * Servlet that handles the creation of new CATEGORIES for a user.
 *
 * @author Sahana
 * @version 1.0
 */
public class ServletCreateCategory extends HttpServlet {
	  /**
     * Processes the request to create a new category and associates 
     * selected contacts with that category. 
     *
     * @param request  The HttpServletRequest object that contains the request 
     *                 data.
     * @param response The HttpServletResponse object used to send a response 
     *                 to the client.
     * @throws ServletException If an error occurs during the request processing.
     * @throws IOException If an input or output error is detected when the 
     *                     servlet handles the request.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = (int) request.getAttribute("user_id");
        String categoryName = request.getParameter("categoryName");

        String[] contactIds = request.getParameterValues("contactIds");

        DaoRegisterLogin rld = new DaoRegisterLogin(userId);
		int categoryId=0;
		try {
			categoryId = rld.insertCategoryByName(new BeanCategory(categoryName));
		} catch (NoSuchFieldException | IllegalAccessException | SQLException e) {
			e.printStackTrace();
		}

		if (contactIds != null && contactIds.length > 0) {
		    for (String contactId : contactIds) {
		        rld.insertCategoryById(Integer.parseInt(contactId),categoryId);
		    }
		    
		}

		response.sendRedirect("categoryDetails.jsp?id="+categoryId);
    }
}

