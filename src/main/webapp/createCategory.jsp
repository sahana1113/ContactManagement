<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.example.*" %>
<%@ include file="sessionValidation.jsp" %>
<%@ page import="com.Dao.*" %>
<%@ page import="com.Bean.*" %>
<%@ page session="false" %>

<%
int uId  = (Integer) request.getAttribute("user_id"); 
DaoUserContact cd = new DaoUserContact(uId);
List<BeanContactDetails> allContacts = cd.Contactdisplay();
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create New Category</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            display: flex;
            min-height: 100vh;
        }
        .sidebar {
            width: 250px;
            background-color: #2C3E50;
            color: white;
            padding: 20px;
            box-shadow: -2px 0 5px rgba(0, 0, 0, 0.1);
            position: fixed;
    		height:100vh;
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
            margin-left:280px;
            flex-grow: 1;
            background-color: #ECF0F1;
            padding: 20px;
        }
        .contacts-list {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
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
        }
        .btn {
            padding: 10px 20px;
            background-color: #3498DB;
            color: white;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;
            cursor: pointer;
            font-size:17px;
        }
        .btn:hover {
            background-color: #2980B9;
        }
        .add-container {
            display: flex;
            align-items: center;
        }
        .add-container input[type="checkbox"] {
            margin-right: 10px;
        }
        #categoryName {
            width: 50%; 
            padding: 10px; 
            font-size: 16px; 
            margin-bottom: 20px; 
            
        }
        h3{
        font-size:20px;
        }
    </style>

    <script>
        function toggleCheckbox(contactId) {
            var checkbox = document.getElementById("contact-" + contactId);
            checkbox.checked = !checkbox.checked;
        }
    </script>

</head>
<body>
    <div class="container">
        <!-- Sidebar -->
        <div class="sidebar">
            <h2>My Account</h2>
            <ul>
                <li><a href="myDetails.jsp">My Details</a></li>
                <li><a href="category.jsp">View Categories</a></li>
                <li><a href="contacts.jsp">View Contacts</a></li>
                <li><a href="logout">Logout</a></li>
            </ul>
        </div>

        <div class="main-content">
            <h2>Create New Category</h2>
            <form action="createCategory" method="POST">
                <label for="categoryName"><h3>Category Name:</h3></label>
                <input type="text" id="categoryName" name="categoryName" required>
                <h3>Add Contacts to Category</h3>
                <div class="contacts-list">
                    <ul>
                        <%
                        for (BeanContactDetails contact : allContacts) {
                        %>
                            <li>
                                <span><%=contact.getName()%> - <%= contact.getPhonenumber() %></span>
                                <div class="add-container">
                                    <input type="checkbox" id="contact-<%= contact.getContact_id() %>" name="contactIds" value="<%= contact.getContact_id() %>">
                                    <button type="button" class="btn" onclick="toggleCheckbox('<%= contact.getContact_id() %>')">Add</button>
                                </div>
                            </li>
                        <% } %>
                    </ul>
                </div>
                <button type="submit" class="btn">Create Category</button>
                

               
            </form>
        </div>
    </div>
</body>
</html>
