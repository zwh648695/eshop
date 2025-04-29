package com.example.test.dao;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.CategoryDAO;
import com.example.pojo.entity.Category;

@SpringJUnitConfig(locations = "classpath:spring/applicationContext.xml")
@Transactional
@Rollback(false) // 改成 true 可讓測試後資料回滾
public class CategoryDAOTest {
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	// === 測試：新增單筆分類 ===
//	@Test
	public void testadd() {
		Category category = new Category();
        category.setName("玩具");

        categoryDAO.add(category);
        System.out.println("✅ 新增分類成功，ID = " + category.getId());
	}
	
	// === 測試：查詢單筆分類 ===
//	@Test
	public void testFindById() {
		Category category = categoryDAO.findById(1L);
		System.out.println("✅ 查詢結果：\n" + "ID: " +  category.getId() + "\tName: " + category.getName());
	}
	
	// === 測試：查詢全部分類 ===
//	@Test
	public void testFindAll() {
		List<Category> list = categoryDAO.findAll();
		System.out.println("✅ 全部共查詢到 " + list.size() + " 筆分類");
		
		for(Category category : list) {
			System.out.println("ID: " +  category.getId() + "\tName: " + category.getName());
		}
	}
	
	// === 測試：更新單筆分類 ===
	@Test
	public void testUpdate() {
		Category category = categoryDAO.findById(2L);
        category.setName("文具");
        
        categoryDAO.update(category);
        System.out.println("✅ 更新完成：\n" + "ID: " +  category.getId() + "\tName: " + category.getName());
	}
	
	// === 測試：刪除單筆分裂 ===
//	@Test
	public void testDelete() {
		// 先新增一筆測試資料
		Category category = new Category();
		category.setName("測試類");
		categoryDAO.add(category); // 新增完後，Hibernate 會幫你回寫 ID
		
        Long testId = category.getId();
        String testName = category.getName();
        System.out.println("✅ 已建立測試資料，ID = " + testId + "\tName = " + testName);
        
        // 執行刪除
        categoryDAO.delete(testId);
        System.out.println("✅ 已刪除 ID = " + testId);
        
        // 再確認是否真的刪除了
        Category check = categoryDAO.findById(testId);
        if (check == null) {
            System.out.println("✅ 已刪除 Name = " + testName + " 的分類");
        } 
        else {
            System.err.println("❌ 刪除失敗：資料仍存在！");
        }
	}

}
