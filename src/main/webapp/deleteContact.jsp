<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ include file="sessionValidation.jsp" %>
<%@ page import="com.Dao.*" %>
<%@ page import="com.Bean.*" %>
<%

int contactId = Integer.parseInt(request.getParameter("id"));
int uId= (Integer) request.getAttribute("user_id"); 
    DaoRegisterLogin contactDao = new DaoRegisterLogin();

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
