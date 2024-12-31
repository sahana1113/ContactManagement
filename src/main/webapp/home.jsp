<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="java.sql.SQLException" %>
<%@ include file="sessionValidation.jsp" %>
<%@ page import="com.Dao.*" %>
<%@ page import="com.Bean.*" %>
<%@ page import="com.Session.*" %>
<%@ page session="false" %>
<%@ page import="com.Session.*" %>
<%
int uId = (Integer) request.getAttribute("user_id"); 
BeanUserDetails user=SessionData.getUserData().get(uId);
if(user==null){
	DaoUserContact dao=new DaoUserContact();
	user=dao.getUserDetailsById(uId);
}
String s=user.getUsername();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
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
            margin-left: 290px;
            flex-grow: 1;
            background-color: #ECF0F1;
            padding: 20px;
            display: flex;
    		flex-direction: column;
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
            font-size:40px;
          /*  text-align: center;*/
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
            justify-content:space-between;
            align-items: center; 
            text-align:center;
        }
        .details-list ul li:hover {
            background-color: #f9f9f9;
            cursor: pointer;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3498DB;
            color: white;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            text-align: center;
            margin-top: 20px;
        }
        .btn:hover {
            background-color: #2980B9;
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

        <div class="main-content">
            <div class="details-list">
                <h1>Welcome back <%=s+"!"%></h1>
                <h3>My Account Details</h3>
                <%
                DaoUserContact contactDao = new DaoUserContact();
                try {
                	if(SessionData.getUserData().containsKey(uId))
                	{
                 	user=SessionData.getUserData().get(uId);
                	}
                	else
                	{
                		user=contactDao.getUserDetailsById(uId);
                	}
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                if (user != null) {                                                  
                %>
                <ul>
                    <li><strong>Username:</strong> <span><%= (user.getUsername()).toString() %></span></li>
                    <li><strong>Primary Email:</strong> <span><%= user.getUsermail() %></span></li>
                    <li><strong>Gender:</strong> <span><%= user.getGender() %></span></li>
                    <li><strong>Primary Phone Number:</strong> <span><%= user.getPhonenumber() %></span></li>
                    <li><strong>Birthday:</strong> <span><%= user.getBirthday() %></span></li>
                    <li><strong>Alternate Emails:</strong>
                        <ul>
                            <%
                            boolean r=false;
                            for (BeanMail email : user.getAltMail()) {
                                  if (email!=null) {
                                        r=true;
                            %>
                                    <li><%=email.getAltMail()%></li>
                            <% } }if(!r)
                            	out.print("No alternate mails assigned"); %>
                        </ul>
                    </li>
                    <li><strong>Alternate Phone Numbers:</strong>
                        <ul>
                            <%
                            boolean r1=false;
                            for (BeanPhone phone : user.getAltPhone()) {
                                       if (phone!=null) {
                                              r1=true;
                            %>
                                    <li><%=phone.getAltPhone()%></li>
                            <% } }if(!r)
                            	out.print("No alternate phonenumber assigned"); %>
                        </ul>
                    </li>
                    
                </ul>
                <a href="EditDetails.jsp" class="btn">Edit My Details</a>
                &emsp;
                 <a href="prime.jsp" class="btn">Change Password</a>
                <%
                    } else {
                %>
                    <p>No user details found. Please ensure you are logged in.</p>
                <%
                    }
                %>
            </div>
        </div>

    </div>

</body>
</html>
