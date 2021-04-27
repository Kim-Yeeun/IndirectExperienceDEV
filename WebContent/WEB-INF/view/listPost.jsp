<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지 목록</title>
</head>
<body>

<table border="1">
	<tr>
		<td colspan="4"><a ref="write.do">[공지 등록하기]</a></td>
	</tr>
	<tr>
		<td>번호</td>
		<td>제목</td>
		<td>작성자</td>
		<td>조회수</td>
	</tr>
<c:if test="${postPage.hasNoPosts()}">
	<tr>
		<td colspan="4">공지글이 없습니다.</td>
	</tr>
</c:if>
<c:forEach var="post" items="${postPage.content}">
	<tr>
		<td>${post.number}</td>
		<td>
		<a href="read.do?no=${post.number}&pageNo=${postPage.currentPage}">
			<c:out value="${post.title}"/>
		</a>
		</td>
		<td>${post.writerEnt.name}</td>
		<td>${post.readCount}</td>
	</tr>
</c:forEach>
<c:if test="${postPage.hasPosts()}">
	<tr>
		<td colspan="4">
			<c:if test="${postPage.startPage > 5}">
			<a href="list.do?pageNo=${postPage.startPage - 5}">[이전]</a>
			</c:if>
			<c:forEach var="pNo"
					begin="${postPage.startPage}"
					end="${postPage.endPage}">
			<a href="list.do?pageNo=${pNo}">[${pNo}]</a>
			</c:forEach>
			<c:if test="${postPage.endPage < postPage.totalPages}">
			<a href="list.do?pageNo=${postPage.startPage + 5}">[다음]</a>
			</c:if>
		</td>
	</tr>
</c:if>
</table>

</body>
</html>