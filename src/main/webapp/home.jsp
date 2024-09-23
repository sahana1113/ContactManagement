<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.*" %>
<%    
String userId = request.getParameter("id");
int uId = Integer.parseInt(userId);
UserContactDao cd = new UserContactDao(uId);
String s = cd.getUsername();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #2C3E50;
        }
        h2{
       color: white;
        }
        .container {
            display: flex;
            flex-direction: row-reverse; /* Sidebar moves to the right */
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
            display: flex; /* Enable flexbox for main content */
        }
        .contacts-list, .categories-list {
            width: 50%; /* Each column takes up half the width */
            padding: 20px;
        }
        .contacts-list h3, .categories-list h3 {
            margin-bottom: 20px;
        }
        /* Contact list styling */
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
        .tab {
  display: inline-block;
  margin-left: 400px;
}
        /* Button styles */
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3498DB;
            color: white;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;
        }
        .btn:hover {
            background-color: #2980B9;
        }
        a {
            color: black; /* Link color */
            text-decoration: none; /* Remove underline */
        }
    </style>
</head>
<body>
    <h2>Welcome back <%=s+"!" %></h2>
    <div class="container">

        <div class="sidebar">
            <h3>My Account</h3>
            <ul>
                <li><a href="myDetails.jsp?uid=<%= uId %>">My Details</a></li>
                <li><a href="EditDetails.jsp?uid=<%= uId %>">Edit My Details</a></li>
                <li><a href="prime.jsp?uid=<%= uId %>">Edit Primary Credentials</a></li>
                <li><a href="login.jsp">Logout</a></li>
            </ul>
        </div>

        <div class="main-content">
             <div class="contacts-list">
                <h3>Categories</h3>
                <ul>
                    <li><a href="category.jsp?name=family">Family</a></li>
                    <li><a href="category.jsp?name=work">Work</a></li>
                    <li><a href="category.jsp?name=friends">Friends</a></li>
                    <li><a href="category.jsp?name=favourites">Favourites</a></li>
                    <li><a href="category.jsp?name=archived">Archived</a></li>
                    <li><a href="category.jsp?name=blocked">Blocked</a></li>
                </ul>
                <a href="createNew.jsp" class="btn">Create New</a>
            </div>
            <div class="contacts-list">
                <h3>My Contacts</h3>
                <h3>Contact Name <span class="tab"></span> Phone Number</h3>
                <ul>
                    <%
                    List<ContactDetailsBean> contactList = cd.Contactdisplay();
                    if (contactList != null && !contactList.isEmpty()) {
                        for (ContactDetailsBean contact : contactList) { %>
                        <a href="contactDetails.jsp?id=<%= contact.getContact_id() %>&userId=<%= uId %>">
                        <li>
                            <span><%= contact.getContactname() %></span>
                            <span><%= contact.getPhonenumber() %></span>
                        </li>
                        </a>
                    <%  }
                    } else { %>
                        <li>No contacts available.</li>
                    <% } %>
                </ul>
                <a href="createContact.jsp" class="btn">Create New Contact</a>
            </div>

           
        </div>
    </div>
</body>
</html>
