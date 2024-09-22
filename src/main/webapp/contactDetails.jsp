<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="java.sql.SQLException" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            width: 50%;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        h2 {
            text-align: center;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #ccc;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        td {
            background-color: #fff;
        }
        .back-btn {
            display: block;
            text-align: center;
            margin-top: 20px;
            padding: 10px 15px;
            background-color: blue;
            color: white;
            text-decoration: none;
            border-radius: 5px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Contact Details</h2>

    <%
    String contactIdParam = request.getParameter("id");
                        int contactId = Integer.parseInt(contactIdParam);
                        String userId = request.getParameter("userId");
                        UserContactDao contactDao = new UserContactDao();
                        ContactDetailsBean contact = new ContactDetailsBean();

                        try {
                            contact = contactDao.getContactDetailsById(contactId);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        if (contact != null) {
    %>
    <table>
        <tr>
            <th>Name</th>
            <td><%= contact.getContactname() %></td>
        </tr>
        <tr>
            <th>Mail</th>
            <td><%= contact.getContactmail() %></td>
        </tr>
        <tr>
            <th>Phone Number</th>
            <td><%= contact.getPhonenumber() %></td>
        </tr>
        <tr>
            <th>Gender</th>
            <td><%= contact.getGender() %></td>
        </tr>
        <tr>
            <th>Birthday</th>
            <td><%= contact.getBirthday() %></td>
        </tr>
        <tr>
            <th>Location</th>
            <td><%= contact.getLocation() %></td>
        </tr>
    </table>

    <% } else { %>
        <p>No contact details found.</p>
    <% } %>

    <!-- Back button -->
    <a href="home.jsp?id=<%=userId%>" class="back-btn">Back</a>
</div>

</body>
</html>
