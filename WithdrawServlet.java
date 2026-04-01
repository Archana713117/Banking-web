import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class WithdrawServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");

        String amountStr = request.getParameter("amount");
        int amount = Integer.parseInt(amountStr);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/atmproject",
                "root",
                "your_password"
            );

            HttpSession session = request.getSession(false);

            if (session == null) {
                response.getWriter().println("Session expired! Please login again.");
                return;
            }

            String card = (String) session.getAttribute("card");

            // 🔥 STEP 1: Calculate current balance
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(
                "SELECT SUM(CASE WHEN type='Deposit' THEN amount ELSE -amount END) AS balance FROM bank WHERE card='" + card + "'"
            );

            int balance = 0;

            if (rs.next()) {
                balance = rs.getInt("balance");
            }

            // 🔥 STEP 2: Check balance
            if (balance < amount) {

                response.getWriter().println(
                    "<html><head>" +
                    "<script>" +
                    "alert('Insufficient Balance! Available balance:" + balance + "');" +
                    "window.location='withdraw.html';" +
                    "</script>" +
                    "</head></html>"
                );

                return;
            }

            // 🔥 STEP 3: Perform withdraw
            String query = "INSERT INTO bank (card, date, type, amount) VALUES (?, NOW(), ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, card);
            ps.setString(2, "Withdraw");
            ps.setString(3, amountStr);

            ps.executeUpdate();

            response.getWriter().println(
                "<html><head>" +
                "<script>" +
                "alert('Withdrawal Successful!');" +
                "window.location='dashboard.html';" +
                "</script>" +
                "</head></html>"
            );

        } catch (Exception e) {
            response.getWriter().println("<h2>Error</h2>");
            response.getWriter().println(e.toString());
        }
    }
}
