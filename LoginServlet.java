import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import java.security.MessageDigest;

public class LoginServlet extends HttpServlet {

    //SHA-256 hash function
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

        response.setContentType("text/html; charset=UTF-8");

        String card = request.getParameter("card");
        String pin = request.getParameter("pin");

        //Hash the entered PIN
        String hashedPin = hashPin(pin);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/atmproject",
                "root",
                "Archana@07"
            );

            String query = "SELECT * FROM login WHERE cardnumber=? AND pin=?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, card);
            ps.setString(2, hashedPin);  //use hashed pin

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                // ✅ Create session
                HttpSession session = request.getSession();
                session.setAttribute("pin", hashedPin); // store hashed pin
                session.setAttribute("card", card);

                response.sendRedirect("dashboard.html");

            } else {
                response.getWriter().println("<h2>Invalid Credentials!</h2>");
            }

        } catch (Exception e) {
            response.getWriter().println("<h2>Error</h2>");
            response.getWriter().println(e.toString());
        }
    }
}