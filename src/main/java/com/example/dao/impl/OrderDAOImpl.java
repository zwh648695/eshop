package com.example.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.dao.OrderDAO;
import com.example.pojo.entity.Order;

/**
 * OrderDAO 實作類
 * 提供對 orders 資料表的存取操作
 * ✅ 一筆訂單 Order 對應多筆明細 OrderItem（OneToMany）
 * ✅ 一筆訂單屬於一位顧客 Customer（ManyToOne）
 */
@Repository
public class OrderDAOImpl implements OrderDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * 取得目前的 Hibernate Session
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * 儲存一筆訂單（含訂單明細）
     * 由於 Order 實體已設定 cascade，儲存 Order 同時會儲存 OrderItem
     */
    @Override
    public void save(Order order) {
    	// ❗ 不建議包 try/catch，讓 Spring Transaction 處理 rollback
        getSession().save(order);
    }

    /**
     * 根據顧客 ID 查詢所有訂單（依下單時間倒序）
     * 若查詢失敗，會記錄錯誤並回傳空清單
     */
    @Override
    public List<Order> findByCustomerId(Long customerId) {
        try {
            String hql = "FROM Order o WHERE o.customer.id = :customerId ORDER BY o.createdAt DESC";
            Query<Order> query = getSession().createQuery(hql, Order.class);
            query.setParameter("customerId", customerId);
            return query.getResultList();
        } 
        catch (Exception e) {
            System.err.println("❌ 查詢顧客訂單失敗: " + e.getMessage());
            return new ArrayList<>();
        }
    }
    
    /**
     * 根據訂單 ID 查詢單一訂單
     * 若查無則回傳 null，查詢過程中發生例外會印出錯誤
     */
    @Override
    public Order findById(Long id) {
        try {
            return getSession().get(Order.class, id);
        } 
        catch (Exception e) {
            System.err.println("❌ 查詢訂單失敗: " + e.getMessage());
            return null;
        }
    }
    
}
