package com.Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Bean.*;
import com.Dao.DaoUserContact;

public class ServletCreateContact extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		BeanContactDetails contact = new BeanContactDetails();
		contact.setMail(request.getParameter("email"));
		contact.setName(request.getParameter("username"));
		contact.setGender(request.getParameter("gender"));
		contact.setBirthday(request.getParameter("birthday"));
		contact.setPhonenumber(request.getParameter("phone"));
		contact.setLocation(request.getParameter("location"));
		contact.setCreated_time(System.currentTimeMillis() / 1000);
		contact.setUpdated_time((System.currentTimeMillis() / 1000));
		String[] selectedCategories = request.getParameterValues("categories");
		System.out.println(Arrays.asList(selectedCategories));
		if (selectedCategories != null && selectedCategories.length != 0) {
			List<BeanCategory>list=new ArrayList<>();
			for(String s:selectedCategories)
			{
				list.add(new BeanCategory(s));
			}
			
			contact.setCategory(list);
		}
		DaoUserContact cd = new DaoUserContact((int) request.getAttribute("user_id"));
		try {
			if (cd.contactDetailsRegister(contact)) {
				response.sendRedirect("contacts.jsp");
			} else {
				response.sendRedirect("createContact.jsp?error=Didn't create");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
