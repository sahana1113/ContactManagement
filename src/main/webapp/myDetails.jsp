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
        }
        .container {
            width: 60%;
            margin: 0 auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ccc;
        }
        th {
            background-color: #f2f2f2;
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
