package com.example.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.dao.CustomerDAO;
import com.example.pojo.entity.Customer;

/**
 * Customer DAO 實作類，使用 Hibernate 操作資料庫。
 * 採用 Spring 管理 SessionFactory，透過 getCurrentSession() 由 Spring 控制交易。
 */
@Repository
public class CustomerDAOImpl implements CustomerDAO {
	
	@Autowired
	private SessionFactory sessionFactory; // Spring 自動注入 SessionFactory
	
	/**
     * 取得當前 Hibernate Session（由 Spring Transaction 管理）
     */
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    // 新增客戶資料
	@Override
	public void add(Customer customer) {
		try {
			Session session = getSession();
			session.save(customer); // 儲存 Customer 實體至資料庫
		}
		catch(Exception e) {
			System.err.println("新增客戶時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
	}

	// 根據 ID 查詢客戶資料
	@Override
	public Customer findById(Long id) {
		Customer customer = null;
		
		try {
			Session session = getSession(); // 取得 Hibernate Session（由 Spring Transaction 管理）
			customer = session.get(Customer.class, id); // 使用 Session 查詢指定主鍵的 Customer 實體
		}
		catch(Exception e) {
			// 錯誤處理：印出例外訊息（可改為紀錄 log）
			System.err.println("查詢客戶資料時發生錯誤：" + e.getMessage());
	        e.printStackTrace(); // 開發時方便追蹤錯誤，正式環境建議使用 logger
		}

		return customer; // 無論是否查到資料，都會回傳 customer，找不到時為 null
	}
	
	// 查詢全部客戶資料
	@Override
	public List<Customer> findAll() {
		List<Customer> list = null;
		String hql = "FROM Customer";
		
		try {
			Session session = getSession();
			Query<Customer> query = session.createQuery(hql, Customer.class);
			list = query.list();
		}
		catch(Exception e) {
			System.err.println("查詢全部客戶時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
		
		return list;
	}
	
	// 查询客户的帐号和密码是否存在于资料库内，登入使用
	@Override
	public Customer findByUsernameAndPassword(String username, String password) {
		Customer customer = null;
		String hql = "FROM Customer c WHERE c.username = :username AND c.password = :password";
		
		try {
			Session session = getSession();
			Query<Customer> query = session.createQuery(hql, Customer.class);
			query.setParameter("username", username);
		    query.setParameter("password", password);
		    customer = query.getSingleResult();
		}
		catch(Exception e) {
			System.err.println("查詢客戶是否存在時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
		
	    return customer;
	}
	
	// 查询帐号是否存在于资料库，注册使用
	@Override
	public Customer findByUsername(String username) {
		Customer customer = null;
	    String hql = "FROM Customer c WHERE c.username = :username";
	    
	    try {
	        Session session = getSession();
	        Query<Customer> query = session.createQuery(hql, Customer.class);
	        query.setParameter("username", username);
	        customer = query.uniqueResult();
	    } catch (Exception e) {
	        System.err.println("查詢帳號是否存在時發生錯誤：" + e.getMessage());
	        e.printStackTrace();
	    }
	    
	    return customer;
	}

	// 更新客戶資料
	@Override
	public void update(Customer customer) {
		try {
			Session session = getSession();
			session.update(customer); // 將更新同步至資料庫
		}
		catch(Exception e) {
			System.err.println("更新客戶資料時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
		
	}

	// 根據 ID 刪除客戶資料
	@Override
	public void delete(Long id) {
		try {
			Session session = getSession();
			Customer customer = session.get(Customer.class, id);
			
			if (customer != null) {
                session.delete(customer); // 若查到對應資料，則執行刪除
            } 
			else {
                System.err.println("刪除失敗：找不到 ID 為 " + id + " 的客戶");
            }
		}
		catch(Exception e) {
			System.err.println("刪除客戶時發生錯誤：" + e.getMessage());
            e.printStackTrace();
		}
	}

	// 查詢客戶輸入 帳號 是否在資料庫唯一，註冊使用
	@Override
	public boolean isUsernameExists(String username) {
		String hql = "SELECT 1 FROM Customer c WHERE c.username = :username"; // 只想知道「資料存不存在」，不需要取得內容

	    try {
	        Session session = getSession();
	        List<Integer> result = session.createQuery(hql, Integer.class)
	                                      .setParameter("username", username)
	                                      .setMaxResults(1) // 對應 SQL 的 LIMIT 1，表示只查到第一筆資料就停止 → 更快！
	                                      .list(); // 執行查詢，回傳 List 結果，如果查不到，會回傳空的 List
	        return !result.isEmpty(); 
	        // 若有值（即使只是一個 1），就代表資料庫中存在該帳號 → 回傳 true，若是空的，就代表該帳號不存在 → 回傳 false
	    } 
	    catch (Exception e) {
	        System.err.println("查詢帳號是否存在時發生錯誤：" + e.getMessage());
	        return false;
	    }
	}

	// 查詢客戶輸入 Email 是否在資料庫唯一，註冊使用
	@Override
	public boolean isEmailExists(String email) {
		String hql = "SELECT 1 FROM Customer c WHERE c.email = :email";
		
	    try {
	        Session session = getSession();
	        List<Integer> result = session.createQuery(hql, Integer.class)
						                  .setParameter("email", email)
						                  .setMaxResults(1)
						                  .list();
	        return !result.isEmpty(); 
	    } 
	    catch (Exception e) {
	        System.err.println("查詢 Email 是否存在時發生錯誤：" + e.getMessage());
	        return false;
	    }
	}

}
