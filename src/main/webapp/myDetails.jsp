<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.sql.SQLException" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Account Details</title>
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            padding: 15px;
            border: 1px solid #ccc;
        }
        th {
            background-color: #f0f0f0;
            font-weight: bold;
        }
        td {
            background-color: #fff;
        }
        li {
            list-style-type: none;
            padding-left: 0;
        }
        tr:nth-child(even) td {
            background-color: #f9f9f9; /* Alternating row colors */
        }
        .back-btn {
            display: inline-block;
            padding: 12px 20px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            text-align: center;
            transition: background-color 0.3s ease;
            font-size: 16px;
            margin-top: 20px;
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
    <h2>My Account Details</h2>
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
            
            // Check if user details are available
            if (user != null) {
    %>
    
    <table>
        <tr>
            <th>Username</th>
            <td><%= user.getUsername() %></td>
        </tr>
        <tr>
            <th>Primary Email</th>
            <td><%= user.getUsermail() %></td>
        </tr>
        <tr>
            <th>Gender</th>
            <td><%= user.getGender() %></td>
        </tr>
        <tr>
            <th>Primary Phone Number</th>
            <td><%= user.getPhonenumber() %></td>
        </tr>
        <tr>
            <th>Birthday</th>
            <td><%= user.getBirthday() %></td>
        </tr>
        <tr>
            <th>Alternate mails</th>
            <td>
            <%
                    for (String s : user.getAllMail()) {
                    	if(s.length()!=0)
                    out.println("<li>" + s + "</li>");
                    }
            %>
             </td>
        </tr>
        <tr>
            <th>Alternate phone</th>
            <td>
            <%
                    for (String s : user.getAllPhone()) {
                    if(s.length()!=0)
                    out.println("<li>" + s + "</li>");
                    }
            %>
             </td>
        </tr>
    </table>
    
    <% } else { %>
        <p>No user details found. Please ensure you are logged in.</p>
    <% } %>
    <a href="home.jsp?id=<%=userId%>" class="back-btn">Back</a>
</div>

</body>
</html>
