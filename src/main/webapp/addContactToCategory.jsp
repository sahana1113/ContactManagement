<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="com.Dao.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ include file="sessionValidation.jsp" %>
<%
int contactId = Integer.parseInt(request.getParameter("contactId"));
int c_id= Integer.parseInt(request.getParameter("category"));
int uId= (int) session.getAttribute("user_id");
    DaoRegisterLogin contactDao = new DaoRegisterLogin(uId);

    boolean additionSuccess = false;
        additionSuccess = contactDao.insertCategoryById(contactId,c_id); 
     
    if (additionSuccess) {
        response.sendRedirect("categoryDetails.jsp?id="+c_id);
    } else {
        out.println("<p>Failed to add contact. Please try again.</p>");
    }
%>