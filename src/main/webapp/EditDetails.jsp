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
        }
        .container {
            width: 60%;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        form {
            display: flex;
            flex-direction: column;
        }
        label {
            margin-top: 10px;
        }
        input[type="text"], input[type="email"], input[type="date"] {
            padding: 8px;
            width: 100%;
            margin-top: 5px;
        }
        .button {
            margin-top: 20px;
            padding: 10px;
            background-color: blue;
            color: white;
            border: none;
            cursor: pointer;
            width: 150px;
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
