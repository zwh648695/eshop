package com.example.action.customer;

import java.util.Map;

import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 處理會員登出邏輯
 */
public class CustomerLogoutAction extends ActionSupport implements SessionAware {
	
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> session;

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
	
	@Override
    public String execute() {
        session.clear(); // 清除 Session
        return SUCCESS; // 登出成功後跳轉 logoutSuccess.jsp 或登入頁
    }

}
