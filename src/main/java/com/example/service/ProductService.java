package com.example.service;

import java.util.List;

import com.example.pojo.entity.Product;

public interface ProductService {
	
	// 新增商品
    void addProduct(Product product);

    // 根據 ID 查詢商品
    Product getProductById(Long id);
    
    // 根據 名稱 查詢商品
    Product getProductByName(String name);

    // 查詢所有商品
    List<Product> getAllProducts();

    // 更新商品資料
    void updateProduct(Product product);

    // 根據 ID 刪除商品
    void deleteProduct(Long id);

    // 根據分頁查詢商品
    List<Product> getProductsByPage(int currentPage, int pageSize);
    
    // 查詢商品總筆數
    int getTotalProductCount(); // 🔁 加回

    // 根據商品總數計算總共有幾頁
    int getTotalPages(int pageSize);
    
}
