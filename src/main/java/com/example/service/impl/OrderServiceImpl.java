package com.example.service.impl;

import com.example.dao.OrderDAO;
import com.example.pojo.entity.Order;
import com.example.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * OrderService 實作類
 * 封裝訂單的業務邏輯（目前為簡單委派 DAO）
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO orderDAO;

    @Override
    public void saveOrder(Order order) {
        if (order == null || order.getCustomer() == null || order.getOrderItems() == null || order.getOrderItems().isEmpty()) {
            throw new IllegalArgumentException("訂單資訊不完整！");
        }
        orderDAO.save(order);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderDAO.findById(id);
    }

    @Override
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderDAO.findByCustomerId(customerId);
    }
    
}
