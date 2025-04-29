package com.example.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.dao.CartItemDAO;
import com.example.pojo.entity.CartItem;

/**
 * CartItem DAO 實作類，操作 cart_item 表。
 * ✅ 每個 CartItem 連到一個 Cart（多對一）
 * ✅ 每個 CartItem 也連到一個 Product（多對一）
 * ✅ Product 又關聯到 Category，形成間接商品分類關係
 */
@Repository
public class CartItemDAOImpl implements CartItemDAO {
	
	@Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    // 新增購物車項目
	@Override
	public void add(CartItem item) {
		try {
			Session session = getSession();
			session.save(item);
        } 
		catch (Exception e) {
            System.err.println("新增購物車項目時發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }
	}

	// // 根據 ID 查詢購物車項目
	@Override
	public CartItem findById(Long id) {
		CartItem item = null;
		
        try {
            item = getSession().get(CartItem.class, id);
        } 
        catch (Exception e) {
            System.err.println("查詢購物車項目時發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }
        
        return item;
	}

	// 查詢全部購物車項目
	@Override
	public List<CartItem> findAll() {
		List<CartItem> list = null;
		
        try {
            Query<CartItem> query = getSession().createQuery("FROM CartItem", CartItem.class);
            list = query.list();
        } 
        catch (Exception e) {
            System.err.println("查詢所有項目時發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }
        return list;
	}

	// 更新購物車項目
	@Override
	public void update(CartItem item) {
		try {
            getSession().update(item);
        } 
		catch (Exception e) {
            System.err.println("更新購物車項目時發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }
	}

	// 根據 ID 刪除購物車項目
	@Override
	public void delete(Long id) {
		try {
			Session session = getSession();
            CartItem item = session.get(CartItem.class, id);
            
            if (item != null) {
                session.delete(item);
            } 
            else {
                System.err.println("刪除失敗：找不到 ID 為 " + id + " 的購物車項目");
            }
        } 
		catch (Exception e) {
            System.err.println("刪除購物車項目時發生錯誤: " + e.getMessage());
            e.printStackTrace();
        }
	}

}
