<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기업회원 가입하기</title>
</head>
<body>
<h3>기업회원가입</h3>
<form action="joinEnt.do" method="post">
<p>
	아이디 : <input type="text" name="id" value="${param.id}">
	<c:if test="${errors.id}">ID를 입력하세요.</c:if>
	<c:if test="${errors.duplicateId}">이미 사용중인 아이디입니다.</c:if>
</p>
<p>
	이름 : <input type="text" name="name" value="${param.name}">
	<c:if test="${errors.name}">이름을 입력하세요.</c:if>
</p>
<p>
	암호 : <input type="password" name="password">
	<c:if test="${errors.password}">암호를 입력하세요.</c:if>
</p>
<p>
	암호 확인 : <input type="password" name="confirmPassword">
	<c:if test="${errors.confirmPassword}">확인을 입력하세요.</c:if>
	<c:if test="${errors.notMatch}">암호와 확인이 일치하지 않습니다.</c:if>
</p>
<p>
	이메일 : <input type="email" name="email">
	<c:if test="${errors.email}">이메일을 입력하세요.</c:if>
</p>
<p>
	기업명 : <input type="text" name="entname">
	<c:if test="${errors.entname}">기업명을 입력하세요.</c:if>
</p>
<input type="submit" value="가입">
</form>
</body>
</html>