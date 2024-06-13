<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Orange🍊</title>
    <link rel="stylesheet" href="login.css">
</head>
<body>
<div class="container">
    <img src="orange.png" alt="로고" class="logo">
    <form action="LoginServlet" method="post">
        <input type="text" name="id" placeholder="아이디">
        <input type="password" name="password" placeholder="비밀번호">
        <div class="buttons">
            <button type="submit">로그인</button>
            <button type="button" onclick="location.href='/jwbook/website/register'">회원가입</button>
        </div>
    </form>
</div>
</body>
</html>
