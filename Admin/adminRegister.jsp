<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Signup</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .container {
            width: 400px;
            margin: 80px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
        }
        input, select {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
            border: 1px solid #ccc;
        }
        button {
            width: 100%;
            background: #007bff;
            border: none;
            padding: 10px;
            color: white;
            font-size: 16px;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background: #0056b3;
        }
        p {
            text-align: center;
        }
        a {
            color: purple;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Admin Signup</h2>

    <!-- ✅ Fixed action to include context path -->
    <form method="post" action="${pageContext.request.contextPath}/RegisterAdminServlet">

        <label>Admin ID</label>
        <input type="text" name="adminId" placeholder="Enter Admin ID" required>

        <label>Full Name</label>
        <input type="text" name="name" placeholder="Enter your full name" required>

        <label>Branch</label>
        <select name="branch" required>
            <option value="">Select Branch</option>
            <option value="Bhandup">Bhandup</option>
            <option value="Vidyavihar">Vidyavihar</option>
            <option value="Mulund">Mulund</option>
            <option value="Ghatkopar">Ghatkopar</option>
            <option value="Thane">Thane</option>
        </select>

        <label>Phone Number</label>
        <input type="text" name="phone" placeholder="Enter phone number" required>

        <label>Email</label>
        <input type="email" name="email" placeholder="Enter email" required>

        <label>Password</label>
        <input type="password" name="password" placeholder="Enter password" required>

        <button type="submit">Register</button>
    </form>

    <p>Already have an account? <a href="login.jsp">Login</a></p>
</div>
</body>
</html>
