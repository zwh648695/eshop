<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>請先登入</title>
	
	<meta http-equiv="refresh" content="2; url=<c:url value='/login' />" /> <!-- ✅ HTML 備援跳轉（若 JS 失效，也能跳） -->
	
</head>
<body style="text-align:center; margin-top:100px">

	<h2 style="color: red;">請先登入才能使用此功能！</h2>
	<p>系統將於 2 秒後自動導向登入頁...</p>
	<p>
		<a href="<c:url value='/login' />">👉 若未跳轉，請點此前往登入頁</a>
	</p>


	<script>
		window.onload = function () {
			setTimeout(function () {
				window.location.href = "<c:url value='/login' />";
			}, 2000); // ✅ 延遲 2 秒後跳轉（時間與 meta 一致，避免重複或衝突）
		};
	</script>
	    
</body>
</html>
