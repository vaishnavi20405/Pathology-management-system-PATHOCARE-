<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Upload Test Report</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-4">
    <h2 class="text-center mb-4">Upload Patient Test Report</h2>

    <!-- ✅ Show Success / Error Message -->
    <%
        String msg = request.getParameter("msg");
        if("sent".equals(msg)){
    %>
        <div class="alert alert-success">✅ Report uploaded & emailed successfully!</div>
    <% } else if("notfound".equals(msg)){ %>
        <div class="alert alert-warning">⚠️ Patient ID not found!</div>
    <% } else if("error".equals(msg)){ %>
        <div class="alert alert-danger">❌ Something went wrong. Please try again.</div>
    <% } %>

    <!-- ✅ Step 1: Admin enters Patient ID -->
    <form action="<%= request.getContextPath() %>/FetchPatientDetailsServlet" method="post" class="card p-4 shadow-sm mb-4">
        <h5>Step 1: Enter Patient ID</h5>
        <div class="mb-3">
            <label class="form-label">Patient ID:</label>
            <input type="number" name="patientId" class="form-control" placeholder="Enter Patient ID" required>
        </div>
        <button type="submit" class="btn btn-primary w-100">Fetch Patient Details</button>
    </form>

    <!-- ✅ Step 2: Show upload area only if patient data found -->
    <%
        String patientEmail = (String) request.getAttribute("patientEmail");
        String patientName = (String) request.getAttribute("patientName");
        List<String> testList = (List<String>) request.getAttribute("testList");

        if(patientEmail != null && testList != null){
    %>

    <form action="<%= request.getContextPath() %>/UploadReportServlet" method="post" enctype="multipart/form-data" class="card p-4 shadow-sm mb-4">
        <h5>Step 2: Upload & Send Report</h5>

        <input type="hidden" name="patientId" value="<%= request.getAttribute("patientId") %>">

        <div class="mb-3">
            <label class="form-label">Patient Name:</label>
            <input type="text" class="form-control" value="<%= patientName %>" readonly>
        </div>

        <div class="mb-3">
            <label class="form-label">Patient Email:</label>
            <input type="email" name="email" class="form-control" value="<%= patientEmail %>" readonly>
        </div>

        <div class="mb-3">
            <label class="form-label">Select Test:</label>
            <select name="testName" class="form-select" required>
                <% for(String t : testList){ %>
                    <option value="<%= t %>"><%= t %></option>
                <% } %>
            </select>
        </div>

        <div class="mb-3">
            <label class="form-label">Upload Report (PDF Only):</label>
            <input type="file" name="reportFile" class="form-control" accept="application/pdf" required>
        </div>

        <button type="submit" class="btn btn-success w-100">
            Send Email
        </button>
    </form>

    <% } %>

</div>

</body>
</html>
