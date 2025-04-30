package com.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.ProductDAO;
import com.example.pojo.entity.Product;
import com.example.service.ProductService;

/**
 * ProductService 實作類，提供商品的業務邏輯。
 */
@Service
@Transactional // 開啟事務管理，避免資料不一致
public class ProductServiceImpl implements ProductService {
	
	@Autowired
    private ProductDAO productDAO;
	
	// ✅ 新增商品（名稱不可重複、價格不可為負），實作 DAO 方法來檢查
	@Override
    public void addProduct(Product product) {
		// 名稱不可重複
		if (productDAO.findByName(product.getName()) != null) {
            throw new IllegalArgumentException("商品名稱已存在，請勿重複！");
        }
		
		// 價格驗證
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("商品價格不能為負數！");
        }
        
        productDAO.add(product);
    }

	// ✅ 根據 ID 查詢 商品
    @Override
    public Product getProductById(Long id) {
        return productDAO.findById(id);
    }
    
    // ✅ 根據 名稱 查詢 商品
    @Override
    public Product getProductByName(String name) {
        return productDAO.findByName(name);
    }


    // ✅ 查詢全部 商品
    @Override
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    // ✅ 更新商品（不可為 null、名稱不能與他人重複、價格不能為負）
    @Override
    public void updateProduct(Product product) {
    	Product existing = productDAO.findById(product.getId());
    	
        if (existing == null) {
            throw new IllegalArgumentException("欲更新的商品不存在！");
        }
        
        // 若名稱有改，且新名稱已存在於其他商品
        Product productWithSameName = productDAO.findByName(product.getName());
        
        if (productWithSameName != null && !productWithSameName.getId().equals(product.getId())) {
            throw new IllegalArgumentException("新的商品名稱已被使用！");
        }

        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("商品價格不能為負數！");
        }

        productDAO.update(product);
    }

    // ✅ 刪除商品（先確認存在）
    @Override
    public void deleteProduct(Long id) {
    	Product product = productDAO.findById(id);
    	
        if (product == null) {
            throw new IllegalArgumentException("欲刪除的商品不存在！");
        }

        productDAO.delete(id);
    }

    // ✅ 分頁查詢 商品
    @Override
    public List<Product> getProductsByPage(int currentPage, int pageSize) {
        return productDAO.findByPage(currentPage, pageSize);
    }
    
 // ✅ 查詢 商品 總筆數
    @Override
    public int getTotalProductCount() {
        return productDAO.countTotalProducts(); // 重用 DAO 方法
    }


    // ✅ 查詢商品總數
    @Override
    public int getTotalPages(int pageSize) {
    	if (pageSize <= 0) {
            throw new IllegalArgumentException("每頁筆數必須大於 0");
        }
    	
    	int total = productDAO.countTotalProducts(); // 調用上面的方法
        return (int) Math.ceil((double) total / pageSize); // 封裝邏輯
        // Math.ceil(...)：無條件進位 → 例如 4.6 變成 5.0
    }

}
