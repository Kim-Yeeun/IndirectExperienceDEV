<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지 수정</title>
</head>
<body>

공지를 수정했습니다.
<br>
${ctxPath = pageContext.request.contextPath ; ''}
<a href="${ctxPath}/post/list.do">[게시글목록보기]</a>
<a href="${ctxPath}/post/read.do?no=${modReq.postNumber}">
[공지내용보기]</a>
</body>
</html>