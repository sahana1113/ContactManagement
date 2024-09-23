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
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 60%; /* Slightly wider for better readability */
            margin: 50px auto;
            padding: 30px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
            font-size: 24px;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid #ccc;
        }
        th, td {
            padding: 15px;
            text-align: left;
        }
        th {
            background-color: #f0f0f0;
            font-weight: bold;
            color: #333;
        }
        td {
            background-color: #fff;
        }
        tr:nth-child(even) td {
            background-color: #f9f9f9; /* Alternating row colors for better readability */
        }
        .back-btn {
            display: inline-block;
            padding: 12px 20px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            text-align: center;
            font-size: 16px;
            transition: background-color 0.3s ease;
            margin-top: 20px;
            text-align: center;
        }
        .back-btn:hover {
            background-color: #0056b3;
        }
        @media (max-width: 768px) {
            .container {
                width: 90%;
                padding: 20px;
            }
            th, td {
                padding: 10px;
            }
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
