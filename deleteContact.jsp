<%@page import="website.LoginServlet"%>
<%@page import="java.sql.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String userid = LoginServlet.id;
    String contactId = request.getParameter("id");

    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "ora_user", "1234");

        String sql = "DELETE FROM "+userid+" WHERE CON_ID = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, contactId);
        int result = pstmt.executeUpdate();

        if (result > 0) {
            out.print("success");
        } else {
            out.print("failure");
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.print("error");
    } finally {
        if (pstmt != null) try { pstmt.close(); } catch (Exception e) {}
        if (conn != null) try { conn.close(); } catch (Exception e) {}
    }
%>