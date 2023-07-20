<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메세지</title>
</head>
<body>
	<script>
		alert("${msg}"); // "로 안하면 변수명이됨"
		location.replace("${pageContext.request.contextPath}${loc}");
	</script>
</body>
</html>