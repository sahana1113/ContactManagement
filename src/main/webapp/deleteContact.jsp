<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ include file="sessionValidation.jsp" %>
<%

int contactId = Integer.parseInt(String.valueOf(session.getAttribute("contact")));
int uId= (int) session.getAttribute("user_id");
    RegisterLoginDao contactDao = new RegisterLoginDao();

    boolean deletionSuccess = false;

    try {
        deletionSuccess = contactDao.deleteContactById(contactId); 
    } catch (SQLException e) {
        e.printStackTrace();
    }
    if (deletionSuccess) {
        response.sendRedirect("contacts.jsp");
    } else {
        out.println("<p>Failed to delete contact. Please try again.</p>");
    }
%>
