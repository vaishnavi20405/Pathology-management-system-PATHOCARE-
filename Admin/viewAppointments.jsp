<%@ page import="java.sql.*" %>
<%
Class.forName("com.mysql.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathocare", "root", "");
Statement st = con.createStatement();
ResultSet rs = st.executeQuery("SELECT * FROM bookings ORDER BY booking_id DESC");
%>

<!DOCTYPE html>
<html>
<head>
  <title>Admin ? View Appointments</title>
  <style>
    body {
      font-family: "Poppins", sans-serif;
      background-color: #f4f6f9;
      margin: 0;
      padding: 0;
    }

    h2 {
      text-align: center;
      background: #0073e6;
      color: white;
      padding: 15px 0;
      margin: 0;
      font-size: 22px;
      letter-spacing: 0.5px;
    }

    .container {
      width: 90%;
      margin: 30px auto;
      background: white;
      border-radius: 10px;
      box-shadow: 0 4px 12px rgba(0,0,0,0.1);
      padding: 20px;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 10px;
      border-radius: 10px;
      overflow: hidden;
    }

    th {
      background: #0073e6;
      color: white;
      text-transform: uppercase;
      padding: 12px;
      font-size: 14px;
    }

    td {
      padding: 10px;
      text-align: center;
      border-bottom: 1px solid #ddd;
      font-size: 14px;
    }

    tr:hover {
      background-color: #f1f8ff;
      transition: 0.2s;
    }

    select {
      padding: 5px 8px;
      border-radius: 5px;
      border: 1px solid #ccc;
      background-color: #fff;
    }

    button {
      background: #0073e6;
      color: white;
      padding: 6px 12px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-size: 13px;
      transition: 0.3s;
    }

    button:hover {
      background: #005bb5;
    }

    td form {
      display: flex;
      gap: 6px;
      justify-content: center;
    }

    .status {
      font-weight: bold;
      border-radius: 5px;
      padding: 4px 10px;
    }

    .status.Pending {
      background: #fff3cd;
      color: #856404;
    }

    .status.Approved {
      background: #d4edda;
      color: #155724;
    }

    .status.Rejected {
      background: #f8d7da;
      color: #721c24;
    }

    td[colspan] {
      color: red;
      text-align: center;
      font-weight: bold;
      background: #fff7f7;
    }

    .topbar {
      display: flex;
      justify-content: space-between;
      align-items: center;
      background: #0073e6;
      color: white;
      padding: 15px 40px;
    }

    .topbar a {
      background: white;
      color: #0073e6;
      text-decoration: none;
      padding: 8px 16px;
      border-radius: 6px;
      font-weight: bold;
      transition: 0.3s;
    }

    .topbar a:hover {
      background: #eaf2ff;
    }

  </style>
</head>
<body>

  <div class="topbar">
    <h2>All Bookings</h2>
    <a href="adminDashboard.jsp">Back to Dashboard</a>
  </div>

  <div class="container">
    <table>
      <tr>
        <th>ID</th>
        <th>Patient Name</th>
        <th>Age</th>
        <th>Gender</th>
        <th>Test Name</th>
        <th>Date</th>
        <th>Time</th>
        <th>Status</th>
        <th>Action</th>
      </tr>

      <%
      boolean hasData = false;
      while(rs.next()){
          hasData = true;
      %>
        <tr>
          <td><%= rs.getInt("booking_id") %></td>
          <td><%= rs.getString("patient_name") %></td>
          <td><%= rs.getString("age") %></td>
          <td><%= rs.getString("gender") %></td>
          <td><%= rs.getString("test_name") %></td>
          <td><%= rs.getString("date") %></td>
          <td><%= rs.getString("time") %></td>
          <td>
            <span class="status <%= rs.getString("status") %>">
              <%= rs.getString("status") %>
            </span>
          </td>
          <td>
            <form action="../UpdateStatusServlet" method="post">
              <input type="hidden" name="booking_id" value="<%= rs.getInt("booking_id") %>">
              <select name="status">
                <option value="Approved">Approve</option>
                <option value="Rejected">Reject</option>
              </select>
              <button type="submit">Update</button>
            </form>
          </td>
        </tr>
      <%
      }
      if(!hasData){
      %>
          <tr><td colspan="9">No bookings found!</td></tr>
      <%
      }
      rs.close();
      con.close();
      %>
    </table>
  </div>

</body>
</html>
