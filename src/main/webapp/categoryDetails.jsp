<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="java.util.List" %>
<%@ page session="false" %>
<%@ include file="sessionValidation.jsp" %>
<%@ page import="com.Dao.*" %>
<%@ page import="com.Bean.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Category Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }

        .container {
            display: flex;
            min-height: 100vh;
            width: 100%;
        }

        .sidebar {
            width: 250px;
            background-color: #2C3E50;
            color: white;
            padding: 20px;
            box-shadow: -2px 0 5px rgba(0, 0, 0, 0.1);
            position: fixed;
    		height:100vh;
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
            display: block;
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
            display: block;
            color: white;
            text-decoration: none;
            font-size: 18px;
        }

        .main-content {
            margin-left:280px;
            flex-grow: 1;
            background-color: #ECF0F1;
            padding: 20px;
        }

        .contacts-list {
            padding: 20px;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .contacts-list h2 {
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

        .btn {
            padding: 10px 20px;
            background-color: #007BFF;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-top: 20px;
            display: inline-block;
            text-align: center;
            transition: background-color 0.3s ease;
        }

        .btn:hover {
            background-color: #0056b3;
        }

        .delete-btn {
            background-color:#E74C3C;
        }

        .delete-btn:hover {
            background-color: #d9534f;
        }

        .add-btn {
            background-color: #28a745;
        }

        .add-btn:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="sidebar">
        <h2>My Account</h2>
        <ul>
            <li><a href="myDetails.jsp">My Details</a></li>
            <li><a href="category.jsp">View Categories</a></li>
            <li><a href="contacts.jsp">View Contacts</a></li>
            <li><a href="logout">Logout</a></li>
        </ul>
    </div>

    <div class="main-content">
        <div class="contacts-list">
            <%
            int category = Integer.parseInt(request.getParameter("id"));
                                    int userId = (Integer) request.getAttribute("user_id"); 

                                    DaoUserContact categoryDao = new DaoUserContact(userId);
                                    List<BeanContactDetails> contactsInCategory = categoryDao.getContactsInCategory(category);
                                    List<BeanContactDetails> contactsNotInCategory = categoryDao.getContactsNotInCategory(category, userId);
            %>

            <h2>Contacts in Category: <%=categoryDao.getCategoryName(category)%></h2>

            <table>
                <tr>
                    <th>Contact Name</th>
                    <th>Phone Number</th>
                    <th>Action</th>
                </tr>
                <%
                if (contactsInCategory != null && !contactsInCategory.isEmpty()) {
                                    for (BeanContactDetails contact : contactsInCategory) {
                %>
                        <tr>
                            <td><%=contact.getName()%></td>
                            <td><%=contact.getPhonenumber()%></td>
                            <td>
                                <a href="deleteContactCategory.jsp?contactId=<%=contact.getContact_id()%>&category=<%=category%>" class="btn delete-btn" onclick="return confirm('Are you sure you want to remove this contact?');">Remove</a>
                            </td>
                        </tr>
                <%
                } } else {
                %>
                    <tr>
                        <td colspan="3">No contacts found in this category.</td>
                    </tr>
                <%
                }
                %>
            </table>

            <h2>Add Contacts to Category</h2>

            <table>
                <tr>
                    <th>Contact Name</th>
                    <th>Phone Number</th>
                    <th>Action</th>
                </tr>
                <%
                if (contactsNotInCategory != null && !contactsNotInCategory.isEmpty()) {
                                                    for (BeanContactDetails contact : contactsNotInCategory) {
                                 
                %>
                        <tr>
                            <td><%=contact.getName()%></td>
                            <td><%= contact.getPhonenumber() %></td>
                            <td>
                                <a href="addContactToCategory.jsp?contactId=<%= contact.getContact_id() %>&category=<%= category %>" class="btn add-btn">Add</a>
                            </td>
                        </tr>
                <% } } else { %>
                    <tr>
                        <td colspan="3">All contacts are already in this category.</td>
                    </tr>
                <% } %>
            </table>

            <a href="deleteCategory.jsp?category=<%= category %>" class="btn delete-btn" onclick="return confirm('Are you sure you want to remove this contact?');">Delete Whole Category</a>
        </div>
    </div>
</div>

</body>
</html>
