package com.arfaz.ecom.sbecom.service;

import com.arfaz.ecom.sbecom.model.Category;
import com.arfaz.ecom.sbecom.payloads.CategoryDTO;
import com.arfaz.ecom.sbecom.payloads.CategoryResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer  pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO addCategory(@RequestBody CategoryDTO categoryDTO);
    CategoryDTO deleteCategory(@PathVariable Long categoryId);
    CategoryDTO updateCategoryById(Long categoryId, CategoryDTO categoryDTO);
}
