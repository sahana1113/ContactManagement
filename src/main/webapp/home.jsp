<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.example.*" %>
<%    // Assuming user object is stored in session
  //  String username = (String) session.getAttribute("username");

    // Assuming contacts and categories are fetched from your database
 //   List<Contact> contacts = (List<Contact>) request.getAttribute("contacts");
   // Map<String, List<Contact>> categories = (Map<String, List<Contact>>) request.getAttribute("categories");

%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .container {
            width: 100%;
            display: flex;
            justify-content: space-between;
            padding: 20px;
        }
        .column {
            width: 30%;
            padding: 10px;
            border: 1px solid #ccc;
        }
        .column h3 {
            border-bottom: 1px solid #ccc;
            padding-bottom: 10px;
        }
        /* Remove bullet points */
        .column ul {
            list-style-type: none;
            padding-left: 0;
        }
        /* Style for the list items (li) */
        .column ul li {
            margin-bottom: 15px;
            padding: 10px;
            border: 2px solid #ccc; /* Add border */
            border-radius: 5px; /* Rounded corners */
            font-size: 18px; /* Increase font size */
            text-align: center; /* Center align the text */
            transition: 0.3s ease-in-out; /* Add transition for hover effect */
        }
        /* Hover effect on list items */
        .column ul li:hover {
            background-color: #f0f0f0; /* Change background color on hover */
            border-color: #000; /* Darken border on hover */
        }
        /* Anchor tag without extra styling */
        a {
            color: black; /* Link color */
            text-decoration: none; /* Remove underline */
        }
        .logout-btn {
            margin-top: 20px;
            display: block;
            padding: 10px 15px;
            background-color: blue;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            text-align: center;
        }
    </style>
</head>
<body>
    <!-- Welcome Section -->
    <h2>Welcome back!</h2>

    <div class="container">
        <!-- My Account Column -->
        <div class="column">
            <h3>My Account</h3>
            <ul>
                <li><a href="editDetails.jsp">Edit My Details</a></li>
                <li><a href="showDetails.jsp">Show My Details</a></li>
                <li><a href="addAltEmail.jsp">Add Alternate Email ID</a></li>
                <li><a href="addAltPhone.jsp">Add Alternate Phone Number</a></li>
            </ul>
            <a href="logout.jsp" class="logout-btn">Logout</a>
        </div>

        <!-- My Contacts Column -->
        <div class="column">
            <h3>My Contacts</h3>
            <ul>
        <% 
        try {
            ContactDao cd = new ContactDao(); // Ensure ContactDao is correctly implemented
            List<String> contactList = cd.display(); // Call the display() method
            
            // Iterate through the contact list
            if (contactList != null && !contactList.isEmpty()) {
                for (String contact : contactList) { %>
                    <li class="contact-item"><%= contact %> </li>
                <% }
            } else { %>
                <li>No contacts available.</li>
            <% }
        } catch (Exception e) {
            e.printStackTrace(); %>
            <li>Error fetching contacts: <%= e.getMessage() %></li>
        <% } %>
    </ul>
             <a href="createContact.jsp" class="logout-btn">Create New</a>
        </div>

        <!-- Categories Column -->
        <div class="column">
            <h3>Categories</h3>
            <ul>
                <li><a href="category.jsp?name=family">Family</a></li>
                <li><a href="category.jsp?name=work">Work</a></li>
                <li><a href="category.jsp?name=friends">Friends</a></li>
                <li><a href="category.jsp?name=favourites">Favourites</a></li>
                <li><a href="category.jsp?name=archived">Archived</a></li>
                <li><a href="category.jsp?name=blocked">Blocked</a></li>
            </ul>
            <a href="createNew.jsp" class="logout-btn">Create New</a>
        </div>
    </div>
</body>
</html>
    