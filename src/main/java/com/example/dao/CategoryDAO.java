package com.example.dao;

import java.util.List;

import com.example.pojo.entity.Category;

public interface CategoryDAO {
	
	// 新增分類
    void add(Category category);

    // 根據 ID 查詢分類
    Category findById(Long id);

    // 查詢全部分類
    List<Category> findAll();

    // 更新分類資料
    void update(Category category);

    // 根據 ID 刪除分類
    void delete(Long id);

}
