import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class PinChangeServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.getWriter().println("Session expired! Please login again.");
            return;
        }

        String oldPin = (String) session.getAttribute("pin");
        String newPin = request.getParameter("newpin");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/atmproject",
                "root",
                "Archana@07"
            );

            // 🔥 Update in login table
            String card = (String) session.getAttribute("card");

            String query1 = "UPDATE login SET pin=? WHERE cardnumber=?";
            PreparedStatement ps1 = conn.prepareStatement(query1);

            ps1.setString(1, newPin);
            ps1.setString(2, card);
            ps1.executeUpdate();

            /* 🔥 Update in bank table
            String query2 = "UPDATE bank SET pin=? WHERE pin=?";
            PreparedStatement ps2 = conn.prepareStatement(query2);
            ps2.setString(1, newPin);
            ps2.setString(2, oldPin);
            ps2.executeUpdate();*/

            // 🔥 Update session
            session.setAttribute("pin", newPin);

            response.getWriter().println("<h1>PIN Changed Successfully!</h1>");

        } catch (Exception e) {
            response.getWriter().println("<h1>Error</h1>");
            response.getWriter().println(e.toString());
        }
    }
}