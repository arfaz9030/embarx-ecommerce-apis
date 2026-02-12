package com.arfaz.ecom.sbecom.service;

import com.arfaz.ecom.sbecom.model.Category;
import com.arfaz.ecom.sbecom.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService{

    private List<Category> categories = new ArrayList<>();
//    private Long nextId = 1L;
// before creating nextId user supposed to give in reqst-body it's not realistic
//    so created nextId n passes setId()
//    we are removing nextId because we are generating automatically using
//    Jpa annotation Id, Generated

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
//before findAll() we used to return categories List not Jpa
        return categoryRepository.findAll();

    }

    @Override
    public void addCategory(Category category) {
// category.setCategoryId(nextId++); we are removing nextId because we are generating automatically using
//  Jpa annotation Id, Generated
//        categories.add(category); removing add method of AL
//        due to usage of Jpa to save in DB
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
/*
        first check in DB all categories presnt or not
        n we use optimised one findById()
        List<Category> categories1 = categoryRepository.findAll();
*/
        Category deleteCategoryId = categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        /*Before JPA we are using below to delete category by id need to check if id is present by matching use filter
         Category deleteCategoryId= categories1
                   .stream()
                   .filter(c -> c.getCategoryId().equals(categoryId))
                   .findFirst()        // returns Optional<Category>
                   .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found")); // converts Optional<Category> to category*/

           categoryRepository.delete(deleteCategoryId);
        return "Category with categoryId: " + categoryId + " deleted successfully !!";
    }

    @Override
    public Category updateCategoryById(Long categoryId, Category category) {
//   before storing in DB we store data in AL so search chatgpt>springboot>stream filter> "How object is referring which came from stream in AL"
//        fetch all rows from DB
       /*
        first check in DB all categories presnt or not below
        n we use optimised one findById()
        List<Category> categories1 = categoryRepository.findAll();
*/
       Category categoryIdfind = categoryRepository.findById(categoryId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        /* Before using above JPA method we used below code
        Category categoryIdfind= categories1.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));*/
        categoryIdfind.setCategoryId(categoryId);
        categoryIdfind.setCategoryName(category.getCategoryName());
      Category updatingCategory = categoryRepository.save(categoryIdfind);
        return updatingCategory;
    }
}
