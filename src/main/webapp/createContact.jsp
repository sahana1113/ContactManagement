<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="sessionValidation.jsp" %>
<%@ page import="com.example.*" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Dao.*" %>
<%@ page import="com.Bean.*" %> 
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Enter the Contact Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
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
            flex-grow: 1;
            background-color: #ECF0F1;
            padding: 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            flex-direction: column;
        }
        .form-container {
        margin-bottom:120px;
            width: 50%;
            background-color: white;
            padding: 30px;
            box-shadow: 0px 0px 15px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }
        .form-container h2 {
            text-align: center;
            color: #333;
            font-size: 24px;
            margin-bottom: 20px;
        }
        label {
            font-weight: bold;
            margin-bottom: 5px;
            font-size:20px;
            display: block;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
            
        }
         input[type="checkbox"] {
            appearance: none;
            width: 20px;
            height: 20px;
            border: 2px solid #3498DB;
            border-radius: 4px;
            outline: none;
            cursor: pointer;
            transition: background-color 0.3s, border-color 0.3s;
        }

        input[type="checkbox"]:checked {
            background-color: #3498DB;
            border-color: #2980B9;
        }

        input[type="checkbox"]:hover {
            border-color: #2980B9;
        }
        input[type="text"], input[type="email"], input[type="date"], input[type="tel"] {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 17px;
            background-color: #f9f9f9;
        }
        input[type="submit"] {
            width: 100%;
            padding: 12px;
             background-color: #3498DB;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }
        .checkbox-group {
    			display: flex; 
    			align-items: center; 
    			margin-bottom: 10px; 
		}
        input[type="submit"]:hover {
            background-color: #2980B9;
        }
        .gender-group {
            display: flex;
            justify-content: space-between;
            margin-top: 10px;
        }
        .gender-group input {
            margin-right: 5px;
        }
        @media (max-width: 768px) {
            .form-container {
                width: 80%;
            }
        }
        @media (max-width: 480px) {
            .form-container {
                width: 95%;
            }
            .gender-group {
                flex-direction: column;
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
            <h2>Create New Contact</h2>
            <%
            DaoUserContact ucd=new DaoUserContact((Integer) request.getAttribute("user_id") );
                                    List<BeanCategory>categories=ucd.getCategoriesByUserId();
            %>
            <form action="create" method="post">
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required>
                </div>

                <div class="form-group">
                    <label for="username">Contact Name:</label>
                    <input type="text" id="username" name="username" required>
                </div>

                <div class="form-group">
                    <label for="gender">Gender:</label>
                    <div class="gender-group">
                        <input type="radio" name="gender" value="male"> Male<br>
                        <input type="radio" name="gender" value="female"> Female<br>
                        <input type="radio" name="gender" value="other"> Other
                    </div>
                </div>

                <div class="form-group">
                    <label for="birthday">Birthday:</label>
                    <input type="date" id="birthday" name="birthday" required>
                </div>

                <div class="form-group">
                    <label for="phone">Phone Number:</label>
                    <input type="tel" id="phone" name="phone" pattern="[0-9]{10}" required>
                </div>

                <div class="form-group">
                    <label for="location">Location:</label>
                    <input type="text" id="location" name="location" required>
                </div>
                <div class="form-group">
    <label>Select Categories:</label>
    <%
    for (BeanCategory category : categories) {
    %>
        <div class="checkbox-group">
            <input type="checkbox" id="<%=category.getCategory_id()%>" name="categories" value="<%=category.getCategory_name()%>">
            &nbsp;<label for="<%=category.getCategory_id()%>"><%=category.getCategory_name()%></label>
        </div>
    <%
        }
    %>
</div>
                    <input type="submit" value="Create">
                </div>
            </form>
        </div>
    </div>

</div>

</body>
</html>
