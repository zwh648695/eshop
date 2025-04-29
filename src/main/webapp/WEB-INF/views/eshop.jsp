<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>eShop 首頁</title>

	<style>
		.container {
			margin: 0 auto;
			padding: 20px;
			position: relative; /* ✅ 定義定位範圍 */
		}
		
		hr {
			margin-top: 20px;
		}
	</style>
	
</head>
<body>

	<div class="container">

		<!-- ✅ 固定在容器右上角的登出按鈕 -->
		<s:form action="logout" method="post" style="position:absolute;top:0;right:0;">
			<s:submit value="登出" style="background-color: #ff4d4d; color: white; border: none; padding: 8px 16px; border-radius: 5px; cursor: pointer; font-weight: bold;" />
		</s:form>

		<h2>🏠 歡迎來到 eShop 系統</h2>

		<!-- 👤 使用者歡迎區 -->
		<c:choose>
			<c:when test="${not empty sessionScope.currentCustomer}">
				<p>
					您好，<c:out value="${sessionScope.currentCustomer.username}" />，歡迎回來！
				</p>
			</c:when>
			
			<c:otherwise>
				<p>您尚未登入</p>
			</c:otherwise>
		</c:choose>

		<hr>

		<!-- 📦 主頁內容 -->
		<p>這裡是首頁內容區域，您可以加入推薦商品、分類、廣告等。</p>

	</div>

</body>
</html>
