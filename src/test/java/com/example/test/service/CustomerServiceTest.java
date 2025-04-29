package com.example.test.service;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.example.pojo.entity.Customer;
import com.example.service.CustomerService;

/**
 * CustomerService 測試類，測試業務邏輯整合是否正確。
 */
@SpringJUnitConfig(locations = "classpath:spring/applicationContext.xml")
@Transactional
@Rollback(false) // 設為 false 可觀察 DB 實際資料，測試完成後可設 true
public class CustomerServiceTest {
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void testAddCustomer() {
		Customer customer = new Customer();
        customer.setUsername("fff");
        customer.setPassword("555");
        customer.setEmail("eee@example.com");
        customer.setFullName("EEE");
        customer.setPhone("0955555555");
        customer.setAddress("新北市");
        customer.setCreatedAt(LocalDateTime.now());
        
        boolean result = customerService.addCustomer(customer);
        
        if (result) {
            System.out.println("✅ 測試：新增用戶成功");
        } 
        else {
            System.out.println("❌ 新增失敗（可能 帳號 或 Email 已存在）");
        }
	}
	
//	@Test
	public void testGetCustomerById() {
		Customer customer = customerService.getCustomerById(3L);
		
		if(customer != null) {
			System.out.println("✅ 查詢結果：\n" + "ID: " + customer.getId() + "\tName: " + customer.getFullName());
		}
		else {
			System.out.println("❌ 查詢失敗：此客戶不存在");
		}
	}
	
//	@Test
	public void testGetCustomerByUsername() {
		Customer customer = customerService.getCustomerByUsername("bbbb");
		
		if(customer != null) {
			System.out.println("✅ 查詢結果：\n" + "ID: " + customer.getId() + "\tUsername: " + customer.getUsername());
		}
		else {
			System.out.println("❌ 查詢失敗：此客戶不存在");
		}
	}
	
//	@Test
	public void testLoginByUsernameAndPassword() {
		Customer customer = customerService.loginByUsernameAndPassword("aaa", "111");
		
		if(customer != null) {
			System.out.println("✅ 測試成功：登入成功");
			System.out.println("Username: " + customer.getUsername() + "\tAddress: " + customer.getAddress());
		}
		else {
            System.out.println("❌ 測試失敗：登入失敗，帳號或密碼錯誤");
        }
	}
	
//	@Test
	public void testChangePassword() {
		// 先輸入一次 帳號 確認資料
		Customer customer = customerService.getCustomerByUsername("bbb");

		// 更改密碼
        boolean result = customerService.changePassword(customer.getId(), "2222", "222");

        // 重新登入更改過密碼的帳號
        if (result) {
            Customer newCustomer = customerService.loginByUsernameAndPassword("bbb", "222");
            
            if (newCustomer != null) {
                System.out.println("✅ 測試成功：密碼修改成功並可登入");
                System.out.println("Username: " + newCustomer.getUsername() + "\tPassword: " + newCustomer.getPassword());
            } 
            else {
                System.out.println("❌ 測試失敗：修改後登入失敗");
            }
        } 
        else {
            System.out.println("❌ 測試失敗：密碼修改失敗");
        }
	}
	
//	@Test
	public void testUpdateCustomer() {
		Customer customer = customerService.getCustomerByUsername("aaa");
		customer.setAddress("台北市");

        boolean result = customerService.updateCustomer(customer);

        if (result) {
            Customer newCustomer = customerService.getCustomerByUsername("aaa");
            
            if (newCustomer.getAddress().equals("台北市")) {
                System.out.println("✅ 測試成功：個人資料更新成功");
                System.out.println("Username: " + newCustomer.getUsername() + "\tAddress: " + newCustomer.getAddress());
            } 
            else {
                System.out.println("❌ 測試失敗：資料更新不一致");
            }
        } 
        else {
            System.out.println("❌ 測試失敗：更新資料操作失敗");
        }
	}
	
//	@Test
	public void testDeleteCustomer() {
		// 先新增一筆測試資料
        Customer customer = new Customer();
        customer.setUsername("delete_test");
        customer.setPassword("123456");
        customer.setEmail("delete@example.com");
        customer.setFullName("刪除測試");
        customer.setPhone("0999888777");
        customer.setAddress("測試地址");
        customer.setCreatedAt(LocalDateTime.now());
        
        customerService.addCustomer(customer); // 新增完後，Hibernate 會幫你回寫 ID
        Long newId = customer.getId();
        String newUsername = customer.getUsername();
        System.out.println("✅ 已建立測試資料，ID = " + newId + "\tUsername = " + newUsername);
        
        // 執行刪除
        boolean result = customerService.deleteCustomer(newId);
        System.out.println("✅ 已刪除 ID = " + newId);
        
        // 再確認是否真的刪除了
        if (result) {
            System.out.println("✅ 確認刪除成功：Username = " + newUsername + " 已不存在");
        } 
        else {
            System.err.println("❌ 刪除失敗：資料仍存在！");
        }
	}
	
//	@Test
	public void testGetAllCustomers() {
		List<Customer> listAll = customerService.getAllCustomers();
		System.out.println("✅ 查詢全部，筆數：" + listAll.size());
    	
    	for(Customer customer : listAll) {
    		System.out.println("ID: " + customer.getId() + "\tName: " + customer.getFullName());
    	}
	}
	
//	@Test
	public void testIsUsernameExists() {
		boolean exists = customerService.isUsernameExists("aaa");
        System.out.println("帳號是否存在：" + exists);
	}
	
//	@Test
    public void testIsEmailExists() {
        boolean exists = customerService.isEmailExists("test@example.com");
        System.out.println("Email 是否存在：" + exists);
    }

}
