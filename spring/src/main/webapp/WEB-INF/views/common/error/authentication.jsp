<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>권한 부족에러 페이지</title>
</head>
<body>
	<h3 style="color:red"><%=exception.getMessage() %></h3>
	<h4>3초후 메인화면으로 이동합니다.</h4>
	<script>
		setTimeout(()=>{  // 3초뒤 메인화면으로 이동
			location.replace("${pageContext.request.contextPath}");
		},3000)
	</script>
	
	
	
</body>
</html>