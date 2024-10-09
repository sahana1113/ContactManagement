
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); 
    response.setHeader("Pragma", "no-cache"); 
    response.setDateHeader("Expires", 0); 
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color:  #ECF0F1;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 600px; 
            padding: 20px;
            background-color: #2C3E50;
            box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
            margin: 40px auto; /* Centers the container with a top margin */
            margin-top:100px;
        }
        h2 {
            text-align: center;
            color: white;
            font-size: 24px;
            margin-bottom: 20px;
            font-weight: bold;
        }
        label {
            font-weight: bold;
            display: block;
            margin-bottom: 8px;
            color: white;
        }
        .form-group {
            margin-bottom: 20px;
        }
        input[type="text"], 
        input[type="email"], 
        input[type="date"], 
        input[type="tel"], 
        input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 16px;
            background-color: #fafafa;
            transition: border-color 0.3s ease;
        }
        input[type="text"]:focus, 
        input[type="email"]:focus, 
        input[type="date"]:focus, 
        input[type="tel"]:focus, 
        input[type="password"]:focus {
            border-color: #007BFF; /* Border color change on focus */
            outline: none;
        }
        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background-color: #007BFF;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 18px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        input[type="submit"]:hover {
            background-color: #0056b3; /* Darker shade on hover */
        }
        .gender-group {
            display: flex;
            justify-content: space-around;
            align-items: center;
        }
        .gender-group input {
            margin-right: 8px;
        }
        .gender-group label {
            margin: 0;
            font-weight: normal;
        }
        @media (max-width: 600px) {
            .container {
                width: 90%;
                margin: 20px auto;
            }
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>User Registration</h2>
        <form action="register" method="post" autocomplete="off">
            <div class="form-group">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
            </div>

            <div class="form-group">
                <label for="password">Set password:</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="gender">Gender:</label>
                <div class="gender-group">
                    <label><input type="radio" name="gender" value="male"> Male</label>
                    <label><input type="radio" name="gender" value="female"> Female</label>
                    <label><input type="radio" name="gender" value="other"> Other</label>
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
                <input type="submit" value="Register">
            </div>
        </form>
    </div>

</body>
</html>
