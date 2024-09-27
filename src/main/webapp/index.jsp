<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="sessionValidation.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #ECF0F1;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            margin-bottom:110px;
            text-align: center;
            background-color: white;
            padding: 50px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            width: 400px;
        }
        h1 {
            color: #2C3E50;
            margin-bottom: 40px;
            font-size: 28px;
        }
        .button {
            display: inline-block;
            padding: 15px 40px;
            margin-top: 20px;
            font-size: 18px;
            color: white;
            background-color: #3498DB;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }
        .button:hover {
            background-color: #2980B9;
        }
        .register-btn {
            background-color: #2ECC71;
        }
        .register-btn:hover {
            background-color: #27AE60;
        }
        @media (max-width: 768px) {
            .container {
                width: 90%;
                padding: 30px;
            }
            h1 {
                font-size: 24px;
            }
            .button {
                padding: 12px 30px;
                font-size: 16px;
            }
        }
    </style>
</head>
<body>

    <div class="container">
        <h1>Welcome to Our Contact Management App</h1>
        <a href="login.jsp" class="button">Have an Account? Log In</a>
        <a href="register.jsp" class="button register-btn">Don't have an Account? Register</a>
    </div>

</body>
</html>
