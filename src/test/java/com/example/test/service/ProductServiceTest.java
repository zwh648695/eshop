package com.example.test.service;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.CategoryDAO;
import com.example.pojo.entity.Category;
import com.example.pojo.entity.Product;
import com.example.service.ProductService;

/**
 * 測試 ProductService 的功能
 */
@SpringJUnitConfig(locations = "classpath:spring/applicationContext.xml")
@Transactional
@Rollback(false) // 可改為 true 防止污染資料庫
public class ProductServiceTest {
	
	@Autowired
    private ProductService productService;
	
	@Autowired
	private CategoryDAO categoryDAO;

    // ✅ 測試新增商品
//    @Test
    public void testAddProduct() {
        Product product = new Product();
        product.setName("蘋果");
        product.setDescription("香甜又好吃");
        product.setPrice(15.50);
        product.setStockQuantity(10);
        product.setCreateDate(LocalDateTime.now());

        Category category = categoryDAO.findById(1L); // 1L 是資料庫中存在的分類 ID
        product.setCategory(category); // 設定關聯關係

        productService.addProduct(product);
        System.out.println("✅ 新增成功：\n" + "商品名稱：" + product.getName() + "\t商品分類：" + product.getCategory().getName());
    }
    
    // ❌ 測試 重複名稱 和 價格<0 是否會拋例外
//    @Test
    public void testAddProduct_DuplicateName() {
        Product duplicate = new Product();
        duplicate.setName("蘋"); // 已存在名稱
        duplicate.setDescription("重複商品");
        duplicate.setPrice(-80.00);
        duplicate.setStockQuantity(5);
        duplicate.setCreateDate(LocalDateTime.now());

        Category category = categoryDAO.findById(1L);
        duplicate.setCategory(category); // 設定關聯關係

        try {
            productService.addProduct(duplicate); // 嘗試新增重複名稱的商品
            System.err.println("❌ 測試失敗：重複名稱未拋出例外");
        } 
        catch (IllegalArgumentException e) {
            System.out.println("✅ 測試成功：拋出例外訊息 = " + e.getMessage());
        } 
        catch (Exception e) {
            System.err.println("❌ 測試失敗：拋出的不是 IllegalArgumentException，而是 " + e.getClass().getSimpleName());
        }
    }
    
    // 🔍 根據 名稱 查詢
//    @Test
    public void testGetProductByName() {
        Product product = productService.getProductByName("原子筆");
        
        if (product != null) {
            System.out.println("✅ 查詢成功：\n" + "商品名稱：" + product.getName() + "\t商品價格：" + product.getPrice());
        } 
        else {
            System.err.println("❌ 查無此商品名稱");
        }
    }
    
    // 🔍 根據 ID 查詢
//    @Test
    public void testGetProductById() {
//        Product product = productService.getProductByName("測試商品-A");
        Product product = productService.getProductByName("自動鉛筆");
        
        if (product != null) {
            Product result = productService.getProductById(product.getId());
            
            if (result != null) {
                System.out.println("✅ 成功查詢商品 ID：" + result.getId() + "\t商品名稱：" + result.getName());
            } 
            else {
                System.err.println("❌ 查無商品");
            }
        }
        else {
        	System.err.println("❌ 查無商品");
        }
    }
    
    // 🔍 查詢全部商品
//    @Test
    public void testGetAllProducts() {
        List<Product> list = productService.getAllProducts();
        
        if (list != null && !list.isEmpty()) {
            System.out.println("✅ 查詢全部商品：共 " + list.size() + " 筆");
        } 
        else {
            System.err.println("❌ 沒有任何商品");
        }
    }
    
    // 🔍 分頁查詢商品
//    @Test
    public void testGetProductsByPage() {
        List<Product> list = productService.getProductsByPage(1, 6);
        
        if (list != null) {
            System.out.println("✅ 第 1 頁商品數：" + list.size());
            
            for (Product p : list) {
                System.out.println("  - " + p.getName());
            }
        } 
        else {
            System.err.println("❌ 查詢分頁結果為 null");
        }
    }
    
    // 🔍 查詢商品 總筆數
//    @Test
    public void testGetTotalProductCount() {
        int count = productService.getTotalProductCount();
        System.out.println("✅ 商品總數量為：" + count);
    }
    
    // 🔍 查詢商品有幾頁
    @Test
    public void testGetTotalPages() {
    	
    	try {
    		int totalProducts = productService.getTotalProductCount(); // ✅ 取得目前資料總數
            int pageSize = 2; // ✅ 假設每頁顯示2筆資料，可自行調整
            
            int expectedPages = (int) Math.ceil((double) totalProducts / pageSize); // 計算「預期頁數」
            int actualPages = productService.getTotalPages(pageSize); //  計算「實際頁數」
            
            System.out.println("📦 商品總數：" + totalProducts);
            System.out.println("📄 每頁筆數：" + pageSize);
            System.out.println("📌 預期頁數：" + expectedPages);
            System.out.println("📌 實際頁數：" + actualPages);
            
            if (actualPages == expectedPages) {
                System.out.println("✅ 總頁數計算正確！");
            } 
            else {
                System.err.println("❌ 總頁數錯誤：預期 " + expectedPages + "，實際 " + actualPages);
            }
    	}
    	catch (Exception e) {
            System.err.println("❌ 測試過程發生錯誤：" + e.getMessage());
        }
    }
    
    // 🚫 testGetTotalPagesWithInvalidPageSize()：測試異常情況（pageSize = 0）
//    @Test
    public void testGetTotalPagesWithInvalidPageSize() {
        try {
            productService.getTotalPages(0); // 傳入無效值
            System.err.println("❌ 應該要拋出 IllegalArgumentException，但沒有拋出！");
        } 
        catch (IllegalArgumentException e) {
            System.out.println("✅ 成功攔截例外：" + e.getMessage());
        } 
        catch (Exception e) {
            System.err.println("❌ 錯誤例外類型：" + e.getClass().getSimpleName() + "，訊息：" + e.getMessage());
        }
    }
    
    // ✏️ 測試更新商品
//    @Test
    public void testUpdateProduct() {
    	Product product = productService.getProductByName("蘋果");
    	
        if (product != null) {
            product.setStockQuantity(20);
            product.setDescription("已更新商品");

            try {
                productService.updateProduct(product);
                System.out.println("✅ 商品更新成功，新描述：" + product.getDescription());
            } 
            catch (Exception e) {
                System.err.println("❌ 商品更新失敗：" + e.getMessage());
            }
        } 
        else {
            System.err.println("❌ 查無要更新的商品");
        }
    }

    // ❌ 測試刪除商品
//    @Test
    public void testDeleteProduct() {
    	// 1️⃣ 建立測試商品資料
        Product product = new Product();
        product.setName("測試商品-ToDelete");
        product.setPrice(15.5);
        product.setDescription("good");
        product.setStockQuantity(50);
        product.setCreateDate(LocalDateTime.now());

        Category category = categoryDAO.findById(2L);
        product.setCategory(category); // 設定關聯關係

        try {
        	// 使用 Service 新增商品
            productService.addProduct(product);
            
            Long testId = product.getId();
            String testName = product.getName();
            System.out.println("✅ 已建立測試商品，ID = " + testId + "\tName = " + testName);

            // 2️⃣ 執行刪除
            productService.deleteProduct(testId);
            System.out.println("✅ 已刪除商品 ID = " + testId);

            // 3️⃣ 再次查詢驗證
            Product check = productService.getProductById(testId);
            
            if (check == null) {
                System.out.println("✅ 商品確實已刪除：Name = " + testName);
            } 
            else {
                System.err.println("❌ 刪除失敗：商品仍存在！");
            }
        } 
        catch (Exception e) {
            System.err.println("❌ 測試過程中發生例外：" + e.getMessage());
        }
    }

}
