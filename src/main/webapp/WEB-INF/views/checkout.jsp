<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>確認購物車</title>

	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/toastify-js"></script>
	
</head>
<body class="container mt-4">

	<h2 class="mb-4">🛒 確認購物車內容</h2>

	<c:if test="${not empty sessionScope.cart}">
		<c:set var="grandTotal" value="0" />

		<table class="table table-bordered align-middle">
			<thead>
				<tr>
					<th>商品名稱</th>
					<th>單價</th>
					<th>數量</th>
					<th>小計</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${sessionScope.cart}">
					<c:set var="product" value="${item.value.product}" />
					<c:set var="quantity" value="${item.value.quantity}" />
					<c:set var="unitPrice" value="${product.price}" />
					<c:set var="subtotal" value="${unitPrice * quantity}" />
					<c:set var="grandTotal" value="${grandTotal + subtotal}" />

					<tr>
						<td>${product.name}</td>
						<td>NT$ ${unitPrice}</td>
						<td>
							<form action="updateCart" method="post" class="d-inline">
								<input type="hidden" name="productId" value="${product.id}" /> <input
									type="hidden" name="actionType" value="decrease" /> <input
									type="submit" value="-"
									class="btn btn-outline-secondary btn-sm" />
							</form> ${quantity}

							<form action="updateCart" method="post" class="d-inline">
								<input type="hidden" name="productId" value="${product.id}" /> <input
									type="hidden" name="actionType" value="increase" /> <input
									type="submit" value="+"
									class="btn btn-outline-secondary btn-sm" />
							</form>
						</td>
						<td>NT$ ${subtotal}</td>
						<td>
							<form action="updateCart" method="post" class="d-inline">
								<input type="hidden" name="productId" value="${product.id}" /> <input
									type="hidden" name="actionType" value="remove" /> <input
									type="submit" value="🗑" class="btn btn-outline-danger btn-sm" />
							</form>
						</td>
					</tr>
				</c:forEach>

				<tr>
					<td colspan="3" class="text-end fw-bold">總金額：</td>
					<td class="fw-bold text-danger">NT$ ${grandTotal}</td>
					<td></td>
				</tr>
			</tbody>
		</table>

		<div class="d-flex justify-content-between mt-4">
			<a href="eshop?currentPage=1" class="btn btn-secondary">← 繼續購物</a>
			<s:form action="confirmOrder">
				<s:submit value="✔️ 確認訂單" cssClass="btn btn-success" />
			</s:form>
		</div>
	</c:if>

	<!-- ✅ 當購物車為空：顯示提示 + 返回按鈕 + 自動跳轉 -->
	<c:if test="${empty sessionScope.cart}">
		<div class="alert alert-warning text-center">
			📭 目前購物車是空的，請回商品頁選購。
		</div>

		<div class="text-center mt-3">
			<a href="eshop.action?currentPage=1" class="btn btn-outline-primary">🔄 回到商品列表</a>
		</div>
	</c:if>

	<!-- ✅ Toastify 提示訊息 -->
	<c:if test="${not empty sessionScope.toastifyMessage}">
		<script>
			Toastify(
					{
						text : "${sessionScope.toastifyMessage}",
						duration : 1000,
						gravity : "top",
						position : "center",
						backgroundColor : "linear-gradient(to right, #00b09b, #96c93d)",
						close : true
					}).showToast();
		</script>
		<c:remove var="toastifyMessage" scope="session" />
	</c:if>

</body>
</html>
