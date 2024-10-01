package com.example;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/createCategory")
public class CreateCategoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        int userId = (int) session.getAttribute("user_id");
        String categoryName = request.getParameter("categoryName");

        String[] contactIds = request.getParameterValues("contactIds");

        RegisterLoginDao rld = new RegisterLoginDao(userId);
		int categoryId = rld.insertCategoryByName(categoryName);

		if (contactIds != null && contactIds.length > 0) {
		    for (String contactId : contactIds) {
		        rld.insertCategoryById(Integer.parseInt(contactId),categoryId);
		    }
		    
		}

		response.sendRedirect("categoryDetails.jsp?id="+categoryId);
    }
}

