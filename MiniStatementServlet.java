import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class MiniStatementServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter(); 

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.getWriter().println("Session expired! Please login again.");
            return;
        }

        String pin = (String) session.getAttribute("card");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/atmproject",
                "root",
                "Archana@07"
            );

            String query = "SELECT * FROM bank WHERE card=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, pin);

            ResultSet rs = ps.executeQuery();

            response.getWriter().println(
            "<!DOCTYPE html>" +
            "<html><head>" +
            "<meta charset='UTF-8'>" +

            "<style>" +
            "body {" +
            " font-family: Arial;" +
            " background: url(\"money.jpg\") no-repeat center center fixed;" +
            " background-size: cover;" +
            " display: flex;" +
            " justify-content: center;" +
            " align-items: center;" +
            " height: 100vh;" +
            " margin: 0;" +
            "}" +

            ".container {" +
            " background: rgba(255,255,255,0.9);" +
            " padding: 25px;" +
            " width: 500px;" +
            " border-radius: 12px;" +
            " box-shadow: 0px 0px 15px rgba(0,0,0,0.2);" +
            " text-align: center;" +
            "}" +

            "table {" +
            " width: 100%;" +
            " border-collapse: collapse;" +
            " margin-top: 15px;" +
            "}" +

            "th, td {" +
            " padding: 10px;" +
            " border-bottom: 1px solid #ccc;" +
            "}" +

            "th {" +
            " background-color: #3498db;" +
            " color: white;" +
            "}" +

            "tr:hover {" +
            " background-color: #f2f2f2;" +
            "}" +

            "a {" +
            " display: block;" +
            " margin-top: 15px;" +
            " text-decoration: none;" +
            " color: #3498db;" +
            "}" +

            "</style>" +
            "</head><body>" +

            "<div class='container'>" +
            "<h2>Mini Statement</h2>" +

            "<table>" +
            "<tr><th>Date</th><th>Type</th><th>Amount</th></tr>"
            );
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("date") + "</td>");
                out.println("<td>" + rs.getString("type") + "</td>");
                out.println("<td>&#8377;" + rs.getString("amount") + "</td>");
                out.println("</tr>");
            }

            response.getWriter().println(
            "</table>" +
            "<a href='/dashboard.html'>Back to Dashboard</a>" +
            "</div></body></html>"
            );

        } catch (Exception e) {
            response.getWriter().println("<h1>Error</h1>");
            response.getWriter().println(e.toString());
        }
    }
}