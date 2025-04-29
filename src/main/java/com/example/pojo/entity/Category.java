package com.example.pojo.entity;

import javax.persistence.*;
import java.util.List;

/**
 * 商品类别实体类，表示商品的分类。
 */
@Entity
@Table(name = "category") // 映射到 "category" 表
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 主键生成策略：自动增长
	private Long id;

	@Column(name = "name", length = 50, nullable = false) // 对应数据库 "name" 列，不能为空
	private String name;

	@OneToMany(mappedBy = "category") // 一对多关系，mappedBy 由 Product 类中的 "category" 字段维护关系
	private List<Product> products; // 对应当前分类下的所有商品

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

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + "]";
	}
	
}
