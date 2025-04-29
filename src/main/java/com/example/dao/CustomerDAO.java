package com.example.dao;

import java.util.List;

import com.example.pojo.entity.Customer;

public interface CustomerDAO {
	
	// 新增客户
	void add(Customer customer);
	
	// 根据 ID 查詢客户
	Customer findById(Long id);
	
	// 查詢全部客户
	List<Customer> findAll();
	
	// 查詢帳號是否存在
	Customer findByUsername(String username);
	
	// 查詢客户的帳號密碼是否存在
	Customer findByUsernameAndPassword(String username, String password);
	
	// 更新客户資料
	void update(Customer customer);
	
	// 根据 ID 删除客户
	void delete(Long id);
	
	// 查詢帳號是否唯一
	boolean isUsernameExists(String username);
	
	// 查詢Email是否唯一
	boolean isEmailExists(String email);

}
