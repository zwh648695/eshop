<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登出成功</title>
</head>
<body>

	<script>
        alert("您已成功登出！");
        window.location.href = "<c:url value='/index.jsp' />"; // ✅ 跳轉到首頁（你的 index.jsp）
    </script>

</body>
</html>