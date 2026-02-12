package com.arfaz.ecom.sbecom.service;

import com.arfaz.ecom.sbecom.model.Category;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    void addCategory(@RequestBody Category category);
    String deleteCategory(@PathVariable Long categoryId);
    Category updateCategoryById(Long categoryId, Category category);
}
