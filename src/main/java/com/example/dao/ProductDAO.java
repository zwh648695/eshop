package com.example.dao;

import java.util.List;

import com.example.pojo.entity.Product;

public interface ProductDAO {
	
	// 新增商品
    void add(Product product);

    // 根據 ID 查詢商品
    Product findById(Long id);

    // 查詢全部商品
    List<Product> findAll();

    // 更新商品資料
    void update(Product product);

    // 根據 ID 刪除商品
    void delete(Long id);
    
    // 根據頁數查詢商品
    List<Product> findByPage(int currentPage, int pageSize);
    
    // 查詢全部商品的筆數
    int countTotalProducts();
    
    // 🔍 根據商品名稱查詢：新增
    Product findByName(String name);

}
