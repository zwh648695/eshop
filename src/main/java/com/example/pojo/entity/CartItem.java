package com.example.pojo.entity;


import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 購物車項目實體類，對應 cart_item 表。
 * 每個項目代表一件加入購物車的商品，包含商品、數量、單價與小計。
 */
@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 多個項目屬於同一台購物車
    @JoinColumn(name = "cart_id") // 默认就是连到 Cart 的主键 id
    private Cart cart;

    @ManyToOne // 每個項目對應一個商品
    @JoinColumn(name = "product_id") // 默认就是连到 Product 的主键 id
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity; // 商品数量
    
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice; // 加入購物車當時的商品價格

    @Column(name = "total_price")
    private BigDecimal totalPrice; // 此项小计（商品单价 × 数量）
    
    // 🔄 每次新增或更新時，自動重新計算小計
    @PrePersist // 第一次存入資料庫前會執行
    @PreUpdate // 每次更新前會執行
    private void calculateTotalPrice() {
        if (unitPrice != null && quantity != null) { // ✅ 檢查 unitPrice 和 quantity 是否都有值
            this.totalPrice = unitPrice.multiply(BigDecimal.valueOf(quantity)); // quantity 是 Integer，用 BigDecimal.valueOf(...) 轉換才能進行乘法
        }
    }

    // -------------------- Getter / Setter --------------------
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

}
