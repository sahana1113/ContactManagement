<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page session="true" %>
<%@ include file="sessionValidation.jsp" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>

<%
    int contactId = Integer.parseInt(String.valueOf(session.getAttribute("contact")));
    int uId= (int) session.getAttribute("user_id");
    UserContactDao contactDao = new UserContactDao();
    ContactDetailsBean contact = new ContactDetailsBean();

    try {
        contact = contactDao.getContactDetailsById(contactId);
    } catch (SQLException e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #ECF0F1;
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
        }
        .details-list {
            width: 100%;
            padding: 20px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        .details-list h2 {
            margin-bottom: 20px;
            font-size: 40px;
        }
        .details-list ul {
            list-style-type: none;
            padding: 0;
        }
        .details-list ul li {
            background-color: white;
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 10px;
            font-size: 18px;
            border-radius: 5px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            text-align: center;
        }
        
        .details-list ul li:hover {
            background-color: #f9f9f9;
            cursor: pointer;
        }
         .btn {
            display: inline-block;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;
            margin-top: 20px;
            font-size:17px;
        }

        .edit-btn {
            background-color: #3498DB;
            color: white;
        }

        .edit-btn:hover {
            background-color: #2980B9;
        }

        .delete-btn {
            background-color: #E74C3C;
            color: white;
        }

        .delete-btn:hover {
            background-color: #C0392B;
        }

        .button-container {
            display: flex;
            justify-content: space-between;
            width: 200px;
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
        <div class="details-list">
            <h2>Contact Details</h2>

            <%
            if (contact != null) {
            %>
            <ul>
                <li><strong>Name:</strong> <span><%= contact.getContactname() %></span></li>
                <li><strong>Email:</strong> <span><%= contact.getContactmail() %></span></li>
                <li><strong>Phone Number:</strong> <span><%= contact.getPhonenumber() %></span></li>
                <li><strong>Gender:</strong> <span><%= contact.getGender() %></span></li>
                <li><strong>Birthday:</strong> <span><%= contact.getBirthday() %></span></li>
                <li><strong>Location:</strong> <span><%= contact.getLocation() %></span></li>
                <li><strong>Groups:</strong>
                        <ul>
                            <% List<String> categories = contact.getCategory();
                            if (categories != null && !categories.isEmpty()) {
                               for (String group : categories) {
                                if (!group.isEmpty()) { %>
                                    <li><%= group %></li>
                            <% } } } else {
                    out.print("No groups assigned.");
                }
                %>
                        </ul>
                    </li>
            </ul>

            <div class="button-container">

                <form action="update2" method="post">

                    <input type="hidden" name="edit" value=<%= contact.getContact_id() %>></input>

                    <input type="submit" class="btn edit-btn" value="Edit Contact"></input>

                </form>
                   &emsp; &emsp;
                <form action="update2" method="post">

                    <input type="hidden" name="delete" value=<%= contact.getContact_id() %>></input>

                    <input type="submit" class="btn delete-btn" value="Delete Contact" onclick="return confirm('Are you sure you want to delete this contact?');"></input>

                </form>

            </div>
            <% } else { %>
                <p>No contact details found.</p>
            <% } %>

        </div>
    </div>

</div>

</body>
</html>
