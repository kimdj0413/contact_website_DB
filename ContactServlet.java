package website;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Contact
 */
@WebServlet("/website/ContactServlet")
public class ContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
    	String userid = LoginServlet.id;
		String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String rel = request.getParameter("relationship");
        String rel_nm="00";
        if (rel.equals("가족")) {rel_nm = "01";}
        if (rel.equals("친구")) {rel_nm = "02";}
        if (rel.equals("회사")) {rel_nm = "03";}
        if (rel.equals("기타")) {rel_nm = "04";}
        String jdbcURL = "jdbc:oracle:thin:@localhost:1521:xe";
        String jdbcUsername = "ora_user";
        String jdbcPassword = "1234";

        String insertQuery = "INSERT INTO "+userid+"(con_id, con_nm, con_num, con_add, con_rel) VALUES (get_seq_id(),'"+name+"','"+phone+"','"+address+"','"+rel_nm+"')";

        try {
            try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
            Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            
            int result = preparedStatement.executeUpdate();
            if (result > 0) {
                response.sendRedirect("/jwbook/website/contact");
            }
        } catch (SQLException e) {
            String errorMessage = "다시 시도해주세요.";
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('" + errorMessage + "'); history.go(-1);</script>");
            out.flush();
            return;
        }
    }
}
