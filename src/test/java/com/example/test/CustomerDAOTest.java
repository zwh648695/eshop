package com.example.test;

import java.time.LocalDateTime;

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
	
	// === 測試：新增 ===
    @Test
    public void testAdd() {
        Customer customer = new Customer();
        customer.setUsername("aaa");
        customer.setPassword("111");
        customer.setEmail("aaa@example.com");
        customer.setFullName("AAA");
        customer.setPhone("0911111111");
        customer.setAddress("台北市");
        customer.setCreatedAt(LocalDateTime.now());

        customerDAO.add(customer);
        System.out.println("✅ 新增成功，ID = " + customer.getId());
    }

}
