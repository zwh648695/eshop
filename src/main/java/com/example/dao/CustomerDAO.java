package com.example.dao;

import java.util.List;

import com.example.pojo.entity.Customer;

public interface CustomerDAO {
	
	// 新增客户
	void add(Customer customer);
	
	// 查询全部客户
	List<Customer> findAll();
	
	// 根据ID查询客户
	Customer findById(Long id);
	
	// 更新客户资料
	void update(Customer customer);
	
	// 根据ID删除客户
	void delete(Long id);

}
