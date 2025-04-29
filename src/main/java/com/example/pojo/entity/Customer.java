package com.example.pojo.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

/**
 * 客戶實體類，對應 customer 表。
 * 包含使用者基本資料、購物車與訂單資訊。
 */
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 主鍵 ID

    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username; // 使用者帳號（唯一）

    @Column(name = "password", length = 255, nullable = false)
    private String password; // 密碼（建議加密）

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email; // 電子郵件（唯一）

    @Column(name = "full_name", length = 100)
    private String fullName; // 姓名

    @Column(name = "phone", length = 20)
    private String phone; // 電話

    @Column(name = "address", length = 255)
    private String address; // 收貨地址

    @Column(name = "created_at")
    private LocalDateTime createdAt; // 註冊時間
    
    // ✅ 一位客戶可以有多個購物車
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;
    
    // ✅ 一位客戶可以有多張訂單
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    // === Getter 和 Setter 方法 ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @PrePersist
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }

	public List<Cart> getCarts() {
		return carts;
	}

	public void setCarts(List<Cart> carts) {
		this.carts = carts;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
    
}
