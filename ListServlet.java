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

@WebServlet("/website/ListServlet")
public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = LoginServlet.id;
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 데이터베이스 연결 설정
        	response.setCharacterEncoding("UTF-8");
        	response.setContentType("text/html; charset=UTF-8");

            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "ora_user", "1234");
            
            // 쿼리 실행
            String sql = "SELECT * FROM "+id;
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            // HTML 테이블 생성
            out.println("<div class='card-container'>");
        	String rel ="";
            while (rs.next()) {
                // 각 결과 행을 HTML 카드 형식으로 추가
            	rel = rs.getString("CON_REL");
            	if (rel.equals("01")) {rel = "가족";}
            	if (rel.equals("02")) {rel = "친구";}
            	if (rel.equals("03")) {rel = "회사";}
            	if (rel.equals("04")) {rel = "기타";}
                out.println("<div class='card'>");
                out.println("<h3>" + rs.getString("CON_NM") + "</h3>");
                out.println("<p>번호: " + rs.getString("CON_NUM") + "</p>");
                out.println("<p>관계: " + rel + "</p>");
                out.println("<p>주소: " + rs.getString("CON_ADD") + "</p>");
                out.println("<div class='card-buttons'>");
                out.println("<button onclick='editContact(" 
                	    + rs.getInt("CON_ID") + ", \"" 
                	    + rs.getString("CON_NM") + "\", \"" 
                	    + rs.getString("CON_NUM") + "\", \"" 
                	    + rs.getString("CON_ADD") + "\")'>수정</button>");
                out.println("<button onclick='deleteContact(" + rs.getInt("CON_ID")+ ")'>삭제</button>");
                out.println("</div>");
                out.println("</div>");
            }

            // 카드 컨테이너 종료
            out.println("</div>");
            
            // 자원 정리
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("데이터베이스 연결 중 에러가 발생했습니다.");
        } finally {
            out.close();
        }
    }
}