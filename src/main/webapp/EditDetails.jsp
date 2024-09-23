<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User Details</title>
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
        input[type="text"], input[type="email"], input[type="date"], select {
            padding: 10px;
            width: 100%;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
            background-color: #f9f9f9;
        }
        input[readonly] {
            background-color: #e9e9e9;
            cursor: not-allowed;
        }
        .button {
            margin-top: 30px;
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
    <h2>Edit User Details</h2>

    <%
    String userIdParam = request.getParameter("uid");
        int userId = Integer.parseInt(userIdParam);
        UserContactDao contactDao = new UserContactDao();
        UserDetailsBean user = new UserDetailsBean();

        try {
            user = contactDao.getUserDetailsById(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
            
            if (user != null) {
    %>

    <form action="update" method="post">
        <!-- Username -->
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required>
        
        <!-- Gender -->
        <!-- Gender -->
<label for="gender">Gender:</label>
<select id="gender" name="gender" required>
    <option value="male" <%= (user.getGender() != null && user.getGender().equals("male")) ? "selected" : "" %>>Male</option>
    <option value="female" <%= (user.getGender() != null && user.getGender().equals("female")) ? "selected" : "" %>>Female</option>
    <option value="other" <%= (user.getGender() != null && user.getGender().equals("other")) ? "selected" : "" %>>Other</option>
</select>

        
        <!-- Birthday -->
        <label for="birthday">Birthday:</label>
        <input type="date" id="birthday" name="birthday" value="<%= user.getBirthday() %>" required>

        <!-- Primary Email (non-editable) -->
        <label for="primaryEmail">Primary Email:</label>
        <input type="email" id="primaryEmail" name="primaryEmail" value="<%= user.getUsermail() %>" readonly>

        <!-- Alternate Email -->
        <label for="altEmail">Alternate Email:</label>
        <input type="email" id="altEmail" name="altEmail" value="<%= user.getAltmail() != null ? user.getAltmail() : "" %>">
        
        <!-- Primary Phone Number (non-editable) -->
        <label for="primaryPhone">Primary Phone Number:</label>
        <input type="text" id="primaryPhone" name="primaryPhone" value="<%= user.getPhonenumber() %>" readonly>

        <!-- Alternate Phone Number -->
        <label for="altPhone">Alternate Phone Number:</label>
        <input type="text" id="altPhone" name="altPhone" value="<%= user.getAltphone() != null ? user.getAltphone() : "" %>">
        
        <!-- Submit Button -->
        <input type="submit" class="button" value="Save Changes">
    </form>

    <% } else { %>
        <p>No user details found. Please log in.</p>
    <% } %>
</div>

</body>
</html>
