package com.example.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.dao.CartDAO;
import com.example.pojo.entity.Cart;

/**
 * Cart DAO 實作類，透過 Hibernate 操作 cart 資料表。
 * ✅ 每個購物車 Cart 對應一位顧客 Customer（多對一）
 * ✅ 每個購物車可以擁有多個 CartItem 項目（一對多）
 */
@Repository
public class CartDAOImpl implements CartDAO {
	
	@Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    // 新增購物車
	@Override
	public void add(Cart cart) {
		try {
            getSession().save(cart);
        } 
		catch (Exception e) {
            System.err.println("新增購物車時發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }	
	}

	// 根據 ID 查詢購物車
	@Override
	public Cart findById(Long id) {
		Cart cart = null;
		
        try {
            cart = getSession().get(Cart.class, id);
        } 
        catch (Exception e) {
            System.err.println("查詢購物車時發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }
        return cart;
	}

	// // 查詢全部購物車
	@Override
	public List<Cart> findAll() {
		List<Cart> list = null;
		
        try {
            Query<Cart> query = getSession().createQuery("FROM Cart", Cart.class);
            list = query.list();
        } 
        catch (Exception e) {
            System.err.println("查詢全部購物車時發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }
        
        return list;
	}
	
	// 查詢某位顧客所有購物車（歷史記錄）
    @Override
    public List<Cart> findAllByCustomerId(Long customerId) {
    	List<Cart> list = null;
    	
        try {
            String hql = "FROM Cart c WHERE c.customer.id = :customerId ORDER BY c.id DESC";
            Query<Cart> query = getSession().createQuery(hql, Cart.class);
            query.setParameter("customerId", customerId);
            list = query.list();
        } 
        catch (Exception e) {
            System.err.println("查詢使用者所有購物車時發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }
        
        return list;
    }

	// 更新購物車
	@Override
	public void update(Cart cart) {
		try {
            getSession().update(cart);
        } 
		catch (Exception e) {
            System.err.println("更新購物車時發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }
	}

	// 根據 ID 刪除購物車
	@Override
	public void delete(Long id) {
		try {
			Session session = getSession();
            Cart cart = session.get(Cart.class, id);
            
            if (cart != null) {
            	session.delete(cart);
            } 
            else {
                System.err.println("刪除失敗：找不到 ID 為 " + id + " 的購物車");
            }
        } 
		catch (Exception e) {
            System.err.println("刪除購物車時發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }
	}

}
