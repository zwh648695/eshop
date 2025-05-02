package com.example.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.pojo.entity.Product;
import com.example.service.ProductService;
import com.opensymphony.xwork2.ActionSupport;

public class EshopAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private ProductService productService;
	
	private List<Product> products; // 當前頁的商品資料列表，提供給 JSP 顯示
	private int currentPage = 1; // 使用者請求的目前頁碼，預設為第 1 頁
	private int pageSize = 6; // 每頁要顯示的商品數量，這裡設定為 6 筆
	private int totalPages; // 根據總商品數量及 pageSize 計算得出來的總頁數
	
	@Override
    public String execute() {

        totalPages = productService.getTotalPages(pageSize);  // 查總頁數
        
        if (totalPages < 1) totalPages = 1;  // 沒有商品也保底 1 頁
     
        if (currentPage < 1) currentPage = 1;  // 防呆：頁碼過小
        
        if (currentPage > totalPages) currentPage = totalPages; // 防呆：頁碼過大
        
        products = productService.getProductsByPage(currentPage, pageSize); // 查當前頁資料
        
        return SUCCESS;
    }
	
	// --- Getter / Setter 給 JSP 用 ---
    public List<Product> getProducts() {
        return products;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

}
