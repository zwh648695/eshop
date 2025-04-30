<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登入成功</title>

	<!-- ✅ HTML 備援跳轉（若 JS 失效） -->
	<meta http-equiv="refresh" content="2; url=<c:url value='/eshop?currentPage=1' />" />
	
	<!-- ✅ 引入 Toastify 樣式 -->
	<link rel="stylesheet" type="text/css" href="public/css/toastify.min.css">

</head>
<body style="text-align:center; margin-top:100px">

	<!-- 可以留個 loading 圖示或簡單訊息 -->
	<h3 style="color: #888;">登入中，請稍候...</h3>

	<!-- ✅ Toast 訊息 & 自動跳轉 -->
	<script src="<c:url value='/public/js/toastify.min.js' />"></script>
	
	<script>
		// 顯示登入成功提示
		Toastify({
			text: "✅ 登入成功，歡迎使用 eShop 系統！",
			duration: 3000,
			gravity: "top",
			position: "center"
		}).showToast(); // ← 這裡才是「顯示」通知的動作

		// JavaScript 跳轉（與 meta 保持一致）
		setTimeout(function () {
			<!-- window.location.href = "<c:url value='/eshop' />"; -->
			
			window.location.href = "<c:url value='/eshop?currentPage=1' />";
		}, 2000);
	</script>

</body>
</html>
