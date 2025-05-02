<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		
		.card-img-top {
		    max-height: 250px;
		    object-fit: contain;
		    margin: auto;
		    display: block;
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
		<s:if test="#session.currentCustomer != null">
	        <p>您好，<s:property value="#session.currentCustomer.username" />，歡迎回來！</p>
	    </s:if>
	    <s:else>
	        <p>您尚未登入</p>
	    </s:else>

		<hr>

		<!-- 🔥 商品清單 -->
		<h3>商品列表</h3>
		<p class="text-muted">第 <s:property value="currentPage" /> 頁 / 共 <s:property value="totalPages" /> 頁</p>
		
		<s:if test="products != null && !products.isEmpty()">
			<div class="row">
			    <s:iterator value="products" var="product">
			        <div class="col-md-4 mb-4">
			            <div class="card h-100">
			            
			                <!-- 圖片依商品名稱判斷 -->
		                    <s:if test="#product.name == '自動鉛筆'">
	                            <img src="<s:url value='/public/images/autoPen.jpg' />" class="card-img-top" alt="自動鉛筆">
	                        </s:if>
	                        <s:elseif test="#product.name == '原子筆'">
	                            <img src="<s:url value='/public/images/ballpointPen.jpg' />" class="card-img-top" alt="原子筆">
	                        </s:elseif>
	                        <s:elseif test="#product.name == '蘋果'">
	                            <img src="<s:url value='/public/images/apple.jpeg' />" class="card-img-top" alt="蘋果">
	                        </s:elseif>
	                        <s:else>
	                            <img src="<s:url value='/public/images/default.jpg' />" class="card-img-top" alt="預設圖片">
	                        </s:else>
		                    
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
		</s:if>
		
		<!-- 🚫 無商品提示 -->
		<s:if test="products == null || products.isEmpty()">
			<div class="alert alert-warning text-center mt-3">目前沒有商品可顯示。</div>
		</s:if>
		
		<!-- 📄 分頁導航 -->
		<nav aria-label="Page navigation">
		    <ul class="pagination justify-content-center">
		
		        <!-- ◀️ 上一頁 -->
		        <s:if test="currentPage <= 1">
		            <li class="page-item disabled">
		                <a class="page-link">上一頁</a>
		            </li>
		        </s:if>
		        <s:else>
		            <li class="page-item">
		                <s:url var="prevUrl" action="eshop">
		                    <s:param name="currentPage" value="%{currentPage - 1}" />
		                </s:url>
		                <a class="page-link" href="<s:property value='#prevUrl' />">上一頁</a>
		            </li>
		        </s:else>
		
		        <!-- 🔢 頁碼 -->
		        <s:iterator begin="1" end="%{totalPages}" var="i">
		            <s:if test="currentPage == #i">
		                <li class="page-item active">
		                    <a class="page-link" href="#">${i}</a>
		                </li>
		            </s:if>
		            <s:else>
		                <s:url var="pageUrl" action="eshop">
		                    <s:param name="currentPage" value="%{#i}" />
		                </s:url>
		                <li class="page-item">
		                    <a class="page-link" href="<s:property value='#pageUrl' />">${i}</a>
		                </li>
		            </s:else>
		        </s:iterator>
		
		        <!-- ▶️ 下一頁 -->
		        <s:if test="currentPage >= totalPages">
		            <li class="page-item disabled">
		                <a class="page-link">下一頁</a>
		            </li>
		        </s:if>
		        <s:else>
		            <li class="page-item">
		                <s:url var="nextUrl" action="eshop">
		                    <s:param name="currentPage" value="%{currentPage + 1}" />
		                </s:url>
		                <a class="page-link" href="<s:property value='#nextUrl' />">下一頁</a>
		            </li>
		        </s:else>
		
		    </ul>
		</nav>
		

	</div>

</body>
</html>
