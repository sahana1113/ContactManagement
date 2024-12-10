 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ include file="sessionValidation.jsp" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<%@ page import="com.Dao.*" %>
<%@ page import="com.Bean.*" %>
<%@ page session="false" %>
<%@ page import="java.time.*" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
int contactId = Integer.parseInt(request.getParameter("id"));
    int uId= (Integer) request.getAttribute("user_id"); 
    DaoUserContact contactDao = new DaoUserContact();
    BeanContactDetails contact = new BeanContactDetails();
    try {
        contact = contactDao.getContactDetailsById(contactId);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    String selectedTimeZone = String.valueOf(request.getAttribute("selectedTimeZone"));
    if (selectedTimeZone.equals("null") || selectedTimeZone.isEmpty()) {
        selectedTimeZone = "Asia/Kolkata"; 
    }
    long createdTimeEpoch = contact.getCreated_time(); 
 	Instant createdTimeInstant = Instant.ofEpochSecond(createdTimeEpoch);
 	ZonedDateTime contactZonedDateTime = createdTimeInstant.atZone(ZoneId.of("UTC")); 
 	ZonedDateTime convertedTime = contactZonedDateTime.withZoneSameInstant(ZoneId.of(selectedTimeZone));
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
    String formattedTime = convertedTime.format(formatter);
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
            height: 100%;
        }
        .main-content {
            margin-left:280px;
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
        .alternate-email-wrapper, .alternate-phone-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 10px;
}

.alternate-email-input, .alternate-phone-input {
    flex: 1;
    padding: 10px;
    font-size: 16px;
    margin-right: 10px; /* To create space between the input and delete button */
    border-radius: 5px;
    border: 1px solid #ccc;
}

.delete-btn {
    background-color: #E74C3C;
    color: white;
    padding: 10px 15px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
}

.delete-btn:hover {
    background-color: #C0392B;
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
        select {
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border-radius: 5px;
    border: 1px solid #ccc;
    background-color: #fff;
    color: #333;
    cursor: pointer;
    transition: border-color 0.3s, background-color 0.3s;
}

select:focus {
    border-color: #3498DB;
    outline: none;
    background-color: #ECF0F1;
}

option {
    padding: 10px;
    background-color: #fff;
    color: #333;
}

option:hover {
    background-color: #f0f0f0;
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
                <li>
                    <strong>Created Time:</strong>
    <div style="display: flex; align-items: center;">
        <form action="convertTimeZone" method="GET">
            <input type="hidden" name="contactId" value=<%= contactId %>/>
            <select name="timeZone" id="timeZone" onchange="this.form.submit()">
                <%
                String[] selectedTimeZones = {
                    "America/New_York",
                    "Europe/London",
                    "Asia/Tokyo",
                    "Australia/Sydney",
                    "America/Los_Angeles",
                    "Europe/Berlin",
                    "Asia/Kolkata",
                    "UTC"
                };
                for (String timeZone : selectedTimeZones) {
                    String selected = timeZone.equals(selectedTimeZone) ? "selected" : "";
                %>
                    <option value="<%= timeZone %>" <%= selected %>><%= timeZone %></option>
                <%
                }
                %>
            </select>               
        </form>&emsp;
                <p style="margin-right: 20px;"><%= formattedTime %></p>
    </div>
                </li>
                <li><strong>Name:</strong> <span><%=contact.getName()%></span></li>
                <li><strong>Email:</strong> <span><%=contact.getMail()%></span></li>
                <li><strong>Phone Number:</strong> <span><%= contact.getPhonenumber() %></span></li>
                <li><strong>Gender:</strong> <span><%= contact.getGender() %></span></li>
                <li><strong>Birthday:</strong> <span><%= contact.getBirthday() %></span></li>
                <li><strong>Location:</strong> <span><%= contact.getLocation() %></span></li>
                 
                <li><strong>Groups:</strong>
                        <ul>
                            <% List<BeanCategory> categories = contact.getCategory();
                            if (categories != null && !categories.isEmpty()) {
                               for (BeanCategory group : categories) {
                                if (group!=null) { %>
                                    <li><%= group.getCategory_name() %></li>
                            <% } } } else {
                    out.print("No groups assigned.");
                }
                %>
                        </ul>
                    </li>
            </ul>
               <a href="editContact.jsp?id=<%= contactId %>" class="btn edit-btn">Edit Contact</a> &emsp;
            <a href="deleteContact.jsp?id=<%= contactId %>" class="btn delete-btn" onclick="return confirm('Are you sure you want to delete this contact?');">Delete Contact</a>
            
            <% } else { %>
                <p>No contact details found.</p>
            <% } %>

        </div>
    </div>

</div>

</body>
</html>