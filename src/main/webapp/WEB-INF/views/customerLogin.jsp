<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>會員登入</title>
<style>

	.login-box {
		border: 1px solid #ccc;
		padding: 10px;
		border-radius: 10px;
		display: inline-block;
		background-color: #f9f9f9;
		box-shadow: 0 0 8px rgba(0,0,0,0.1);
		/* ✅ 加上這一行來設定寬度 */
		width: 300px;
	}
	
	/* Struts2 預設會把錯誤訊息包在 <span class="errorMessage"> 或 <li class="errorMessage"> 中 */
	span.errorMessage, li.errorMessage {
		color: red;
	}
	
</style>
</head>
<body>

	<!-- ✅ 若已登入，就不要再讓他登入，直接導去首頁 -->
	<c:if test="${not empty sessionScope.currentCustomer}">
	    <script>
	        alert("您已登入，將返回sShop主頁！");
	        window.location.href = "eshop";
	    </script>
	</c:if>

	<div align="center">
		<div class="login-box">
		
			<h2>會員登入</h2>
			<hr>
	
			<!-- 顯示登入失敗的錯誤訊息（來自 addActionError）-->
			<s:if test="hasActionErrors()">
				<div style="color:red;">
					<s:actionerror />
				</div>
			</s:if>
	
			<!-- ✅ 表單欄位錯誤將自動顯示在欄位下方 -->
			<s:form action="login" method="post">
				<s:textfield name="username" label="帳號" required="true" />
				<s:password name="password" label="密碼" required="true" />	
				<s:submit value="登入" style="display:block; margin: 5px auto;" />
			</s:form>
			
			<!-- ✅ 註冊連結 -->
			<div style="text-align:center; font-size:14px;">
				還沒有帳號？<a href="register" style="text-decoration:none;">立即註冊</a>
			</div>
			
		</div>
	</div>

</body>
</html>
