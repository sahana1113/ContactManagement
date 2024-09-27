<%@ page session="true" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Proxies
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Login</title>
    <script>
        function getUrlParameter(name) {
            name = name.replace(/[\[]/, '\\[').replace(/[\]]/, '\\]');
            var regex = new RegExp('[\\?&]' + name + '=([^&#]*)');
            var results = regex.exec(location.search);
            return results === null ? '' : decodeURIComponent(results[1].replace(/\+/g, ' '));
        }
        window.onload = function() {
            var errorMessage = getUrlParameter('error');
            if (errorMessage) {
                alert(errorMessage);
            }
        };
    </script>
    <style>
body {
    font-family: Arial, sans-serif;
    background-color:  #ECF0F1;
    margin: 0;
    padding: 0;
}

.container {
	color:white;
    width: 600px; /* Fixed width for better alignment */
    padding: 20px;
    background-color: #2C3E50;
    box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1); /* Improved shadow for better effect */
    border-radius: 8px; /* Rounded corners */
    margin: 20px auto; 
    margin-top:200px;
}

h2 {
    text-align: center;
    color: white;
    margin-bottom: 20px;
    font-size: 24px;
    font-weight: 600;
}

label {
    font-weight: bold;
    display: block; /* Ensures label stays above the input */
    margin-bottom: 8px;
    color: white;
}

.form-group {
    margin-bottom: 20px;
}

input[type="text"], 
input[type="password"] {
    width: 100%;
    padding: 10px;
    border: 1px solid #ccc;
    border-radius: 4px;
    box-sizing: border-box;
    font-size: 16px;
    background-color: #fafafa;
    transition: border-color 0.3s ease;
}

input[type="text"]:focus, 
input[type="password"]:focus {
    border-color: #007BFF; /* Border color change on focus */
    outline: none;
}

input[type="submit"] {
    width: 100%;
    padding: 10px;
    background-color: #007BFF;
    color: white;
    border: none;
    border-radius: 4px;
    font-size: 18px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

input[type="submit"]:hover {
    background-color: #0056b3; /* Darker shade on hover */
}

@media (max-width: 600px) {
    .container {
        width: 90%; /* Adjusts to 90% of the viewport on smaller screens */
    }
}

        
    </style>
</head>
<body>

    <div class="container">
        <h2>User Login</h2>
        <form action="login" method="post">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="text" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <input type="submit" value="Login">
            </div>
        </form>
    </div>

</body>
</html>
    