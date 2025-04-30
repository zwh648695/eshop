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
	
	private List<Product> products;
	private int currentPage = 1; // 預設第 1 頁
	private int pageSize = 6; // 一頁顯示 6 筆
	private int totalPages;
	
	@Override
    public String execute() {
		products = productService.getProductsByPage(currentPage, pageSize);
        totalPages = productService.getTotalPages(pageSize);
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
