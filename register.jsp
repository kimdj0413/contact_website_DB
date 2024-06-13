<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Orange🍊</title>
    <link rel="stylesheet" href="register.css">
</head>
<body>
<div class="container">
	<h1>회원가입</h1>
    <form id="registerForm" action="RegisterServlet" method="post" onsubmit="return redirectToLogin()">
        <input type="text" name="name" placeholder="이름(한글만)" pattern="[\uac00-\ud7a3]+" required>
        <div class="input-group">
            <input type="text" name="id" placeholder="아이디(영어와 숫자만)" pattern="[A-Za-z0-9]+" required>
            <button type="button" class="check-btn">중복확인</button>
        </div>
        <div id="message" style="color: red;font-size: 12px;margin-left: 10px;"></div>
        <input type="text" name="password" placeholder="비밀번호(영어와 숫자만)" pattern="[A-Za-z0-9]+" required>
        
        <button type="submit" disabled>Register</button>
    </form>
</div>
<script>
	let isIdAvailable = false;
	
	document.getElementById('registerForm').addEventListener('input', function(event) {
	    if (event.target.name === "id") {
	        isIdAvailable = false;
	        document.getElementById('message').textContent = '';
	    }
	    updateSubmitButtonState();
	});
	
	document.querySelector('.check-btn').addEventListener('click', function() {
	    var userId = document.querySelector('input[name="id"]').value;
	    if (userId) {
	        fetch('CheckIdServlet?id=' + encodeURIComponent(userId))
	            .then(response => response.text())
	            .then(data => {
	                if (data === 'true') {
	                    document.getElementById('message').textContent = '이미 사용 중인 아이디입니다.';
	                    isIdAvailable = false;
	                } else {
	                    document.getElementById('message').textContent = '사용 가능한 아이디입니다.';
	                    isIdAvailable = true;
	                }
	                updateSubmitButtonState();
	            })
	            .catch(error => {
	                console.error('Error:', error);
	                document.getElementById('message').textContent = '오류가 발생했습니다. 다시 시도해 주세요.';
	                isIdAvailable = false;
	                updateSubmitButtonState();
	            });
	    } else {
	        document.getElementById('message').textContent = '아이디를 입력해 주세요.';
	        isIdAvailable = false;
	        updateSubmitButtonState();
	    }
	});
	
	function updateSubmitButtonState() {
	    const form = document.getElementById('registerForm');
	    const fields = form.querySelectorAll('input');
	    const button = form.querySelector('button[type="submit"]');
	    const allFilled = Array.from(fields).every(input => input.value);
	    
	    button.disabled = !(allFilled && isIdAvailable);
	}

</script>
</body>
</html>