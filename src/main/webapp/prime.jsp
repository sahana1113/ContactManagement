<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.sql.SQLException" %>
<%@ page session="true" %>
<%@ include file="sessionValidation.jsp" %>
<%

response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Important Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ECF0F1;
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
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .edit-container {
            margin-bottom: 110px;
            width: 60%;
            padding: 30px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .edit-container h2 {
            text-align: center;
            color: #333;
            font-size: 24px;
            margin-bottom: 20px;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin-top: 15px;
            font-size: 20px;
            font-weight: bold;
            color: #333;
        }
        input[type="text"], input[type="email"], input[type="password"] {
            padding: 10px;
            width: 100%;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 17px;
            background-color: #f9f9f9;
        }
        .button {
            margin-top: 20px;
            padding: 12px;
            background-color: #3498DB;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            width: 150px;
            align-self: center;
            transition: background-color 0.3s ease;
        }
        .button:hover {
            background-color: #2980B9;
        }
        .error {
            color: red;
            text-align: center;
            margin-top: 20px;
        }
        @media (max-width: 768px) {
            .edit-container {
                width: 90%;
                padding: 20px;
            }
            .button {
                width: 100%;
            }
        }
    </style>
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

    <!-- Main Content -->
    <div class="main-content">
        <div class="edit-container">
            <h2>Change Primary Email, Phone Number, and Password</h2>

            <%
            int user_Id = (int) session.getAttribute("user_id");
            UserContactDao contactDao = new UserContactDao();
            UserDetailsBean user = new UserDetailsBean();

            try {
                user = contactDao.getPrimeDetailsById(user_Id);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (user != null) {
            %>
            <form action="prime" method="post">
                <label for="currentPassword">Current Password:</label>
                <input type="password" id="currentPassword" name="currentPassword" required>

                <label for="primaryEmail">Primary Email:</label>
                <input type="email" id="primaryEmail" name="primaryEmail" value="<%= user.getUsermail() %>" required>

                <label for="primaryPhone">Primary Phone Number:</label>
                <input type="text" id="primaryPhone" name="primaryPhone" value="<%= user.getPhonenumber() %>" required>

                <label for="newPassword">New Password:</label>
                <input type="password" id="newPassword" name="newPassword">

                <label for="confirmPassword">Confirm New Password:</label>
                <input type="password" id="confirmPassword" name="confirmPassword">

                <input type="submit" class="button" value="Save Changes">
            </form>

            <% } else { %>
                <p class="error">No user details found. Please log in.</p>
            <% } %>
        </div>
    </div>

</div>

</body>
</html>
