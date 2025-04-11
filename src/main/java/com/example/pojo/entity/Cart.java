package com.example.pojo.entity;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * 购物车实体类，对应 cart 表。
 * 每个购物车属于一个客户，并包含多个购物车商品项（CartItem）。
 */
@Entity
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 多个购物车可以属于同一个客户
    @JoinColumn(name = "customer_id") // 默认就是连到 Customer 的主键 id
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems; // 所有购物车项

    @Column(name = "total_price")
    private BigDecimal totalPrice; // 购物车总价

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
    
}
