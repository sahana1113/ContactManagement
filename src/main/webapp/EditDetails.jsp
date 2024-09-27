<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page session="true" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.sql.SQLException" %>
<%@ include file="sessionValidation.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User Details</title>
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
            margin-bottom: 80px;
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
        input[type="text"], input[type="email"], input[type="date"], select {
            padding: 10px;
            width: 100%;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 17px;
            background-color: #f9f9f9;
        }
        input[readonly] {
            background-color: #e9e9e9;
            cursor: not-allowed;
        }
        .button {
            margin-top: 30px;
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
            <h2>Edit User Details</h2>

            <%
            int user_Id = (int) session.getAttribute("user_id");
            UserContactDao contactDao = new UserContactDao();
            UserDetailsBean user = new UserDetailsBean();

            try {
                user = contactDao.getUserDetailsById(user_Id);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (user != null) {
            %>

            <form action="update" method="post">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required>

                <label for="gender">Gender:</label>
                <select id="gender" name="gender" required>
                    <option value="male" <%= (user.getGender() != null && user.getGender().equals("male")) ? "selected" : "" %>>Male</option>
                    <option value="female" <%= (user.getGender() != null && user.getGender().equals("female")) ? "selected" : "" %>>Female</option>
                    <option value="other" <%= (user.getGender() != null && user.getGender().equals("other")) ? "selected" : "" %>>Other</option>
                </select>

                <label for="birthday">Birthday:</label>
                <input type="date" id="birthday" name="birthday" value="<%= user.getBirthday() %>" required>

                <label for="primaryEmail">Primary Email:</label>
                <input type="email" id="primaryEmail" name="primaryEmail" value="<%= user.getUsermail() %>" readonly>

                <label for="altEmail">Alternate Email:</label>
                <input type="email" id="altEmail" name="altEmail" value="<%= user.getAltmail() != null ? user.getAltmail() : "" %>">

                <label for="primaryPhone">Primary Phone Number:</label>
                <input type="text" id="primaryPhone" name="primaryPhone" value="<%= user.getPhonenumber() %>" readonly>

                <label for="altPhone">Alternate Phone Number:</label>
                <input type="text" id="altPhone" name="altPhone" value="<%= user.getAltphone() != null ? user.getAltphone() : "" %>">

                <input type="submit" class="button" value="Save Changes">
            </form>

            <% } else { %>
                <p>No user details found. Please log in.</p>
            <% } %>
        </div>
    </div>

</div>

</body>
</html>
