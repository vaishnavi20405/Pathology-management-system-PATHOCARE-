<%@ page import="java.sql.*" %>
<%
    String email = request.getParameter("email");
    String phone_no = request.getParameter("phone_no");
    String password = request.getParameter("password");
    String admin_id = request.getParameter("admin_id");

    try {
        Class.forName("com.mysql.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pathocare", "root", "");

        PreparedStatement ps = con.prepareStatement(
            "UPDATE admin SET phone_no=?, email=?, password=? WHERE admin_id=?");
        ps.setString(1, phone_no);
        ps.setString(2, email);
        ps.setString(3, password);
        ps.setString(4, admin_id);

        int updated = ps.executeUpdate();

        if (updated > 0) {
            response.sendRedirect("adminProfile.jsp?msg=Profile Updated Successfully");
        } else {
            response.sendRedirect("adminProfile.jsp?msg=Update Failed");
        }

        con.close();
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("adminProfile.jsp?msg=Error Updating Profile");
    }
%>
