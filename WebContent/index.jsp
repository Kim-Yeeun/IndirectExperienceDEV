<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="u" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>I.E.DEV</title>
</head>
<body>
<center>
	<h1>Indirect Experience DEV</h1>
	<h3>개발자 꿈나무들을 위한 간접적인 개발 경험을 제공하는 웹사이트입니다.</h3>
</center>
<hr>
<br>
<u:isLogin>
<p align="right">
	${authUser.name}님, 안녕하세요.
	<a href="logout.do">[로그아웃]</a>
	<a href="changeIndPwd.do">[암호변경]</a>
</p>
</u:isLogin>
<u:notLogin>
<p align="right">
	기업회원
	<a href="joinEnt.do">[회원가입]</a>
	<a href="loginEnt.do">[로그인]</a>
</p>
<p align="right">
	개인회원
	<a href="joinInd.do">[회원가입]</a>
	<a href="loginInd.do">[로그인]</a>
</p>
</u:notLogin>
</body>
</html>