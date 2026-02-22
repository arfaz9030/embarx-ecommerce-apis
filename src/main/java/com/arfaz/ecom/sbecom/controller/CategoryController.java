package com.arfaz.ecom.sbecom.controller;

import com.arfaz.ecom.sbecom.model.Category;
import com.arfaz.ecom.sbecom.payloads.CategoryDTO;
import com.arfaz.ecom.sbecom.payloads.CategoryResponse;
import com.arfaz.ecom.sbecom.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
//If I have Observe the below endpoints previously /api is common for endpoints so I can use
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/echo")
    public ResponseEntity<String> test(

            // Reads query parameter from URL
            // Example: /test?message=Arfaz
            // "message" is the parameter name expected in the URL
            // required = false → parameter is optional
            // If not passed, 'name' will be null
            @RequestParam(name = "message", required = false) String name) {

        // Returns HTTP response with:
        // 1. Body → "My name is <value>"
        // 2. HTTP Status → 200 OK
        return new ResponseEntity<>("My name is " + name, HttpStatus.OK);
    }

    //we can write as getmapping
    // @RequestMapping(value = "/public/categories", method = RequestMethod.GET)
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(@RequestParam(name = "pageNumber") Integer pageNumber,
                                                             @RequestParam(name = "pageSize") Integer pageSize) {
        CategoryResponse categories = categoryService.getAllCategories(pageNumber, pageSize);
            return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    /* * It tells Spring Boot to check all validation annotations
     * present inside the POJO class (like @NotBlank, @NotNull, etc.)
     *  Difference:
     *  @Valid works at object level (to activate validation throw user friendly message compare to NotBlank),
     *  while @NotBlank works at field level*/
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.addCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
//        return new ResponseEntity<>("Category Successfully created",HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {

        // Call service layer to delete category
        // Service returns a String message
        CategoryDTO resultMessage = categoryService.deleteCategory(categoryId);

        // Wrap the message inside ResponseEntity with HTTP 200 OK
        return new ResponseEntity<CategoryDTO>(resultMessage, HttpStatus.OK);
        /*few more ways more return*/
/*            return ResponseEntity.ok(resultMessage);
            return ResponseEntity.status(HttpStatus.OK).body(resultMessage);*/


    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,
                                                      @RequestBody @Valid CategoryDTO categoryDTO) {
//Instead of Category (entity class) using CategoryDTO due to hiding private data like passwords and fields modifications are easy
        CategoryDTO updateCategoryByIdcategory = categoryService.updateCategoryById(categoryId, categoryDTO);
        return new ResponseEntity<>(updateCategoryByIdcategory, HttpStatus.OK);

    }
}
