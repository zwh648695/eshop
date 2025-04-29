package com.example.test.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.CustomerDAO;
import com.example.pojo.entity.Customer;

/**
 * 使用 Spring 整合 Hibernate 的 CustomerDAO 測試類。
 * 所有操作都在 Transaction 內完成，並可自動回滾。
 */
@SpringJUnitConfig(locations = "classpath:spring/applicationContext.xml")
@Transactional
@Rollback(false) // 改成 true 可讓測試後資料回滾
public class CustomerDAOTest {

	@Autowired
    private CustomerDAO customerDAO;
	
	// === 測試：新增單筆客戶 ===
//    @Test
    public void testAdd() {
        Customer customer = new Customer();
        customer.setUsername("bbb");
        customer.setPassword("222");
        customer.setEmail("bbb@example.com");
        customer.setFullName("BBB");
        customer.setPhone("0922222222");
        customer.setAddress("高雄市");
        customer.setCreatedAt(LocalDateTime.now());

        customerDAO.add(customer);
        System.out.println("✅ 新增客户成功，ID = " + customer.getId());
    }
    
    // === 測試：查詢單筆客戶 ===
//    @Test
    public void testFindById() {
    	Customer customer = customerDAO.findById(1L);
    	System.out.println("✅ 查詢結果：\n" + "Username: " + customer.getUsername() + "\tAddress: " + customer.getAddress());
    }
    
    // === 測試：查詢全部客戶 ===
//    @Test
    public void testFindAll() {
    	List<Customer> all = customerDAO.findAll();
    	System.out.println("✅ 查詢全部，筆數：" + all.size());
    	
    	for(Customer customer : all) {
    		System.out.println("Username: " + customer.getUsername() + "\tPassword: " + customer.getPassword());
    	}
    }
    
    // === 測試：查詢客戶是否存在于资料库(登入使用) ===
//    @Test
    public void testFindByUsernameAndPassword() {
    	Customer customer = customerDAO.findByUsernameAndPassword("aaa", "111");
    	System.out.println("✅ 查詢結果：\n" + "Username: " + customer.getUsername() + "\tPassword: " + customer.getPassword());
    }
    
    // === 測試：查詢帐号是否存在于资料库(注册使用) ===
//    @Test
    public void testFindByUsername() {
    	Customer customer = customerDAO.findByUsername("bbb");
    	System.out.println("✅ 查詢結果：\n" + "Username: " + customer.getUsername() + "\tPassword: " + customer.getPassword());
    }
    
    // === 測試：更新單筆客戶 ===
//    @Test
    public void testUpdate() {
    	Customer customer = customerDAO.findById(1L);
    	customer.setAddress("台北市中山區");
    	
    	customerDAO.update(customer);
    	System.out.println("✅ 查詢結果：\n" + "Username: " + customer.getUsername() + "\tAddress: " + customer.getAddress());	
    }
    
    // === 測試：刪除單筆客戶 ===
//    @Test
    public void testDelete() {
    	// 先新增一筆測試資料
        Customer customer = new Customer();
        customer.setUsername("delete_test");
        customer.setPassword("123456");
        customer.setEmail("delete@example.com");
        customer.setFullName("刪除測試");
        customer.setPhone("0999888777");
        customer.setAddress("測試地址");
        customer.setCreatedAt(LocalDateTime.now());
        
        customerDAO.add(customer); // 新增完後，Hibernate 會幫你回寫 ID
        Long newId = customer.getId();
        String newUsername = customer.getUsername();
        System.out.println("✅ 已建立測試資料，ID = " + newId + "\tUsername = " + newUsername);
        
        // 執行刪除
        customerDAO.delete(newId);
        System.out.println("✅ 已刪除 ID = " + newId);
        
        // 再確認是否真的刪除了
        Customer check = customerDAO.findById(newId);
        if (check == null) {
            System.out.println("✅ 確認刪除成功：ID = " + newId + " 已不存在");
        } 
        else {
            System.err.println("❌ 刪除失敗：資料仍存在！");
        }
    }
    
    // === 測試：查詢 帳號 是否唯一 ===
    @Test
    public void testIsUsernameExists() {
    	String username = "aaaa"; // 之前新增過的帳號
    	boolean result = customerDAO.isUsernameExists(username);
    	
    	if(result) {
    		System.out.println("✅ 帳號 [" + username + "] 已存在");
    	}
    	else {
    		System.out.println("✅ 帳號 [" + username + "] 不存在，可以使用");
    	}	
    }
    
    // === 測試：查詢 email 是否唯一 ===
//    @Test
    public void testIsEmailExists() {
    	String email = "aa@aa.aa";
    	boolean result = customerDAO.isEmailExists(email);
    	
    	if(result) {
    		System.out.println("✅ Email [" + email + "] 已存在");
    	}
    	else {
    		System.out.println("✅ Email [" + email + "] 不存在，可以使用");
    	}
    }

}
