package website;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/website/RegisterServlet")
public class RegisterServlet extends HttpServlet {
private static final long serialVersionUID = 1L;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.setCharacterEncoding("UTF-8");
    	String name = request.getParameter("name");
    	String id = request.getParameter("id");
        String password = request.getParameter("password");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
        	response.setCharacterEncoding("UTF-8");
        	response.setContentType("text/html; charset=UTF-8");

            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "ora_user", "1234");
            String sql = "INSERT INTO login (login_id, Name, ID, PW) VALUES (get_seq_login(), '"+name+"', '"+id+"', '"+password+"')";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            response.sendRedirect("login.jsp");
        } catch (SQLIntegrityConstraintViolationException e) {
            response.getWriter().println("아이디를 확인해주세요");
        }    catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}