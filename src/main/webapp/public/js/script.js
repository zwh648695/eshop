// ✅ 取得欄位
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');
// const phoneInput = document.getElementById('phone');

// ✅ Email 驗證函式
function validateEmail(inputElement) {
	const value = inputElement.value.trim();
	const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

	// 🔁 每次驗證前，先清除前一次的紅框
	inputElement.classList.remove("invalid");

	// 若有填寫但格式錯誤
	if (value !== '' && !emailRegex.test(value)) {
		inputElement.classList.add("invalid"); // 加紅框
		inputElement.value = ''; // 清空
		inputElement.placeholder = "請輸入正確的 Email 格式"; // 顯示錯誤訊息
		return false;
	}

	return true;
}

// ✅ 密碼驗證函式（6~12 位，含大小寫與數字）
function validatePassword(inputElement) {
	const value = inputElement.value.trim();
	const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d).{6,12}$/;
	
	inputElement.classList.remove("invalid");

	if (value !== '' && !passwordRegex.test(value)) {
		inputElement.classList.add("invalid");
		inputElement.value = '';
		inputElement.placeholder = "6～12位，含大小寫與數字";
		return false;
	}
	
	return true;
}

// ✅ 手機驗證函式（台灣手機格式）
function validatePhone(inputElement) {
	const value = inputElement.value.trim();
	const phoneRegex = /^09\d{8}$/;
	
	inputElement.classList.remove("invalid");

	if (value !== '' && !phoneRegex.test(value)) {
		inputElement.classList.add("invalid");
		inputElement.value = '';
		inputElement.placeholder = "例：0912345678";
		return false;
	}
	
	return true;
}

// ✅ 綁定事件（失去焦點時驗證）
if (emailInput) {
	emailInput.addEventListener("blur", function () {
		validateEmail(this);
	});
}

if (passwordInput) {
	passwordInput.addEventListener("blur", function () {
		validatePassword(this);
	});
}

document.getElementById("phone").addEventListener("blur", function () {
	validatePhone(this);
});
