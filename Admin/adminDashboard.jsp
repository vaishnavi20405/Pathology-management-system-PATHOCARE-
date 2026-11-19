<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Get admin name from session
    String adminName = (String) session.getAttribute("adminName");
    if (adminName == null) {
        // If session expired or not logged in, redirect to login
        response.sendRedirect("../index.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard - PathoCare</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            background-color: #f7f9fc;
        }
        /* Blue top bar */
        .top-bar {
            background-color: #0d6efd;
            color: white;
            padding: 10px 20px;
            font-size: 1.4rem;
            font-weight: bold;
            letter-spacing: 0.5px;
        }
        /* Black welcome strip */
        .welcome-bar {
            background-color: #000;
            color: white;
            padding: 10px 20px;
            font-size: 1rem;
            font-weight: 500;
        }
        /* Dark gray strip for profile & logout */
        .menu-bar {
            background-color: #1f2c3d;
            color: white;
            padding: 12px 20px;
        }
        .menu-bar a {
            color: white;
            text-decoration: none;
            font-weight: 500;
        }
        .menu-bar a:hover {
            text-decoration: underline;
        }
        .search-bar input {
            border-radius: 25px;
            padding-left: 15px;
        }
        .card {
            border-radius: 15px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.08);
            transition: transform 0.2s;
        }
        .card:hover {
            transform: translateY(-4px);
        }
        .card-title {
            font-weight: 600;
        }
        .btn-custom {
            border-radius: 8px;
            font-weight: 500;
        }
    </style>
</head>
<body>

<!-- 🔹 First Blue Strip -->
<div class="top-bar">
    &nbsp;PathoCare Admin
</div>

<!-- ⚫ Black Welcome Strip -->
<div class="welcome-bar">
    Welcome, <%= adminName %> 👋
</div>

<!-- 🔹 Gray Menu Strip -->
<nav class="menu-bar d-flex justify-content-end align-items-center">
    <div class="me-3">
        <a href="adminProfile.jsp" class="me-4">
            <i class="bi bi-pencil-square"></i>  Profile
        </a>
        <a href="../index.jsp">
            <i class="bi bi-box-arrow-right"></i> Logout
        </a>
    </div>
</nav>

<!-- 🔍 Patient Search Bar -->
<div class="container mt-4 text-center">
  <div class="row justify-content-center">
    <div class="col-md-6 search-bar">
      <form action="../SearchPatientServlet" method="get" class="d-flex justify-content-center" autocomplete="off">
        <input type="text" name="searchName" id="searchInput" placeholder="Enter patient name..." list="patientList" class="form-control me-2" required>
        <datalist id="patientList"></datalist>
        <button type="submit" class="btn btn-primary">Search</button>
      </form>
    </div>
  </div>
</div>


<!-- 🧩 Dashboard Cards -->
<div class="container mt-5">
    <!-- 🔹 First Row -->
    <div class="row justify-content-center g-4">
        <!-- View Appointments -->
        <div class="col-md-4">
            <div class="card text-center p-4">
                <h5 class="card-title mb-3">📅 View Appointments</h5>
                <p class="text-muted">Check all scheduled patient appointments.</p>
                <a href="viewAppointments.jsp" class="btn btn-primary btn-custom px-4">View</a>
            </div>
        </div>

        <!-- Manage Tests -->
        <div class="col-md-4">
            <div class="card text-center p-4">
                <h5 class="card-title mb-3">🧪 Manage Tests</h5>
                <p class="text-muted">Add, update, or delete diagnostic tests.</p>
                <a href="manageTest.jsp" class="btn btn-primary btn-custom px-4">Manage</a>
            </div>
        </div>
    </div>

    <!-- 🔹 Second Row -->
    <div class="row justify-content-center g-4 mt-3">
        <!-- Upload Reports -->
        <div class="col-md-4">
            <div class="card text-center p-4">
                <h5 class="card-title mb-3">📄 Upload Reports</h5>
                <p class="text-muted">Upload and manage patient test reports.</p>
                <a href="uploadReport.jsp" class="btn btn-primary btn-custom px-4">Upload</a>
            </div>
        </div>

        <!-- View Contact Messages -->
        <div class="col-md-4">
            <div class="card text-center p-4">
                <h5 class="card-title mb-3">📬 View Contact Messages</h5>
                <p class="text-muted">Check messages received from users.</p>
                <a href="viewMessage.jsp" class="btn btn-primary btn-custom px-4">View</a>
            </div>
        </div>
    </div>
</div>


<!-- Footer -->
<footer class="text-center mt-5 mb-3 text-muted">
    &copy; 2025 Pathocare Diagnostics
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">



</body>

</html>
