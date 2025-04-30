package com.example.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.dao.ProductDAO;
import com.example.pojo.entity.Category;
import com.example.pojo.entity.Product;

/**
 * Product DAO 實作類，操作 product 資料表。
 */
@Repository // Spring 自動掃描這個 DAO 類
public class ProductDAOImpl implements ProductDAO {
	
	@Autowired
    private SessionFactory sessionFactory; // 由 Spring 注入的 Hibernate SessionFactory

    /**
     * 取得當前 Hibernate Session（由 Spring 交易管理）
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    // 新增商品資料
	@Override
	public void add(Product product) {
		try {
			Session session = getSession();
			session.save(product); // 儲存商品資料到資料庫
		}
		catch(Exception e) {
			System.err.println("新增商品時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
	}

	// 根據 ID 查詢商品資料
	@Override
	public Product findById(Long id) {
		Product product = null;
		
		try {
			Session session = getSession();
			product = session.get(Product.class, id); // 根據主鍵查詢商品
		}
		catch(Exception e) {
			System.err.println("查詢商品時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
		
		return product;
	}

	// 查詢全部商品資料
	@Override
	public List<Product> findAll() {
		List<Product> list = null;
		String hql = "FROM Product";
		
		try {
			Session session = getSession();
			Query<Product> query = session.createQuery(hql, Product.class); // 查詢全部商品
			list = query.list(); 
		}
		catch(Exception e) {
			System.err.println("查詢全部商品時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
		
		return list;
	}

	// 更新商品資料
	@Override
	public void update(Product product) {
		try {
			Session session = getSession();
			session.update(product); // 將更新後的商品同步到資料庫
		}
		catch(Exception e) {
			System.err.println("更新商品資料時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
	}

	// 根據 ID 刪除商品資料
	@Override
	public void delete(Long id) {
		try {
			Session session = getSession();
			Product product = session.get(Product.class, id); // 查詢商品是否存在
			
			if (product != null) {
                session.delete(product); // 若存在則刪除
            } 
			else {
				System.err.println("找不到 ID 為 " + id + " 的商品，無法刪除");
            }
		}
		catch(Exception e) {
			System.err.println("刪除商品時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
	}

	// 根據 page 查詢商品
	@Override
	public List<Product> findByPage(int currentPage, int pageSize) {
		List<Product> list = null;
		String hql = "FROM Product";
		
		try {
			Session session = getSession();
			Query<Product> query = session.createQuery(hql, Product.class);
			query.setFirstResult((currentPage - 1) * pageSize);
			list = query.list();
		}
		catch(Exception e) {
			System.err.println("根據頁面查詢商品時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
		
		return list;
	}

	// 取得全部商品的筆數
	@Override
	public int countTotalProducts() {
		String hql = "SELECT COUNT(*) FROM Product";
		int count = 0;
		
		try {
			Session session = getSession();
			Query<Long> query = session.createQuery(hql, Long.class);
			count = query.uniqueResult().intValue();
		}
		catch(Exception e) {
			System.err.println("查詢商品總筆數時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
		
		return count;
	}
	
	@Override
	public Product findByName(String name) {
	    String hql = "FROM Product WHERE name = :name";
	    Product product = null;

	    try {
	        Session session = getSession();
	        Query<Product> query = session.createQuery(hql, Product.class);
	        query.setParameter("name", name);
	        product = query.uniqueResult(); // 找到就回傳，否則為 null
	    } 
	    catch (Exception e) {
	        System.err.println("根據商品名稱查詢時發生錯誤：" + e.getMessage());
	        e.printStackTrace();
	    }

	    return product;
	}

}
