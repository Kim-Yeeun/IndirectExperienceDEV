<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기업 프로젝트 공지 등록</title>
</head>
<body>

공지를 등록했습니다.
<br>
${ctxPath = pageContext.request.contextPath ; ''}
<a href="${ctxPath}/post/listPost.do">[목록 보기]</a>
<a href="${ctxPath}/post/readPost.do?no=${newPostNo}">[내용 보기]</a>
</body>
</html>