package com.example.action.customer;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.pojo.entity.Customer;
import com.example.service.CustomerService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 處理註冊新會員的 Action
 */
public class CustomerRegisterAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String email;
	private String fullName;
	private String phone;
	private String address;

	@Autowired
	private CustomerService customerService;

	@Override
	public String execute() {
		// 初次進入註冊頁面（空白輸入）
		if (username == null && password == null && email == null) {
			return INPUT;
		}

		// 建立客戶實體並填入資料
		Customer customer = new Customer();
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setEmail(email);
		customer.setFullName(fullName);
		customer.setPhone(phone);
		customer.setAddress(address);

		// 嘗試新增
		boolean success = customerService.addCustomer(customer);
		
		if (success) {
			return SUCCESS;
		} 
		else {
			// 理論上不會進入，除非並發註冊（例如同帳號被別人搶先註冊）
		    addActionError("註冊失敗：可能是系統忙碌中，請稍後再試");
		    return INPUT;
		}
	}

	@Override
	public void validate() {
		// 判斷是否是第一次進入（沒有填資料）
	    if (username == null && password == null && email == null) {
	        return; // 初次進入不驗證，return後跳到excute()方法，validate()方法内不會處理頁面跳轉
	    }
		
		// 以下為欄位實際驗證邏輯
		if (username == null || username.trim().isEmpty()) {
			addFieldError("username", "帳號不能為空或輸入空格");
		}
		else if (customerService.isUsernameExists(username)) {
			addFieldError("username", "此帳號已存在，請重新輸入");
		}

		if (password == null || password.trim().isEmpty()) {
			addFieldError("password", "密碼不能為空或輸入空格");
		}

		if (email == null || email.trim().isEmpty()) {
		    addFieldError("email", "Email 欄位不能為空或輸入空格");
		} 
		else if (!email.trim().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
		    addFieldError("email", "請輸入有效的 Email 格式");
		}
		else if (customerService.isEmailExists(email)) {
			addFieldError("email", "此 Email 已被註冊");
		}

		if (phone != null && !phone.trim().isEmpty()) {
		    if (!phone.trim().matches("^09\\d{8}$")) {
		    	addFieldError("phone", "手機號碼格式錯誤：應為 09 開頭共 10 位數字（例如 0912345678）");
		    }
		}
	}

	// Getter / Setter（供 Struts2 自動綁定表單欄位）
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
