<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@ include file="sessionValidation.jsp" %>
<%@ page import="com.Dao.*" %>
<%@ page import="com.Bean.*" %>
<%@ page import="com.Session.*" %>

<%@ page session="false" %>
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
            min-height: 100vh;
        }
        .sidebar {
            width: 250px;
            background-color: #2C3E50;
            color: white;
            padding: 20px;
            box-shadow: -2px 0 5px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
            position: fixed; 
            top: 0; 
            left: 0; 
            height: 100%;
            overflow-y: auto;
        }
        .sidebar h2 {
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
        .checkbox-group {
            margin-top: 20px;
            display: flex;
            flex-wrap: wrap;
        }
        .checkbox-group label {
            font-size: 16px;
            color: #333;
            font-weight: bold;
            margin-right: 10px;
        }
        .checkbox-group input[type="checkbox"] {
            margin-right: 10px;
            transform: scale(1.2);
            cursor: pointer;
        }
        .main-content {
            flex-grow: 1;
            background-color: #ECF0F1;
            padding: 20px;
            display: flex;
            flex-direction: column;
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
                int cont_Id = -1;
                int uId = (Integer) request.getAttribute("user_id");
                String idParam = request.getParameter("id");https://www.eclipse.org/downloads/packages/release/kepler/sr2/eclipse-ide-java-ee-developers	

                if (idParam == null || idParam.isEmpty()) {
                    out.println("Invalid ID parameter.");
                    return;
                }

                try {
                    cont_Id = Integer.parseInt(idParam);
                } catch (NumberFormatException e) {
                    out.println("Invalid ID format.");
                    return;
                }
                request.setAttribute("cont_Id", cont_Id);
                DaoUserContact contactDao = new DaoUserContact(uId);
                BeanContactDetails user = null;
                List<BeanCategory> all_categories = contactDao.getCategoriesByUserId();

                try {
                    user = contactDao.getContactDetailsById(cont_Id,uId);
                } catch (SQLException e) {
                    e.printStackTrace();
                    out.println("Error retrieving contact details.");
                    return; // Handle error gracefully
                }

                if (user != null) {
            %>

            <form action="update2" method="post">
                <input type="hidden" name="contactId" value="<%=cont_Id %>" >
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="<%=user.getName()%>" required>

                <label for="gender">Gender:</label>
                <select id="gender" name="gender" required>
                    <option value="male" <%= user.getGender() != null && user.getGender().equals("male") ? "selected" : "" %>>Male</option>
                    <option value="female" <%= user.getGender() != null && user.getGender().equals("female") ? "selected" : "" %>>Female</option>
                    <option value="other" <%= user.getGender() != null && user.getGender().equals("other") ? "selected" : "" %>>Other</option>
                </select>

                <label for="birthday">Birthday:</label>
                <input type="date" id="birthday" name="birthday" value="<%= user.getBirthday() %>" required>

                <label for="primaryEmail">Primary Email:</label>
                <input type="email" id="primaryEmail" name="primaryEmail" value="<%=user.getMail()%>">

                <label for="primaryPhone">Primary Phone Number:</label>
                <input type="text" id="primaryPhone" name="primaryPhone" value="<%= user.getPhonenumber() %>">

                <div class="checkbox-group"> 
                    <label>Categories:</label>
                    <%
                    if (all_categories != null && !all_categories.isEmpty()) {
                                            for (BeanCategory category : all_categories) {
                                                boolean isChecked = user.getCategory() != null && user.getCategory().contains(category.getCategory_name());
                    %>
                        <input type="checkbox" name="categoryContact" value="<%=category.getCategory_name()%>" <%=isChecked ? "checked" : ""%> />
                        <label for="category_<%=category.getCategory_name()%>"><%=category.getCategory_name()%></label><br/>
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
