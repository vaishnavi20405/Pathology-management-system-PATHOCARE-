/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package patho;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/UpdateStatusServlet")

public class UpdateStatusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int booking_id = Integer.parseInt(request.getParameter("booking_id"));
        String status = request.getParameter("status");

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/pathocare", "root", "");

            PreparedStatement ps = con.prepareStatement(
                "UPDATE bookings SET status=? WHERE booking_id=?");
            ps.setString(1, status);
            ps.setInt(2, booking_id);

            ps.executeUpdate();
            con.close();

            response.sendRedirect("Admin/viewAppointments.jsp");

        } catch (Exception e) {
            e.printStackTrace(response.getWriter());
        }
    }
}
