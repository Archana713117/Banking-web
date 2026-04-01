import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.security.MessageDigest;

public class ForgotPinServlet extends HttpServlet {

    public static String hashPin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(pin.getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                String s = Integer.toHexString(0xff & b);
                if (s.length() == 1) hex.append('0');
                hex.append(s);
            }
            return hex.toString();
        } catch (Exception e) {
            return null;
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String card = request.getParameter("card");
        String newPin = request.getParameter("newpin");

        String hashedPin = hashPin(newPin);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/atmproject",
                "root",
                "Archana@07"
            );

            String query = "UPDATE login SET pin=? WHERE cardnumber=?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, hashedPin);
            ps.setString(2, card);

            int rows = ps.executeUpdate();

            if (rows > 0) {
               response.sendRedirect("login.html");
            } else {
                response.getWriter().println("<h2>Invalid Card Number</h2>");
            }

        } catch (Exception e) {
            response.getWriter().println(e.toString());
        }
    }
}