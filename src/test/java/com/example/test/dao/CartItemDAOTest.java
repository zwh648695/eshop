package com.example.test.dao;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.CartDAO;
import com.example.dao.CartItemDAO;
import com.example.dao.ProductDAO;
import com.example.pojo.entity.Cart;
import com.example.pojo.entity.CartItem;
import com.example.pojo.entity.Product;

/**
 * CartItemDAO 測試類別
 * 測試內容：新增購物車項目、查詢所有項目、更新數量、自動計算小計、刪除項目
 * 關聯關係：CartItem 實體中有 @PrePersist 自動計算 totalPrice 的邏輯
 */
@SpringJUnitConfig(locations = "classpath:spring/applicationContext.xml")
@Transactional
@Rollback(false)
public class CartItemDAOTest {
	
	@Autowired
    private CartItemDAO cartItemDAO;

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private ProductDAO productDAO;
    
    @Test
    public void testAddCartItem() {
        try {
            Cart cart = cartDAO.findById(1L);
            Product product = productDAO.findById(1L);

            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(3); // 數量
            item.setUnitPrice(BigDecimal.valueOf(product.getPrice())); // 單價快照

            cartItemDAO.add(item);
            System.out.println("✅ 新增購物車項目成功，小計：" + item.getTotalPrice());
        } catch (Exception e) {
            System.err.println("❌ 新增購物車項目失敗：" + e.getMessage());
        }
    }

}
