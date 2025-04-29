package com.example.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.dao.CategoryDAO;
import com.example.pojo.entity.Category;

/**
 * Category DAO 實作類，操作 category 資料表。
 */
@Repository // 標示為 Spring 的 DAO 組件，會自動掃描注入
public class CategoryDAOImpl implements CategoryDAO {
	
	@Autowired
	private SessionFactory sessionFactory; // Spring 自動注入 SessionFactory
	
	/**
     * 取得當前的 Hibernate Session，由 Spring 管理交易控制
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    // 新增分類資料
	@Override
	public void add(Category category) {
		try {
			Session session = getSession();
			session.save(category); // 儲存分類到資料庫
		}
		catch(Exception e) {
			System.err.println("新增分類時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
	}

	// 根據 ID 查詢分類資料
	@Override
	public Category findById(Long id) {
		Category category = null;
		
		try {
			Session session = getSession();
			category = session.get(Category.class, id); // 根據主鍵查詢分類
		}
		catch(Exception e) {
			System.err.println("查詢分類時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
		
		return category;
	}

	// 查詢全部分類資料
	@Override
	public List<Category> findAll() {
		List<Category> list = null;
		String hql = "FROM Category"; // HQL 查詢語法
		
		try {
			Session session = getSession();
			Query<Category> query = session.createQuery(hql, Category.class);
			list = query.getResultList();
		}
		catch(Exception e) {
			System.err.println("查詢全部分類時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
		
		return list;
	}

	// 更新分類資料
	@Override
	public void update(Category category) {
		try {
			Session session = getSession();
			session.update(category); // 更新資料庫對應的分類記錄
		}
		catch(Exception e) {
			System.err.println("更新分類資料時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
	}

	// 根據 ID 刪除分類資料
	@Override
	public void delete(Long id) {
		try {
			Session session = getSession();
			Category category = session.get(Category.class, id); // 先查詢要删除的 分類ID 是否存在
			
			if (category != null) {
                session.delete(category); // 存在則刪除
            } 
			else {
				System.err.println("找不到 ID 為 " + id + " 的分類，無法刪除");
            }
		}
		catch(Exception e) {
			System.err.println("刪除分類時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
	}

}
