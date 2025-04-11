package com.example.pojo.entity;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 商品实体类，表示一件商品。
 */
@Entity
@Table(name = "product") // 映射到 "product" 表
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略：自动增长
	private Long id;

	@Column(name = "name", length = 100, nullable = false) // 对应数据库 "name" 列，不能为空
	private String name;

	@Column(name = "price", nullable = false) // 对应数据库 "price" 列，不能为空
	private Double price;

	@Column(name = "description", length = 255) // 商品描述，可以为空，长度限制为 255
	private String description;

	@Column(name = "stock_quantity", nullable = false) // 商品库存数量，不能为空
	private Integer stockQuantity;

	@Column(name = "create_date")
	private LocalDateTime createDate; // 使用 LocalDateTime 替代 Date 类型

	@ManyToOne // 多对一关系，多个商品属于一个类别
	@JoinColumn(name = "category_id", nullable = false) // 外键 "category_id" 指向 Category 表的主键
	private Category category;

	// Getter 和 Setter 方法
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
}
