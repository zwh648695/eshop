package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.CustomerDAO;
import com.example.pojo.entity.Customer;
import com.example.service.CustomerService;

@Service
@Transactional // 類別所有 public 方法都會啟用交易
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
    private CustomerDAO customerDAO;

	// 註冊新客戶（帳號不可重複）
	@Override
	public boolean addCustomer(Customer customer) {
		if(customerDAO.isUsernameExists(customer.getUsername())) {
			return false; // 如果 帳號 存在就回傳false，代表不能使用重複帳號註冊
		}
		
		if(customerDAO.isEmailExists(customer.getEmail())) {
			return false; // 同上，但是檢查 email 是否已存在
		}
		
        customerDAO.add(customer);
        return true;
	}
	
	// 根據 ID 查詢客戶
	@Override
	public Customer getCustomerById(Long id) {
		return customerDAO.findById(id);
	}

	// 根據 帳號(username) 查詢客戶
	@Override
	public Customer getCustomerByUsername(String username) {
		return customerDAO.findByUsername(username);
	}

	// 登入驗證：帳號與密碼必須匹配
	@Override
	public Customer loginByUsernameAndPassword(String username, String password) {
		
		Customer customer = customerDAO.findByUsernameAndPassword(username, password);
		return customer;
	}

	// 修改密碼，需驗證舊密碼是否正確
	@Override
	public boolean changePassword(Long customerId, String oldPassword, String newPassword) {
		Customer customer = customerDAO.findById(customerId);
		
        if (customer == null) {
        	return false;
        }

        // 舊密碼比對（注意：實務上密碼應該加密）
        if (!customer.getPassword().equals(oldPassword)) {
            return false; // 舊密碼錯誤
        }

        customer.setPassword(newPassword);
        customerDAO.update(customer);
        
        return true;
	}

	// 更新客戶基本資料（不含密碼）
	@Override
	public boolean updateCustomer(Customer updatedCustomer) {
		Customer customer = customerDAO.findById(updatedCustomer.getId());
		
        if (customer == null) {
        	return false;
        }

        // 更新欄位（根據你的 Customer 欄位調整）
        customer.setEmail(updatedCustomer.getEmail());
        customer.setFullName(updatedCustomer.getFullName());
        customer.setPhone(updatedCustomer.getPhone());
        customer.setAddress(updatedCustomer.getAddress());

        customerDAO.update(customer);
        return true;
	}

	// 根據 ID 刪除客戶
	@Override
	public boolean deleteCustomer(Long id) {
		Customer customer = customerDAO.findById(id);
		
        if (customer == null) {
        	return false;
        }
        
        customerDAO.delete(id);
        return true;
	}

	// 查詢全部客戶資料
	@Override
	public List<Customer> getAllCustomers() {
		return customerDAO.findAll();
	}

	// 查詢 註冊帳號 是否唯一
	@Override
	public boolean isUsernameExists(String username) {
		return customerDAO.isUsernameExists(username);
	}

	// 查詢 註冊email 是否唯一
	@Override
	public boolean isEmailExists(String email) {
		return customerDAO.isEmailExists(email);
	}

}
