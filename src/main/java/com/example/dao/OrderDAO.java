package com.example.dao;

import com.example.pojo.entity.Order;
import java.util.List;

/**
 * OrderDAO 介面
 * 提供對 orders 資料表的存取操作（新增、查詢等）
 * ✅ 一筆訂單 Order 對應多筆明細 OrderItem（OneToMany）
 * ✅ 一筆訂單屬於一位顧客 Customer（ManyToOne）
 */
public interface OrderDAO {
	
    /**
     * 儲存一筆訂單（含訂單明細）
     * @param order 欲儲存的訂單物件，應已包含關聯的顧客與明細
     */
    void save(Order order);
    
    /**
     * 根據顧客 ID 查詢該顧客所有訂單（通常用於「我的訂單」頁面）
     * @param customerId 顧客主鍵 ID
     * @return 該顧客的所有訂單清單（依下單時間倒序）
     */
    List<Order> findByCustomerId(Long customerId);
    
    /**
     * 根據訂單 ID 查詢單一訂單（含明細）
     * @param id 訂單主鍵 ID
     * @return 對應的訂單物件，若查無則回傳 null
     */
    Order findById(Long id);
    
}
