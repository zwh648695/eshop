package com.example.dao;

import java.util.List;

import com.example.pojo.entity.Cart;

/**
 * Cart DAO 介面，定義對 cart 表的基本 CRUD 操作。
 */
public interface CartDAO {
	
	// 新增購物車
	void add(Cart cart); 
	
	// 根據 ID 查詢購物車
    Cart findById(Long id); 
    
    // 查詢所有購物車
    List<Cart> findAll(); 
    
    // 查詢某位顧客的所有購物車（歷史記錄）
    List<Cart> findAllByCustomerId(Long customerId);
    
    // 更新購物車
    void update(Cart cart); 
    
    // 根據 ID 刪除購物車
    void delete(Long id); 

}
