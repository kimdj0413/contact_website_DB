package website;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/website/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	public static String id;
    
    @SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        id = request.getParameter("id");
        String password = request.getParameter("password");
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "ora_user", "1234");
            String sql = "SELECT * FROM login WHERE ID = '"+id+"' AND PW = '"+password+"'";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
            	String sql1 = "CREATE TABLE "+id+"(\r\n"
            			+ "	  con_id		varchar2(8) PRIMARY KEY \r\n"
            			+ "	, con_nm		VARCHAR2(50)\r\n"
            			+ "	, con_num		varchar2(11)\r\n"
            			+ "	, con_rel		varchar2(2)\r\n"
            			+ "	, con_add		varchar2(50)\r\n"
            			+ "	, con_dt		DATE DEFAULT sysdate\r\n"
            			+ ")";
            	String sql2 = "ALTER TABLE "+id+"\r\n"
            			+ "ADD CONSTRAINT "+id+"fk\r\n"
            			+ "FOREIGN key(con_rel)\r\n"
            			+ "REFERENCES relation(rel_no)";
            	pstmt = conn.prepareStatement(sql1);
            	rs = pstmt.executeQuery();
            	pstmt = conn.prepareStatement(sql2);
            	rs = pstmt.executeQuery();
                response.sendRedirect("/jwbook/website/contact");
            } else {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<script>alert('아이디 혹은 비밀번호가 일치하지 않습니다.');history.back();</script>");
                out.close();
            }
        }catch(java.sql.SQLSyntaxErrorException e) {
        	response.sendRedirect("/jwbook/website/contact");
        }catch (Exception e) {
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
