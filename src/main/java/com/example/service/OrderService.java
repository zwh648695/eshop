package com.example.service;

import com.example.pojo.entity.Order;
import java.util.List;

/**
 * Order 業務邏輯介面
 */
public interface OrderService {

    // 儲存訂單（含明細）
    void saveOrder(Order order);

    // 查詢單筆訂單（含明細）
    Order getOrderById(Long id);

    // 查詢某位顧客的所有訂單
    List<Order> getOrdersByCustomerId(Long customerId);
    
}