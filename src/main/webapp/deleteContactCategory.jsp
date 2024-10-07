<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page session="true" %>
<%@ include file="sessionValidation.jsp" %>
<%

int contactId = Integer.parseInt(request.getParameter("contactId"));
int c_id= Integer.parseInt(request.getParameter("category"));
int uId=(Integer) request.getAttribute("user_id"); 
    RegisterLoginDao contactDao = new RegisterLoginDao(uId);

    boolean deletionSuccess = false;
        deletionSuccess = contactDao.deleteContactFromCategory(contactId,c_id); 
     
    if (deletionSuccess) {
        response.sendRedirect("categoryDetails.jsp?id="+c_id);
    } else {
        out.println("<p>Failed to delete contact. Please try again.</p>");
    }
%>