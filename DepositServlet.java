import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
public class DepositServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.getWriter().println("Session expired! Please login again.");
            return;
        }

        String card = (String) session.getAttribute("card");

        String amountStr = request.getParameter("amount");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/atmproject",
                "root",
                "your_password"
            );

            String query = "INSERT INTO bank (card, date, type, amount) VALUES (?, NOW(), ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, card);
            ps.setString(2, "Deposit");
            ps.setString(3, amountStr);
            ps.executeUpdate();

            response.getWriter().println("<h1>Deposit Successful!</h1>");

        } catch (Exception e) {
            response.getWriter().println("<h1>Error</h1>");
            response.getWriter().println(e.toString());
        }
    }
}
