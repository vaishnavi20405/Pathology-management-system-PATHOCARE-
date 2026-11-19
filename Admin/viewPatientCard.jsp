<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Patient Details</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f7f9fc;
            font-family: 'Poppins', sans-serif;
        }
        .card {
            margin: 80px auto;
            max-width: 420px;
            border-radius: 15px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .card-title {
            color: #0d6efd;
            font-weight: 600;
        }
        .emoji {
            font-size: 1.3rem;
        }
    </style>
</head>
<body>

<div class="card p-4 text-center">
    <h4 class="card-title"><span class="emoji">👤</span> Patient Details</h4>
    <hr>

    <% 
        String name = (String) request.getAttribute("patientName");
        Boolean notfound = (Boolean) request.getAttribute("notfound");
        String error = (String) request.getAttribute("error");
    %>

    <% if (error != null) { %>
        <div class="alert alert-warning mt-3"><%= error %></div>
    <% } else if (notfound != null && notfound) { %>
        <div class="alert alert-danger mt-3">No patient found with that name.</div>
    <% } else if (name != null) { %>
        <p><b>Name:</b> <%= name %></p>
        <p><b>Age:</b> <%= request.getAttribute("patientAge") %></p>
        <p><b>Gender:</b> <%= request.getAttribute("patientGender") %></p>
        <p><b>Contact:</b> <%= request.getAttribute("patientContact") %></p>
        <p><b>Email:</b> <%= request.getAttribute("patientEmail") %></p>
    <% } else { %>
        <div class="alert alert-info mt-3">Please search for a patient to view details.</div>
    <% } %>

    <a href="Admin/adminDashboard.jsp" class="btn btn-primary mt-3">Back</a>
</div>

</body>
</html>
