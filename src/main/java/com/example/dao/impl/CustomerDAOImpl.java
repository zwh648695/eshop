package com.example.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
	
	// 取得當前 Session（由 Spring Transaction 管理）
    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

	@Override
	public void add(Customer customer) {
		
		getSession().save(customer);
		
	}

	@Override
	public List<Customer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Customer findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Customer customer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
