import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class BalanceServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

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

            int balance = 0;

            while (rs.next()) {
                String type = rs.getString("type");
                int amt = Integer.parseInt(rs.getString("amount"));

                if (type.equals("Deposit")) {
                    balance += amt;
                } else {
                    balance -= amt;
                }
            }
            response.setContentType("text/html; charset=UTF-8");
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
            " padding: 30px;" +
            " width: 350px;" +
            " border-radius: 12px;" +
            " box-shadow: 0px 0px 15px rgba(0,0,0,0.2);" +
            " text-align: center;" +
            "}" +

            ".balance {" +
            " font-size: 22px;" +
            " margin-top: 10px;" +
            "}" +

            "a {" +
            " display: block;" +
            " margin-top: 20px;" +
            " text-decoration: none;" +
            " color: #3498db;" +
            "}" +

            "</style>" +
            "</head><body>" +

            "<div class='container'>" +
            "<h2>Account Balance</h2>" +
            "<div class='balance'>&#8377;" + balance + "</div>" +
            "<a href='/dashboard.html'>&larr; Back to Dashboard</a>" +
            "</div>" +

            "</body></html>"
        );
        } catch (Exception e) {
            response.getWriter().println("<h1>Error</h1>");
            response.getWriter().println(e.toString());
        }
    }
}