<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>註冊/登入</title>

	<%-- <link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/toastify-js/src/toastify.min.css"> --%>
	<link rel="stylesheet" type="text/css" href="public/css/toastify.min.css">

<style>

	body {
		text-align: center; /* 文字置中 */
		margin-top: 50px; /* 頂部留白 */
	}
	
	button {
		padding: 10px 20px; /* 按鈕內邊距 */
		font-size: 16px; /* 字體大小 */
		cursor: pointer; /* 鼠標變成手型 */
	}
	
</style>

</head>
<body>
	
	<h2>歡迎來到 eShop 系統</h2>
    
    <!-- 註冊按鈕 -->
    <button
    	style="margin-right: 30px;"
    	onclick="showToastAndRedirect('即將進入註冊頁面', 'register', 1500);">
    	📝 前往註冊頁面
    </button>               
    
    <!-- 登入按鈕，跳轉到 login.action -->
	<button 
		onclick="showToastAndRedirect('即將進入登入頁面', 'login', 1500);">
		🔐 前往登入頁面
	</button>
	
	<%-- 通常放在 body 結束標籤之前，確保 DOM 元素已經加載 --%>
	<%-- <script type="text/javascript" src="https://cdn.jsdelivr.net/npm/toastify-js"></script> --%>
	<script type="text/javascript" src="public/js/toastify.min.js"></script>
	
	<%-- 必須放在 Toastify 的 script 引入之後 --%>
	<script>
		function showToastAndRedirect(message, url, toastDuration = 2000) {
		    // 顯示 Toast 提示
		    Toastify({
		        text: message,
		        duration: toastDuration, // Toast 顯示時間
		        gravity: "top", // Toast 顯示位置 (上方)
                position: "center", // Toast 顯示位置 (右邊)
                // 注意：這裡不需要 onClick，因為跳轉是自動的
		    }).showToast();

		    // 在顯示 Toast 後，設置一個短暫的定時器來進行跳轉
		    // 可以選擇讓它立即跳轉 (延遲 0 毫秒)，或者稍微延遲一下 (例如 500 毫秒)，讓使用者能看到提示
            // Math.min(toastDuration, 500) 確保延遲不超過 500ms，但也不少於短暫的 toastDuration
		    setTimeout(() => {
		        window.location.href = url;
		    }, Math.min(toastDuration, 500)); // 這裡的 500ms 是自動跳轉的最大延遲
		}
	</script>
	
</body>
</html>