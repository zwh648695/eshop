package com.example.test.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.CategoryDAO;
import com.example.dao.ProductDAO;
import com.example.pojo.entity.Category;
import com.example.pojo.entity.Product;

@SpringJUnitConfig(locations = "classpath:spring/applicationContext.xml")
@Transactional
@Rollback(false) // 改成 true 可讓測試後資料回滾
public class ProductDAOTest {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	// === 測試：新增單筆商品 ===
//	@Test
	public void testAdd() {
		Product product = new Product();
        product.setName("原子筆");
        product.setPrice(15.5);
        product.setDescription("good");
        product.setStockQuantity(50);
        product.setCreateDate(LocalDateTime.now());

        Category category = categoryDAO.findById(1L); // 1L 是資料庫中存在的分類 ID
        product.setCategory(category); // 設定關聯關係

        productDAO.add(product);
        System.out.println("✅ 新增商品成功，ID = " + product.getId());
	}
	
	// === 測試：查詢單筆商品 ===
//	@Test
	public void testFindById() {
		Product product = productDAO.findById(1L);
		System.out.println("✅ 查詢結果：\n" + "Name： " + product.getName() + "\t分類：" + product.getCategory().getName());
	}
	
	// === 測試：查詢全部分類 ===
//	@Test
	public void testFindAll() {
		List<Product> all = productDAO.findAll();
        System.out.println("✅ 全部共查詢到 " + all.size() + " 項商品");
        
        for (Product product : all) {
            System.out.println("Name： " + product.getName() + "\tPrice：" + product.getPrice());
        }
	}
	
	// === 測試：更新單筆分類 ===
//	@Test
	public void testUpdate() {
		// 1️⃣ 查出要修改的商品（假設 ID = 1）
		Product product  = productDAO.findById(1L);
		
		// 2️⃣ 查出新的分類（假設 ID = 2 是你想換的分類）
		Category newCategory = categoryDAO.findById(2L);
		
		// 3️⃣ 設定新的分類給商品
	    product.setCategory(newCategory);
	    
	    // 4️⃣ 執行更新
	    productDAO.update(product);
	    
	    System.out.println("✅ 更新完成：\n" + "Name： " + product.getName() + "\t分類： " + product.getCategory());
	}
	
	// === 測試：刪除單筆商品 ===
	@Test
	public void testDelete() {
		// 先新增一筆測試資料
		Product product = new Product();
        product.setName("測試商品");
        product.setPrice(15.5);
        product.setDescription("good");
        product.setStockQuantity(50);
        product.setCreateDate(LocalDateTime.now());

        Category category = categoryDAO.findById(1L); 
        product.setCategory(category);
        
		productDAO.add(product); // 新增完後，Hibernate 會幫你回寫 ID
        Long testId = product.getId();
        String testName = product.getName();
        System.out.println("✅ 已建立測試資料，ID = " + testId + "\tName = " + testName);
        
        // 執行刪除
        productDAO.delete(testId);
        System.out.println("✅ 已刪除 ID = " + testId);
        
        // 再確認是否真的刪除了
        Product check = productDAO.findById(testId);
        if (check == null) {
            System.out.println("✅ 已刪除 Name = " + testName + " 的商品");
        } 
        else {
            System.err.println("❌ 刪除失敗：資料仍存在！");
        }
	}

}
