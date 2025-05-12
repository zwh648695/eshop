<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>訂單建立成功</title>

    <!-- ✅ Toastify 樣式 -->
    <link rel="stylesheet" type="text/css" href="<c:url value='/public/css/toastify.min.css' />" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" />
</head>
<body class="container text-center mt-5">

    <h2 class="text-success mb-4">✅ 訂單已成立成功！</h2>

    <!-- ✅ 顯示訂單詳情 -->
    <div class="mb-4" style="font-size: 1.2em;">
        <c:if test="${not empty sessionScope.lastOrderId}">
            <p>📦 訂單編號：
                <span class="text-primary fw-bold">${sessionScope.lastOrderId}</span>
            </p>
        </c:if>

        <c:if test="${not empty sessionScope.lastOrderTotal}">
            <p>💰 訂單總金額：
                <span class="text-danger fw-bold">
                    NT$ <fmt:formatNumber value="${sessionScope.lastOrderTotal}" pattern="#,##0.00"/>
                </span>
            </p>
        </c:if>

        <c:if test="${not empty sessionScope.lastOrderTime}">
            <p>🕒 下訂時間：
                <span class="text-muted">
                    <fmt:formatDate value="${sessionScope.lastOrderTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                </span>
            </p>
        </c:if>
    </div>

    <!-- ✅ 返回首頁按鈕 -->
    <a href="<c:url value='/eshop' />" class="btn btn-primary btn-lg">🔙 回到首頁</a>

    <!-- ✅ Toastify JS -->
    <script src="<c:url value='/public/js/toastify.min.js' />"></script>
    <script>
        const toastMessage = "<c:out value='${sessionScope.toastifyMessage}' />";
        if (toastMessage) {
            Toastify({
                text: toastMessage,
                duration: 1500,
                gravity: "top",
                position: "center",
                backgroundColor: "linear-gradient(to right, #00b09b, #96c93d)",
                close: true
            }).showToast();
        }
    </script>

    <!-- ✅ 清除 session 資料 -->
    <c:remove var="toastifyMessage" scope="session" />
    <c:remove var="lastOrderId" scope="session" />
    <c:remove var="lastOrderTotal" scope="session" />
    <c:remove var="lastOrderTime" scope="session" />

</body>
</html>
