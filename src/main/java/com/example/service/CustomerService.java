package com.example.service;

import java.util.List;

import com.example.pojo.entity.Customer;

/**
 * 客戶服務層介面，定義常用的業務操作方法。
 * 主要負責註冊、登入、更新資料、修改密碼等功能。
 */
public interface CustomerService {
	
	/**
     * 註冊新客戶
     * @param customer 客戶資料
     * @return true 表示註冊成功；false 表示帳號已存在
     */
    boolean addCustomer(Customer customer);
    
    /**
     * 根據 ID 查詢客戶
     * @param id 客戶主鍵
     * @return 對應的 Customer 物件，若找不到則為 null
     */
    Customer getCustomerById(Long id);
    
    /**
     * 根據帳號查詢客戶
     * @param username 帳號
     * @return 對應的 Customer 物件，若找不到則為 null
     */
    Customer getCustomerByUsername(String username);

    /**
     * 登入驗證
     * @param username 帳號
     * @param password 密碼
     * @return 登入成功則回傳 Customer 物件，否則為 null
     */
    Customer loginByUsernameAndPassword(String username, String password);

    /**
     * 修改密碼（需驗證舊密碼）
     * @param customerId 客戶 ID
     * @param oldPassword 舊密碼
     * @param newPassword 新密碼
     * @return true 表示修改成功；false 表示密碼錯誤或找不到客戶
     */
    boolean changePassword(Long customerId, String oldPassword, String newPassword);

    /**
     * 更新客戶基本資料（不含密碼）
     * @param updatedCustomer 欲更新的資料物件
     * @return true 表示更新成功；false 表示找不到客戶
     */
    boolean updateCustomer(Customer updatedCustomer);

    /**
     * 根據 ID 刪除客戶
     * @param id 客戶主鍵
     * @return true 表示刪除成功；false 表示找不到資料
     */
    boolean deleteCustomer(Long id);
    
    /**
     * 查詢全部客戶資料
     * @return 客戶列表
     */
    List<Customer> getAllCustomers();
    
    /**
     * 查詢 註冊帳號 是否唯一
     * @param username
     * @return true表示帳號唯一；false表示帳號已存在重複了
     */
    boolean isUsernameExists(String username);
    
    /**
     * 查詢 註冊email 是否唯一
     * @param email
     * @return true表示email唯一；false表示email已存在重複了
     */
    boolean isEmailExists(String email);

}
