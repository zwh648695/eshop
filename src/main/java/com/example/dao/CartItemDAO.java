package com.example.dao;

import java.util.List;

import com.example.pojo.entity.CartItem;

/**
 * CartItem DAO 介面，定義購物車項目的基本操作方法。
 */
public interface CartItemDAO {
	
	// 新增購物車項目
	void add(CartItem item); 
	
	// 根據 ID 查詢購物車項目
    CartItem findById(Long id); 
    
    // 查詢全部項目
    List<CartItem> findAll(); 
    
    // 更新購物車項目
    void update(CartItem item);
    
    // 根據 ID 刪除
    void delete(Long id); 

}
