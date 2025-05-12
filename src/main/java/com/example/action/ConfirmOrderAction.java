package com.example.action;

import com.example.pojo.entity.*;
import com.example.pojo.session.SessionCartItem;
import com.example.service.OrderService;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * ✅ 整合最佳版本 ConfirmOrderAction：使用 Service + Map 結構 + 提示 + 清空購物車
 */
public class ConfirmOrderAction extends ActionSupport implements SessionAware {

    private static final long serialVersionUID = 1L;

	private Map<String, Object> session;

    @Autowired
    private OrderService orderService;

    @Override
    public String execute() {
    	
        // ✅ 驗證登入狀態
        Customer customer = (Customer) session.get("currentCustomer");
        if (customer == null) {
        	return LOGIN; // ✅ 等同於 return "login"
        }

        // ✅ 取得購物車內容（使用 Map 結構）
        Map<Long, SessionCartItem> cart = (Map<Long, SessionCartItem>) session.get("cart");
        if (cart == null || cart.isEmpty()) {
            addActionError("購物車為空");
            return ERROR;
        }

        // ✅ 建立訂單
        Order order = new Order();
        order.setCustomer(customer);
        
        LocalDateTime now = LocalDateTime.now();
        order.setCreatedAt(now); // 記錄下訂時間

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (SessionCartItem item : cart.values()) {
            Product product = item.getProduct();
            int qty = item.getQuantity();
            BigDecimal price = BigDecimal.valueOf(product.getPrice());
            BigDecimal subtotal = price.multiply(BigDecimal.valueOf(qty));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setProductName(product.getName());
            orderItem.setUnitPrice(price);
            orderItem.setQuantity(qty);
            orderItem.setTotalPrice(subtotal);

            total = total.add(subtotal);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.setTotalPrice(total);

        // ✅ 儲存訂單（透過 Service）
        orderService.saveOrder(order);

        // ✅ 傳遞訊息至 session 供 JSP 顯示
        session.remove("cart"); // 清空購物車
        session.put("lastOrderId", order.getId());
        session.put("lastOrderTotal", order.getTotalPrice());
        session.put("toastifyMessage", "✅ 訂單已成功建立！");
        
        // ✅ 重點：LocalDateTime ➜ java.util.Date（給 JSTL fmt:formatDate 使用）
        Date legacyDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        session.put("lastOrderTime", legacyDate);

        return SUCCESS;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        this.session = session;
    }
    
}
