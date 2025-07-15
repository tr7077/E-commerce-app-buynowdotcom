package com.teorerras.buynowdotcom.service.category;

import com.teorerras.buynowdotcom.model.Category;

import java.util.List;

public interface ICategoryService {
    Category addCategory(Category category);
    Category updateCategory(Category category, Long categoryId);
    void deleteCategory(Long categoryId);
    List<Category> getAllCategories();
    Category findCategoryByName(String name);
    Category findCategoryById(Long categoryId);
}
