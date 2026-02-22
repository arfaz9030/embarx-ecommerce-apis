package com.arfaz.ecom.sbecom.service;

import com.arfaz.ecom.sbecom.exceptions.APIException;
import com.arfaz.ecom.sbecom.exceptions.ResourceNotFoundException;
import com.arfaz.ecom.sbecom.model.Category;
import com.arfaz.ecom.sbecom.payloads.CategoryDTO;
import com.arfaz.ecom.sbecom.payloads.CategoryResponse;
import com.arfaz.ecom.sbecom.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private List<Category> categories = new ArrayList<>();
//    private Long nextId = 1L;
// before creating nextId user supposed to give in reqst-body it's not realistic
//    so created nextId n passes setId()
//    we are removing nextId because we are generating automatically using
//    Jpa annotation Id, Generated

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize) {
//before findAll() we used to return categories List not Jpa
        // Fetch all categories from database using JPA repository
// Internally, findAll() executes a SELECT * query

// Create a Pageable object using PageRequest
// PageRequest.of(pageNumber, pageSize) is a STATIC FACTORY METHOD
// pageNumber → which page to fetch (starts from 0)
// pageSize → number of records per page
        Pageable pageDetails = PageRequest.of(pageNumber, pageSize);


// Call repository method to fetch paginated result
// findAll(Pageable) returns a Page object (not a List)
// Page contains:
//  - actual data (content)
//  - total pages
//  - total elements
//  - current page number
        Page<Category> onePage = categoryRepository.findAll(pageDetails);


// Extract only the actual records (List<Category>) from Page object
// getContent() returns the current page's data
        List<Category> categories = onePage.getContent();
// Business validation:
// If no categories exist, we throw a custom exception
// instead of returning an empty list.
// This ensures client clearly understands that no data exists.
        if (categories.isEmpty()) {

            // Custom runtime exception used to handle business-level errors
            // This will be caught by GlobalExceptionHandler (@ControllerAdvice)
            throw new APIException("No category created till now.");
        }
// Step 3: Convert Entity objects to DTO objects
        // We should not expose Entity directly to client (Security + Clean Architecture)
        // Using ModelMapper to automatically map fields from Category -> CategoryDTO

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        // Step 4: Wrap DTO list inside Response object
        // Response wrapper is useful for adding pagination, metadata in future
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);

        categoryResponse.setPageNumber(onePage.getNumber());
        categoryResponse.setPageSize(onePage.getSize());
        categoryResponse.setTotalElements(onePage.getTotalElements());
        categoryResponse.setTotalpages(onePage.getTotalPages());
        categoryResponse.setLastPage(onePage.isLast());
        return categoryResponse;

    }

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
// category.setCategoryId(nextId++); we are removing nextId because we are generating automatically using
//  Jpa annotation Id, Generated
//        categories.add(category); removing add method of AL
//        due to usage of Jpa to save in DB
//        converting categorydto category using model mapper
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDB = categoryRepository.findByCategoryName(category.getCategoryName());
        if (categoryFromDB != null)
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists !!!");

        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
         
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
/*
        first check in DB all categories presnt or not
        n we use optimised one findById()
        List<Category> categories1 = categoryRepository.findAll();
*/
        Category deleteCategoryId = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        /*Before JPA we are using below to delete category by id need to check if id is present by matching use filter
         Category deleteCategoryId= categories1
                   .stream()
                   .filter(c -> c.getCategoryId().equals(categoryId))
                   .findFirst()        // returns Optional<Category>
                   .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found")); // converts Optional<Category> to category*/
        CategoryDTO deletingCategory = modelMapper.map(deleteCategoryId, CategoryDTO.class);
        categoryRepository.delete(deleteCategoryId);
//        return "Category with categoryId: " + categoryId + " deleted successfully !!";
        return deletingCategory;
    }

    @Override
    public CategoryDTO updateCategoryById(Long categoryId, CategoryDTO categoryDTO) {
//   before storing in DB we store data in AL so search chatgpt>springboot>stream filter> "How object is referring which came from stream in AL"
//        fetch all rows from DB
       /*
        first check in DB all categories presnt or not below
        n we use optimised one findById()
        List<Category> categories1 = categoryRepository.findAll();
*/
        Category categoryIdfind = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        /* Before using above JPA method we used below code
        Category categoryIdfind= categories1.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst().orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));*/
        categoryIdfind.setCategoryId(categoryId);
        categoryIdfind.setCategoryName(categoryDTO.getCategoryName());
        Category updatingCategory = categoryRepository.save(categoryIdfind);
        return modelMapper.map(updatingCategory, CategoryDTO.class);
    }
}
