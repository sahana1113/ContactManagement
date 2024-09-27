<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.*" %>
<%@ include file="sessionValidation.jsp" %>

<%

int uId  = (int) session.getAttribute("user_id");
UserContactDao cd = new UserContactDao(uId);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Categories</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .container {
            display: flex;
            height: 100vh;
        }
        .sidebar {
            width: 250px;
            background-color: #2C3E50;
            color: white;
            padding: 20px;
            box-shadow: -2px 0 5px rgba(0, 0, 0, 0.1);
        }
        .sidebar h3 {
            margin-bottom: 20px;
            color: #ECF0F1;
        }
        .sidebar ul {
            list-style-type: none;
            padding-left: 0;
        }
        .sidebar ul li {
            margin-bottom: 20px;
            padding: 10px;
            background-color: #34495E;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .sidebar ul li:hover {
            background-color: #1ABC9C;
        }
        .sidebar ul li a {
            color: white;
            text-decoration: none;
            font-size: 18px;
        }
        .main-content {
            flex-grow: 1;
            background-color: #ECF0F1;
            padding: 20px;
        }
        .contacts-list {
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        .contacts-list h2 {
            margin-bottom: 20px;
        }
        .contacts-list ul {
            list-style-type: none;
            padding: 0;
        }
        .contacts-list ul li {
            background-color: white;
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 10px;
            font-size: 18px;
            border-radius: 5px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .contacts-list ul li:hover {
            background-color: #f9f9f9;
            cursor: pointer;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3498DB;
            color: white;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;
            margin-top: 20px;
        }
        .btn:hover {
            background-color: #2980B9;
        }
        a {
  all: unset; /* remove all styles */
  /* add custom styles here, e.g. */
  color: #333;
  text-decoration: none;
}
    </style>
</head>
<body>
    <div class="container">
        <div class="sidebar">
            <h3>My Account</h3>
            <ul>
                <li><a href="myDetails.jsp">My Details</a></li>
                
                <li><a href="category.jsp">View Categories</a></li>
                <li><a href="contacts.jsp">View Contacts</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>
        
        <div class="main-content">
            
            <div class="contacts-list">
                <h2>Categories</h2>
                <ul>
                    <a href="category.jsp?name=family"><li>Family</li></a>
                    <a href="category.jsp?name=work"><li>Work</li></a>
                    <a href="category.jsp?name=friends"><li>Friends</li></a>
                    <a href="category.jsp?name=favourites"><li>Favourites</li></a>
                    <a href="category.jsp?name=archived"><li>Archived</li></a>
                    <a href="category.jsp?name=blocked"><li>Blocked</li></a>
                </ul>
                <a href="createNew.jsp" class="btn">Create New</a>
            </div>
        </div>
    </div>
</body>
</html>