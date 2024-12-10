package com.Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/convertTimeZone")
public class TimeZoneConverterServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String selectedTimeZone = request.getParameter("timeZone");
        if (selectedTimeZone == null || selectedTimeZone.isEmpty()) {
            selectedTimeZone = "Asia/Kolkata"; 
        }
        String contactIdParam = request.getParameter("contactId");
        int contactId = Integer.parseInt(contactIdParam.substring(0,contactIdParam.length()-1));
        request.setAttribute("selectedTimeZone", selectedTimeZone);
    	RequestDispatcher rd=request.getRequestDispatcher("contactDetails.jsp?id="+contactId);  
        rd.forward(request, response);  
    }
}

