package com.example.test.dao;

import com.example.dao.OrderDAO;
import com.example.pojo.entity.Order;
import com.example.pojo.entity.OrderItem;
import com.example.pojo.entity.Customer;
import com.example.pojo.entity.Product;
import com.example.dao.CustomerDAO;
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
public class OrderDAOTest {

    @Autowired
    private OrderDAO orderDAO;

    @Autowired
    private CustomerDAO customerDAO;

    // ✅ 建立訂單（含明細）完整測試
//    @Test
    public void testSaveOrderWithItems() {
    	
    	// 🔸 從資料庫查詢一位存在的顧客（ID=1），此顧客將作為這筆訂單的擁有者。
        Customer customer = customerDAO.findById(1L);
        
        // 🔸 如果查不到這位顧客就不進行後續操作（避免 NullPointerException）
        if (customer == null) {
            System.err.println("❌ 顧客不存在！");
            return;
        }

        // 🔸 建立新的訂單物件，並指定顧客與建立時間。
        Order order = new Order();
        order.setCustomer(customer);
        order.setCreatedAt(LocalDateTime.now());

        // 🔸 建立空的明細列表與總金額初始化（用來累加）
        List<OrderItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        // 🔸 訂單將使用你資料庫中已存在的商品 ID（1為自動鉛筆、2為原子筆）
        Long[] productIds = {1L, 2L};

        // 🔁 進入迴圈，建立每筆明細
        for (Long productId : productIds) {
            Product product = new Product();
            product.setId(productId); // 關聯資料庫實際的商品 ID

            // ✅ 商品快照名稱與價格根據 ID 手動對應（更真實）
            String productName;
            BigDecimal unitPrice;
            int quantity = 2; // 假設每樣商品購買 2 個

            if (productId == 1L) {
                productName = "自動鉛筆";
                unitPrice = new BigDecimal("25.5");
            } 
            else if (productId == 2L) {
                productName = "原子筆";
                unitPrice = new BigDecimal("15.5");
            } 
            else {
                continue; // 跳過不存在的商品
            }
        // 🔸 這裡是手動設定商品快照資訊（名稱與單價），目的是：
        // ✅ 即使將來商品資料變更，這筆訂單的紀錄仍保持當下快照值

            // 🔸 建立單一訂單項目，資料包括商品名稱、價格、數量、小計金額
            // 🔸 設定 item.setOrder(order) 是為了 Hibernate 能夠 cascade 儲存（雙向關聯）
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setProductName(productName);
            item.setUnitPrice(unitPrice);
            item.setQuantity(quantity);
            item.setTotalPrice(unitPrice.multiply(BigDecimal.valueOf(quantity)));

            // 🔸 小計累加進總金額 total
            // 🔸 將明細項目加入訂單的 List<OrderItem>
            total = total.add(item.getTotalPrice());
            items.add(item);
        }

        // 🔸 明細與總金額 掛回訂單 設定進 Order 實體
        // 🔸 Hibernate 會自動透過 CascadeType.ALL 把所有明細一併寫入 order_item 資料表
        order.setOrderItems(items);
        order.setTotalPrice(total);

        // 🔸 使用 DAO 儲存訂單（包含明細）
        // 🔸 若成功印出訂單 ID，失敗則顯示錯誤訊息（方便除錯）
        try {
            orderDAO.save(order);
            System.out.println("✅ 成功儲存訂單與明細，訂單 ID = " + order.getId());
        } 
        catch (Exception e) {
            System.err.println("❌ 儲存失敗：" + e.getMessage());
        }
    }

    // 🔍 查詢單一訂單含明細
//    @Test
    public void testFindByIdWithItems() {
        Order order = orderDAO.findById(1L);
        
        if (order != null) {
            System.out.println("✅ 查詢訂單 ID = " + order.getId());
            System.out.println("顧客名稱: " + order.getCustomer().getFullName());
            System.out.println("明細項目共 " + order.getOrderItems().size() + " 筆");
            for (OrderItem item : order.getOrderItems()) {
                System.out.println("- 商品: " + item.getProductName() + "，數量: " + item.getQuantity() + "，單價: NT$" + item.getUnitPrice() + "，小計: NT$" + item.getTotalPrice());
            }
        } 
        else {
            System.err.println("❌ 查無此訂單！");
        }
    }

    // 🔍 查詢顧客所有訂單
    @Test
    public void testFindByCustomerId() {
        List<Order> orders = orderDAO.findByCustomerId(1L);
        
        System.out.println("✅ 該顧客訂單數量：" + orders.size());
        
        for (Order order : orders) {
            System.out.println("訂單 ID：" + order.getId() + "，總金額：NT$" + order.getTotalPrice());
        }
    }
    
}
