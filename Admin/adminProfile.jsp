<%@ page import="java.sql.*, javax.sql.*" %>
<%
    String email = (String)session.getAttribute("adminEmail");
    if(email == null){
        response.sendRedirect("login .jsp");
        return;
    }

    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    String admin_id = "", name = "", phone = "", branch = "", password = "";

    try{
        Class.forName("com.mysql.jdbc.Driver");
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathocare", "root", "");
        ps = con.prepareStatement("SELECT * FROM admin WHERE email=?");
        ps.setString(1, email);
        rs = ps.executeQuery();
        if(rs.next()){
            admin_id = rs.getString("admin_id");
            name = rs.getString("name");
            phone = rs.getString("phone_no");
            branch = rs.getString("branch");
            password = rs.getString("password");
        }
    }catch(Exception e){
        out.println(e);
    }
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Profile</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6f9;
        }
        .profile-card {
            max-width: 500px;
            margin: 60px auto;
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 6px 15px rgba(0,0,0,0.1);
        }
        .profile-card h3 {
            text-align: center;
            font-weight: 600;
            color: #333;
            margin-bottom: 20px;
        }
        .profile-info p {
            font-size: 16px;
            margin: 8px 0;
        }
        .btn-edit {
            background-color: #6c757d;
            color: white;
        }
        .btn-update {
            background-color: #007bff;
            color: white;
        }
        .form-section {
            display: none;
        }
    </style>
</head>
<body>

<div class="profile-card">
    <h3>Admin Profile</h3>

    <!-- Profile Display Section -->
    <div id="view-section" class="profile-info">
        <p><strong>Name:</strong> <%=name%></p>
        <p><strong>Phone No:</strong> <%=phone%></p>
        <p><strong>Branch:</strong> <%=branch%></p>
        <p><strong>Email:</strong> <%=email%></p>
        <p><strong>Password:</strong> ********</p>

        <button class="btn btn-edit mt-3" onclick="enableEdit()">Edit Profile</button>
    </div>

    <!-- Edit Form Section -->
    <div id="edit-section" class="form-section">
<form action="updateAdminProfile.jsp" method="post">
            <input type="hidden" name="admin_id" value="<%=admin_id%>">

            <div class="mb-3">
                <label class="form-label">Phone No</label>
                <input type="text" class="form-control" name="phone_no" value="<%=phone%>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" class="form-control" name="email" value="<%=email%>" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Password</label>
                <input type="password" class="form-control" name="password" value="<%=password%>" required>
            </div>

            <button type="submit" class="btn btn-update">Update Profile</button>
            <button type="button" class="btn btn-secondary" onclick="cancelEdit()">Cancel</button>
        </form>
    </div>
</div>

<script>
    function enableEdit() {
        document.getElementById("view-section").style.display = "none";
        document.getElementById("edit-section").style.display = "block";
    }

    function cancelEdit() {
        document.getElementById("edit-section").style.display = "none";
        document.getElementById("view-section").style.display = "block";
    }
</script>

</body>
</html>
