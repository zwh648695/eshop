package com.example.pojo.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 訂單項目實體類，對應 order_item 表。
 * 每筆記錄代表一個商品的購買細項（含商品快照）。
 */
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // 所屬訂單

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // 原始商品關聯（可選）

    @Column(name = "product_name", length = 100)
    private String productName; // 商品名稱快照

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice; // 單價快照

    @Column(name = "quantity", nullable = false)
    private Integer quantity; // 購買數量

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice; // 明細總價

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
