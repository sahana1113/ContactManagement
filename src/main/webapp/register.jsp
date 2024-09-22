<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }
        .container {
            width: 50%;
            margin: auto;
            padding: 20px;
            background-color: white;
            box-shadow: 0px 0px 10px 0px #aaa;
            margin-top: 50px;
        }
        h2 {
            text-align: center;
        }
        label {
            font-weight: bold;
        }
        .form-group {
            margin-bottom: 15px;
        }
        input[type="text"], input[type="email"], input[type="date"], input[type="tel"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            width: 100%;
            padding: 10px;
            background-color: blue;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .gender-group {
            display: flex;
            justify-content: space-between;
        }
    </style>
</head>
<body>

    <div class="container">
        <h2>User Registration</h2>
        <form action="register" method="post">
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
                <input type="text" id="password" name="password" required>
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
                <input type="submit" value="Register">
            </div>
        </form>
    </div>

</body>
</html>
