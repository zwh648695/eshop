package com.example.test.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.CartDAO;
import com.example.dao.CustomerDAO;
import com.example.pojo.entity.Cart;
import com.example.pojo.entity.Customer;

/**
 * CartDAO 測試類別
 * 測試內容包含：新增購物車、查詢全部、更新總價、刪除購物車
 * 關聯關係：每台購物車需綁定一位顧客（Customer）
 */
@SpringJUnitConfig(locations = "classpath:spring/applicationContext.xml")
@Transactional
@Rollback(false) // 設為 false 可讓資料寫入 DB（方便手動檢查）
public class CartDAOTest {
	
	@Autowired
    private CartDAO cartDAO;

    @Autowired
    private CustomerDAO customerDAO;
	
	// 測試新增一台購物車，並指定給現有的顧客（customer_id = 1）
//    @Test
    public void testAddCart() {
    	try {
            // 從資料庫查詢一位現有顧客（假設已有 ID = 1 的客戶）
            Customer customer = customerDAO.findById(2L);

            // 建立購物車並關聯客戶
            Cart cart = new Cart();
            cart.setCustomer(customer);
            cart.setCartItems(new ArrayList<>()); // 初始化購物車項目列表

            // 儲存購物車
            cartDAO.add(cart);
            System.out.println("✅ 成功新增購物車，ID = " + cart.getId());
        } 
    	catch (Exception e) {
            System.err.println("❌ 新增購物車失敗：" + e.getMessage());
            e.printStackTrace();
        }
    }
    
    // 測試根據 ID 查詢購物車
//    @Test
    public void testFindById() {
        Cart cart = cartDAO.findById(1L);
        
        if (cart != null) {
            System.out.println("✅ 查詢成功：\n" + "Cart_ID： " + cart.getId() + "\tCustomer_Name： " + cart.getCustomer().getFullName());
        } 
        else {
            System.err.println("❌ 查無此購物車！");
        }
    }
    
    // 測試查詢某使用者的所有購物車
//    @Test
    public void testFindAllByCustomerId() {
        List<Cart> carts = cartDAO.findAllByCustomerId(1L);
        System.out.println("✅ 查到此用户的購物車筆數：" + carts.size());
        
        for (Cart cart : carts) {
            System.out.println("Cart_ID： " + cart.getId() + "\tCustomer_Name： " + cart.getCustomer().getFullName());
        }
    }
    
    // 測試更新購物車（總價）
//    @Test
    public void testUpdateCart() {
        Cart cart = cartDAO.findById(1L);
        cart.setTotalPrice(new BigDecimal("999.99"));
        cartDAO.update(cart);
        System.out.println("✅ 更新購物車總價成功，新總價：" + cart.getTotalPrice());
    }

    // 測試刪除購物車
//    @Test
    public void testDeleteCart() {
        cartDAO.delete(1L);
        System.out.println("✅ 已刪除 ID = 1 的購物車");
    }
    
    // 查詢所有購物車資料
//    @Test
    public void testFindAll() {
    	List<Cart> list = cartDAO.findAll();
        System.out.println("✅ 購物車總數：" + list.size());
        
        for (Cart cart : list) {
            System.out.println("Cart_ID： " + cart.getId() + "\tCustomer_Name： " + cart.getCustomer().getFullName());
        }
    }

}
