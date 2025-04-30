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
//	@Test
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
	
	// === 測試：根據頁數查詢商品 ===
//	@Test
	public void testFindByPage() {
		List<Product> products = productDAO.findByPage(1, 5);
		
        for (Product p : products) {
            System.out.println("🛒 商品名稱：" + p.getName() + " / 價格：" + p.getPrice());
        }
	}
	
	// === 測試：取得全部商品的筆數 ===
//	@Test
	public void testCountTotalProducts() {
		int total = productDAO.countTotalProducts();
        System.out.println("📦 商品總筆數：" + total);
	}
	
	// === 測試：根據名稱查詢商品 ===
	@Test
	public void testFindByName() {
		// ✅ 建立測試分類
	    Category testCategory = new Category();
	    testCategory.setName("測試分類");
	    categoryDAO.add(testCategory); // 儲存到 DB，避免 foreign key 錯誤
	    
		// 準備一筆測試資料
	    Product testProduct = new Product();
	    testProduct.setName("測試商品123");
	    testProduct.setDescription("這是用於 findByName 的測試商品");
	    testProduct.setPrice(199.99);
	    testProduct.setStockQuantity(1);
	    testProduct.setCategory(testCategory); // ❗ 重點：設為分類實體
	    testProduct.setCreateDate(LocalDateTime.now());
	    
	    // 先新增商品
	    productDAO.add(testProduct);

	    // 呼叫 findByName() 查詢
	    Product result = productDAO.findByName("測試商品123");

	    if (result != null) {
	    	System.out.println("✅ 查詢成功，商品名稱：" + result.getName() + "，分類：" + result.getCategory().getName());

	        Long productId = result.getId();
	        Long categoryId = result.getCategory().getId();

	        // ❌❌❌ 先刪除商品，再刪除分類（避免外鍵限制錯誤）
	        productDAO.delete(productId);
	        System.out.println("✅ 已刪除測試商品 ID = " + productId);

	        // ❌❌❌ 再刪分類
	        categoryDAO.delete(categoryId);
	        System.out.println("✅ 已刪除分類 ID = " + categoryId);

	        // 驗證商品已刪除
	        Product deleted = productDAO.findById(productId);
	        
	        if (deleted == null) {
	            System.out.println("✅ 商品已成功刪除");
	        } 
	        else {
	            System.err.println("❌ 商品刪除失敗！");
	        }
	    } 
	    else {
	        System.out.println("❌ 查無該名稱商品！");
	    }
	}

}
