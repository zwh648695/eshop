package com.example.action.customer;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.pojo.entity.Customer;
import com.example.service.CustomerService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 客戶登入 Action
 * 功能：使用帳號與密碼登入系統，並將登入資訊存入 Session。
 */
public class CustomerLoginAction extends ActionSupport implements SessionAware {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
    private String password;

    @Autowired
    private CustomerService customerService;
    
    // Struts2 自動注入的 Session（不是 HttpSession，而是封裝後的 Map）
 	private Map<String, Object> session;
 	
 	// Struts2 會自動呼叫這個方法，把 session 傳進來
 	@Override
 	public void setSession(Map<String, Object> session) {
 		this.session = session;
 	}

    @Override
    public String execute() {
    	// ✅ 首次進入登入頁面，不做查詢、不顯示錯誤
    	if (username == null && password == null) {
            return INPUT;
        }
    	
    	// 從資料庫查詢 帳密是否正確
        Customer loginCustomer = customerService.loginByUsernameAndPassword(username, password);

        if (loginCustomer != null) {
        	// 登入成功：儲存登入資料到 Session
        	session.put("currentCustomer", loginCustomer);
			return SUCCESS;
        } 
        else {
            // 登入失敗
            addActionError("登入失敗：帳號或密碼錯誤！");
            this.username = ""; // ✅ 清空欄位
            this.password = "";
            return INPUT;
        }
    }

    // 後端欄位驗證（帳號、密碼不得為空）
    public void validate() {
    	// 首次開啟，不驗證
        if (username == null && password == null) {
            return; // 首次進入頁面，不加入任何錯誤訊息
        }
        
        // 欄位驗證
        if (username == null || username.trim().isEmpty()) {
            addFieldError("username", "帳號不能為空或輸入空格");
        }
        if (password == null || password.trim().isEmpty()) {
            addFieldError("password", "密碼不能為空或輸入空格");
        }
    }
    
    // Getter / Setter（Struts2 會自動注入）
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
	
}
