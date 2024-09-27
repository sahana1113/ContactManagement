<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page session="true" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@ include file="sessionValidation.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Contact Details</title>
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
        .sidebar h2 {
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
        .checkbox-group {
    margin-top: 20px;
}

.checkbox-group label {
    font-size: 16px;
    color: #333;
    font-weight: bold;
    margin-right: 10px;
}

.checkbox-group input[type="checkbox"] {
    margin-right: 10px;
    transform: scale(1.2); /* Enlarge checkbox */
    vertical-align: middle;
    cursor: pointer;
}

.checkbox-group input[type="checkbox"]:checked {
    background-color: #3498DB; /* Change checked color */
}

.checkbox-group {
    display: flex;
    flex-wrap: wrap;
}

.checkbox-group label {
    margin-right: 15px;
    margin-bottom: 10px;
}

@media (max-width: 768px) {
    .checkbox-group {
        display: block;
    }

    .checkbox-group label {
        margin-right: 0;
        margin-bottom: 5px;
    }
}
        
        .main-content {
            flex-grow: 1;
            background-color: #ECF0F1;
            padding: 20px;
        }
        .form-container {
            width: 60%;
            margin: 50px auto;
            padding: 30px;
            background-color: white;
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
        .button {
            margin-top: 30px;
            padding: 12px;
            background-color: #3498DB;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            text-align: center;
            transition: background-color 0.3s ease;
        }
               
        .button:hover {
            background-color: #2980B9;
        }
        @media (max-width: 768px) {
            .form-container {
                width: 90%;
                padding: 20px;
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
        <div class="form-container">
            <h2>Edit Contact Details</h2>

            <%
            int cont_Id = Integer.parseInt(String.valueOf(session.getAttribute("contact")));
            int uId= (int) session.getAttribute("user_id");
                UserContactDao contactDao = new UserContactDao();
                ContactDetailsBean user = new ContactDetailsBean();
                List<String>all_categories=contactDao.getAllCategories(uId);
                session.setAttribute("cont_Id",cont_Id);
                try {
                    user = contactDao.getContactDetailsById(cont_Id);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (user != null) {
            %>

            <form action="update2" method="post">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="<%= user.getContactname() %>" required>

                <label for="gender">Gender:</label>
                <select id="gender" name="gender" required>
                    <option value="male" <%= (user.getGender() != null && user.getGender().equals("male")) ? "selected" : "" %>>Male</option>
                    <option value="female" <%= (user.getGender() != null && user.getGender().equals("female")) ? "selected" : "" %>>Female</option>
                    <option value="other" <%= (user.getGender() != null && user.getGender().equals("other")) ? "selected" : "" %>>Other</option>
                </select>

                <label for="birthday">Birthday:</label>
                <input type="date" id="birthday" name="birthday" value="<%= user.getBirthday() %>" required>

                <label for="primaryEmail">Primary Email:</label>
                <input type="email" id="primaryEmail" name="primaryEmail" value="<%= user.getContactmail() %>">

                <label for="primaryPhone">Primary Phone Number:</label>
                <input type="text" id="primaryPhone" name="primaryPhone" value="<%= user.getPhonenumber() %>">
                <div class="checkbox-group"> 
                   <label for="category">Categories:</label>
                   <%
            if (all_categories != null && !all_categories.isEmpty()) {
                for (String s : all_categories) {
                    boolean isChecked = user.getCategory().contains(s);
            %>
                    <input type="checkbox" name="categoryContact" value="<%= s %>" <%= isChecked ? "checked" : "" %> />
                    <label for="category_<%= s %>"><%= s %></label><br/>
            <% 
                }
            } else {
            %>
                <p>No categories found.</p>
            <% } %>
            </div>
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
