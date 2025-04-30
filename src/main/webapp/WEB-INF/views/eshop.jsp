<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>eShop 首頁</title>

	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">

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

	<div class="container-fluid">

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

		<!-- 🔥 商品清單 -->
		<h3>商品列表</h3>
		<div class="row">
		    <s:iterator value="products" var="product">
		        <div class="col-md-4 mb-4">
		            <div class="card h-100">
		                <img src="<s:property value='#product.imageUrl'/>" class="card-img-top" alt="<s:property value='#product.name'/>">
		                <div class="card-body">
		                    <h5 class="card-title"><s:property value="#product.name"/></h5>
		                    <p class="card-text"><s:property value="#product.description"/></p>
		                    <p class="text-danger fw-bold">NT$ <s:property value="#product.price"/></p>
		                    <p class="text-muted">庫存：<s:property value="#product.stockQuantity"/></p>
		                </div>
		            </div>
		        </div>
		    </s:iterator>
		</div>
		
		<!-- 📄 分頁導航 -->
		<nav aria-label="Page navigation">
		    <ul class="pagination justify-content-center">
		        <li class="page-item <s:if test='currentPage <= 1'>disabled</s:if>'">
		            <s:url var="prevUrl" action="eshop">
		                <s:param name="currentPage" value="%{currentPage - 1}"/>
		            </s:url>
		            <a class="page-link" href="<s:property value='#prevUrl'/>">上一頁</a>
		        </li>
		
		        <s:iterator begin="1" end="%{totalPages}" var="i">
		            <li class="page-item <s:if test='currentPage == #i'>active</s:if>'">
		                <s:url var="pageUrl" action="eshop">
		                    <s:param name="currentPage" value="%{#i}"/>
		                </s:url>
		                <a class="page-link" href="<s:property value='#pageUrl'/>"><s:property value="#i"/></a>
		            </li>
		        </s:iterator>
		
		        <li class="page-item <s:if test='currentPage >= totalPages'>disabled</s:if>'">
		            <s:url var="nextUrl" action="eshop">
		                <s:param name="currentPage" value="%{currentPage + 1}"/>
		            </s:url>
		            <a class="page-link" href="<s:property value='#nextUrl'/>">下一頁</a>
		        </li>
		    </ul>
		</nav>

	</div>

</body>
</html>
