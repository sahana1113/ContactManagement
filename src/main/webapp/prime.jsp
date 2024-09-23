<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change Important Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 60%;
            margin: 50px auto;
            padding: 30px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h2 {
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
            font-size: 16px;
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
            font-size: 14px;
            background-color: #f9f9f9;
        }
        .button {
            margin-top: 20px;
            padding: 12px;
            background-color: #007BFF;
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
            background-color: #0056b3;
        }
        .error {
            color: red;
            text-align: center;
            margin-top: 20px;
        }
        @media (max-width: 768px) {
            .container {
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
    <h2>Change Primary Email, Phone Number, and Password</h2>

    <%
    String userIdParam = request.getParameter("uid");
    int userId = Integer.parseInt(userIdParam);
    UserContactDao contactDao = new UserContactDao();
    UserDetailsBean user = new UserDetailsBean();

    try {
        user = contactDao.getPrimeDetailsById(userId);
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
        <input type="text" id="primaryPhone" name="primaryPhone" value="<%= user.getPhonenumber()  %>" required>
        
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

</body>
</html>
