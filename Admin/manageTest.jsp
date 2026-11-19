<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Manage Tests | PathoCare Admin</title>
    <style>
        body {
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f6f9;
            margin: 0;
            padding: 0;
        }

        .header {
            background-color: #0066ff;
            color: white;
            padding: 15px 25px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header a {
            color: white;
            text-decoration: none;
            background: #0040b8;
            padding: 8px 16px;
            border-radius: 8px;
        }

        .container {
            width: 80%;
            margin: 30px auto;
            background: #fff;
            border-radius: 12px;
            padding: 25px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        h2 {
            color: #333;
            text-align: center;
        }

        form {
            display: flex;
            flex-direction: column;
            gap: 10px;
            width: 60%;
            margin: 0 auto 30px auto;
        }

        input, textarea, button {
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 8px;
            font-size: 15px;
        }

        button {
            background-color: #0066ff;
            color: white;
            font-weight: bold;
            cursor: pointer;
        }

        button:hover {
            background-color: #004dcc;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px;
            border: 1px solid #ddd;
            text-align: center;
        }

        th {
            background-color: #0066ff;
            color: white;
        }

        tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        .action-btn {
            background: #ff3333;
            color: white;
            border: none;
            padding: 6px 10px;
            border-radius: 5px;
            cursor: pointer;
        }

        .action-btn:hover {
            background: #cc0000;
        }
    </style>
</head>
<body>

<div class="header">
    <h2>Manage Tests</h2>
    <a href="adminDashboard.jsp"> Back to Dashboard</a>
</div>

<div class="container">
    <h3>Add New Test</h3>
    <form method="post" action="../AddTestServlet">
        <input type="text" name="test_name" placeholder="Test Name" required>
        <textarea name="description" placeholder="Description" rows="3" required></textarea>
        <input type="number" step="0.01" name="price" placeholder="Price (?)" required>
        <button type="submit">Add Test</button>
    </form>

    <h3>Existing Tests</h3>
    <table>
        <tr>
            <th>ID</th>
            <th>Test Name</th>
            <th>Description</th>
            <th>Price (?)</th>
            <th>Action</th>
        </tr>
        <%
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathocare", "root", "");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM test");

                boolean found = false;
                while (rs.next()) {
                    found = true;
        %>
                    <tr>
                        <td><%= rs.getInt("test_id") %></td>
                        <td><%= rs.getString("test_name") %></td>
                        <td><%= rs.getString("description") %></td>
                        <td><%= rs.getDouble("price") %></td>
                        <td>
                            <form method="post" action="../DeleteTestServlet" style="display:inline;">
                                <input type="hidden" name="test_id" value="<%= rs.getInt("test_id") %>">
                                <button class="action-btn" type="submit">Delete</button>
                            </form>
                        </td>
                    </tr>
        <%
                }
                if(!found){
        %>
                    <tr><td colspan="5">No tests found.</td></tr>
        <%
                }
                con.close();
            } catch(Exception e){
                out.print("<tr><td colspan='5' style='color:red;'>Error: "+ e.getMessage() +"</td></tr>");
            }
        %>
    </table>
</div>

</body>
</html>
