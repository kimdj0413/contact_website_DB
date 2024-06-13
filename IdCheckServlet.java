package website;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/website/IdCheckServlet")
public class IdCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("id");
        boolean isAvailable = checkUserId(userId);

        request.setAttribute("isAvailable", isAvailable);
        request.setAttribute("userId", userId);
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    private boolean checkUserId(String userId) {
        boolean isAvailable = false;
        String jdbcUrl = "jdbc:oracle:thin:@localhost:1521:xe"; // DB URL
        String dbUser = "ora_user";
        String dbPassword = "1234";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(jdbcUrl, dbUser, dbPassword);
            String query = "SELECT COUNT(*) FROM login WHERE ID = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                isAvailable = resultSet.getInt(1) == 0;
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAvailable;
    }
}