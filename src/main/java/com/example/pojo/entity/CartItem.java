package com.example.pojo.entity;


import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 购物车项实体类，对应 cart_item 表。
 * 表示一个商品被加入到购物车中的记录，包含商品、数量和小计。
 */
@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 每个项属于一个购物车
    @JoinColumn(name = "cart_id") // 默认就是连到 Cart 的主键 id
    private Cart cart;

    @ManyToOne // 每个项关联一个商品
    @JoinColumn(name = "product_id") // 默认就是连到 Product 的主键 id
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity; // 商品数量
    
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice; // 加入購物車當時的商品價格

    @Column(name = "total_price")
    private BigDecimal totalPrice; // 此项小计（商品单价 × 数量）

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

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

}
