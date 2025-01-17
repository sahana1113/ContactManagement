<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
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
    <title>Edit User Details</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"> 
    <style>
body {
    font-family: Arial, sans-serif;
    background-color: #ECF0F1;
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

.sidebar h2 {
    margin-bottom: 20px;
    color: #ECF0F1;
}

.sidebar ul {
    list-style-type: none;
    padding-left: 0;
}

.sidebar ul li {
    margin-bottom: 20px;
}

.sidebar ul li a {
    display: block;
    padding: 10px;
    background-color: #34495E;
    border-radius: 5px;
    color: white;
    text-decoration: none;
    font-size: 18px;
    transition: background-color 0.3s;
}

.sidebar ul li a:hover {
    background-color: #1ABC9C;
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

.edit-container {
    margin-bottom: 80px;
    width: 60%;
    padding: 30px;
    background-color: #fff;
    border-radius: 10px;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.edit-container h2 {
    text-align: center;
    color: #333;
    font-size: 24px;
    margin-bottom: 20px;
}

.form-group {
    margin-bottom: 20px;
}

form {
    display: flex;
    flex-direction: column;
    margin-bottom: 20px;
}

label {
    margin-top: 15px;
    font-size: 20px;
    font-weight: bold;
    color: #333;
}

input[type="text"],
input[type="email"],
input[type="date"],
select,
button {
    padding: 10px;
    width: 87%;
    margin-top: 5px;
    border: 1px solid #ccc;
    border-radius: 5px;
    box-sizing: border-box;
    font-size: 17px;
    background-color: #f9f9f9;
}
.delete-btn {
    margin-top: 30px;
    padding: 12px;
    background-color: #E74C3C;
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
    width: 100px;
    align-self: center;
    transition: background-color 0.3s ease;
        }

        .delete-btn:hover {
            background-color: #C0392B;
        }
  .edit-btn {
    padding: 12px;
    width: 100px;
    background-color: #EAF4FB; /* Light background color to differentiate */
    color: #3498DB;
    border: 2px solid #3498DB;
    border-radius: 5px;
    cursor: pointer;
    font-size: 14px;
    margin-top: 30px;
    transition: background-color 0.3s ease;
    display: inline-block;
}
        
input[readonly] {
    background-color: #e9e9e9;
    cursor: not-allowed;
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
    width: 100px;
    align-self: center;
    transition: background-color 0.3s ease;
}


.edit-btn:hover {
    background-color: #D4E6F1;
}

.button:hover {
    background-color: #2980B9;
}

       .action-buttons {
            display: flex;
            gap: 10px;
         }
        .action-buttons i {
            font-size: 20px;
            cursor: pointer;
        }
        .action-buttons i.edit {
            color: #3498DB;
        }
        .action-buttons i.delete {
            color: #E74C3C;
        }
        .action-buttons i:hover {
            opacity: 0.7;
        }

@media (max-width: 768px) {
    .edit-container {
        width: 90%;
        padding: 20px;
    }

    .button {
        width: 100%;
    }

    .sidebar {
        width: 200px; /* Optional: Adjust sidebar width for smaller screens */
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
        <div class="edit-container">
            <h2>Edit User Details</h2>

            <%
            int user_Id = (Integer) request.getAttribute("user_id"); 
            DaoUserContact contactDao = new DaoUserContact();
            BeanUserDetails user = new BeanUserDetails();

            try {
            	if(SessionData.getUserData().containsKey(user_Id))
            	{
            		user=SessionData.getUserData().get(user_Id);
            	}
            	else{
            		user=contactDao.getUserDetailsById(user_Id);
            	}
              //  user = contactDao.getUserDetailsById(user_Id);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (user != null) {
            	List<BeanMail>altEmails=user.getAltMail();
            	List<BeanPhone>altPhone=user.getAltPhone();                                                          
            %>

            <form action="update" method="post">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" value="<%= user.getUsername() %>" required>

                <label for="gender">Gender:</label>
                <select id="gender" name="gender" required>
                    <option value="male" <%= (user.getGender() != null && user.getGender().equals("male")) ? "selected" : "" %>>Male</option>
                    <option value="female" <%= (user.getGender() != null && user.getGender().equals("female")) ? "selected" : "" %>>Female</option>
                    <option value="other" <%= (user.getGender() != null && user.getGender().equals("other")) ? "selected" : "" %>>Other</option>
                </select>

                <label for="birthday">Birthday:</label>
                <input type="date" id="birthday" name="birthday" value="<%= user.getBirthday() %>" required>

                <label for="primaryEmail">Primary Email:</label>
                <select id="primaryEmail" name="primaryEmail" required>
                    <option value="<%= user.getUsermail() %>"><%= user.getUsermail() %></option>
                    <%
                    for (BeanMail email : altEmails) {
                    %>
                    <option value="<%= email.getAltMail() %>"><%=email.getAltMail()%></option>
                    <%
                    }
                    %>
                </select>
                 <label for="primaryPhone">Primary Phone Number:</label>
                <select id="primaryPhone" name="primaryPhone" required>
                    <option value="<%=user.getPhonenumber()%>"><%=user.getPhonenumber()%></option>
                    <%
                    for (BeanPhone phone : altPhone) {
                    %>
                    <option value="<%=phone.getAltPhone() %>"><%=phone.getAltPhone()%></option>
                    <%
                    }
                    %>
                </select>
                 <input type="hidden" name="action" value="updateUserDetails">
                <input type="submit" class="button" value="Save">
            </form>
            <!-- Alternate Emails -->
<label for="altEmail">Alternate Email:</label>
<div id="alternateEmails">
    <form action="mail?action=addEmail" method="post" style="display: inline;">
        <input type="email" name="newAltEmail" placeholder="Enter alternate email" required />
        <button type="submit" class="button">Add</button>
    </form>
    <% for (BeanMail email : altEmails) { %>
        <div style="align-items: center; margin-bottom: 10px;">
        
                <form action="mail?action=editEmail" method="post" style="display: inline;">
                    <input type="email" name="altEmail" value="<%=email.getAltMail()%>" style="width: 900px;" />
                    <input type="hidden" name="id" value="<%=email.getEmail_id() %>" />
                    <button type="submit" class="edit-btn" title="Edit">
                        <i class="fas fa-pencil-alt"></i>
                    </button>
                </form>
                
                <form action="mail?action=deleteEmail" method="post" style="display: inline;">
                    <input type="hidden" name="altEmail" value="<%=email.getAltMail()%>" />
                    <button type="submit" onclick="return confirm('Are you sure you want to delete this email?')" class="delete-btn" title="Delete">
                        <i class="fas fa-trash-alt"></i>
                    </button>
                </form>
        </div>
    <% } %>
</div>

<label for="altPhone">Alternate Phone Number:</label>
<div id="alternatePhones">

    <form action="phone?action=addPhone" method="post" style="display: inline;">
        <input type="text" name="newAltPhone" placeholder="Enter alternate phone number" required />
        <input type="hidden" name="action" value="addPhone" />
        <button type="submit" class="button">Add</button>
    </form>
    
    <% for (BeanPhone phone : altPhone) { %>
        <div style=" align-items: center; margin-bottom: 10px;">
        
                <form action="phone?action=editPhone" method="post" style="display: inline; margin-right:10px">
                    <input type="text" name="altPhone" value="<%=phone.getAltPhone()%>" style="width: 880px;" />
                    <input type="hidden" name="id" value="<%=phone.getPhone_id()  %>" />
                    <button type="submit" class="edit-btn" title="Edit">
                        <i class="fas fa-pencil-alt"></i>
                    </button>
                </form>

                <form action="phone?action=deletePhone" method="post" style="display: inline;">
                    <input type="hidden" name="altPhone" value="<%=phone.getAltPhone()%>" />
                    <button type="submit" onclick="return confirm('Are you sure you want to delete this phone number?')" class="delete-btn" title="Delete">
                        <i class="fas fa-trash-alt"></i>
                    </button>
                </form>
        </div>
    <% } }else { %>
                <p>No user details found. Please log in.</p>
            <% } %>
</div>
        </div>
    </div>

</body>
</html>
