package com.example.test.service;

import com.example.pojo.entity.Customer;
import com.example.pojo.entity.Order;
import com.example.pojo.entity.OrderItem;
import com.example.pojo.entity.Product;
import com.example.dao.CustomerDAO;
import com.example.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringJUnitConfig(locations = "classpath:spring/applicationContext.xml")
@Transactional
@Rollback(false)
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerDAO customerDAO;

    // ✅ 測試 OrderService 儲存訂單（含明細）
//    @Test
    public void testSaveOrderWithItems() {
        Customer customer = customerDAO.findById(2L);
        if (customer == null) {
            System.err.println("❌ 顧客不存在");
            return;
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setCreatedAt(LocalDateTime.now());

        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        // ✅ 只測試加入「蘋果」
        Product product = new Product();
        product.setId(8L); // 蘋果的 ID

        String name = "蘋果";
        BigDecimal price = new BigDecimal("15.5");
        int quantity = 3; // 假設買 3 顆

        OrderItem item = new OrderItem();
        item.setOrder(order);
        item.setProduct(product);
        item.setProductName(name);
        item.setUnitPrice(price);
        item.setQuantity(quantity);
        item.setTotalPrice(price.multiply(BigDecimal.valueOf(quantity)));

        total = total.add(item.getTotalPrice());
        items.add(item);

        order.setOrderItems(items);
        order.setTotalPrice(total);

        try {
            orderService.saveOrder(order);
            System.out.println("✅ 成功透過 Service 儲存只含蘋果的訂單，ID = " + order.getId());
        } 
        catch (Exception e) {
            System.err.println("❌ 儲存失敗：" + e.getMessage());
        }
    }

    // 🔍 查詢單筆訂單（含明細）
//    @Test
    public void testGetOrderById() {
        Long orderId = 2L;
        Order order = orderService.getOrderById(orderId);
        
        if (order != null) {
            System.out.println("✅ 查詢成功：訂單 ID = " + order.getId());
            System.out.println("顧客姓名: " + order.getCustomer().getFullName());
            System.out.println("總金額: NT$" + order.getTotalPrice());
            for (OrderItem item : order.getOrderItems()) {
                System.out.println("- " + item.getProductName() + " x " + item.getQuantity() + "，單價: NT$" + item.getUnitPrice() + "，小計: NT$" + item.getTotalPrice());
            }
        } 
        else {
            System.err.println("❌ 查無訂單");
        }
    }

    // 🔍 查詢某顧客的所有訂單
    @Test
    public void testGetOrdersByCustomerId() {
        Long customerId = 2L;
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);
        
        System.out.println("✅ 顧客訂單數量：" + orders.size());
        
        for (Order order : orders) {
            System.out.println("訂單 ID: " + order.getId() + "，金額: NT$" + order.getTotalPrice() + "，建立時間: " + order.getCreatedAt());
        }
    }
    
}
