<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
/*
if (session == null || session.getAttribute("user_id") == null) {
    response.sendRedirect("login.jsp?error=SessionExpired");
    return;
}*/
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
/*
response.setHeader("Pragma", "no-cache"); 
response.setDateHeader("Expires", 0);
*/
%>