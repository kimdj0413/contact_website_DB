<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Orange🍊</title>
    <link rel="stylesheet" type="text/css" href="contact.css">
	<script>
        document.addEventListener("DOMContentLoaded", function() {
            document.querySelector('form[action="ListServlet"] button[type="button"]').addEventListener("click", function() {
                fetch('ListServlet', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                })
                .then(response => response.text())
                .then(data => {
                    const container = document.querySelector('.container');
                    const existingResultDiv = container.querySelector('.result');
                    if (existingResultDiv) {
                        container.removeChild(existingResultDiv);
                    }
                    const resultDiv = document.createElement('div');
                    resultDiv.classList.add('result');
                    resultDiv.innerHTML = data;
                    container.appendChild(resultDiv);
                })
                .catch((error) => {
                    console.error('Error:', error);
                });
            });
        });
        function editContact(contactId,name,num,add) {
        	console.log("hi");
    	    if (confirm('정말로 이 연락처를 수정하시겠습니까?')) {
    	    	const xhr = new XMLHttpRequest();
    	        xhr.open("POST", "editContact.jsp", true);
    	        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    	        xhr.onreadystatechange = function() {
    	            if (xhr.readyState == 4 && xhr.status == 200) {
    	                // 서버 응답이 성공적일 때 처리
    	                alert('연락처를 수정 후 추가를 눌러주세요.');
    	            }
    	        };
    	        xhr.send("id=" + contactId);
    	        document.getElementById('nameInput').value = name;
    	        document.getElementById('phoneInput').value = num;
    	        document.getElementById('addressInput').value = add;
    	    }
    	}
	function deleteContact(contactId) {
	    if (confirm('정말로 이 연락처를 삭제하시겠습니까?')) {
	        const xhr = new XMLHttpRequest();
	        xhr.open("POST", "deleteContact.jsp", true);
	        xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	        xhr.onreadystatechange = function() {
	            if (xhr.readyState == 4 && xhr.status == 200) {
	                
	                alert('연락처가 삭제되었습니다.');
	            }
	        };
	        xhr.send("id=" + contactId);
	    }
	}
	</script>
</head>
<body>
	<div class="content-wrapper">
		<img src="orange.png" alt="Logo" class="logo">
	    <form action="ContactServlet" method="post">
	            <input type="text" id="nameInput" name="name" placeholder="이름" autocomplete="name" required>
	            <input type="text" id="phoneInput" name="phone" placeholder="번호(숫자만)" autocomplete="num" required>
	            <input type="text" id="addressInput" name="address" placeholder="주소" autocomplete="add" required>
	            <select name="relationship">
	                <option value="가족">가족</option>
	                <option value="친구">친구</option>
	                <option value="회사">회사</option>
	                <option value="기타">기타</option>
	            </select>
	            <button type="submit">추가</button>
	        </form>
	        <form action="ListServlet" method="post">
	        	<button type="button">조회</button>
	        </form>
	    <div class="container">
	    </div>
    </div>
</body>
</html>
