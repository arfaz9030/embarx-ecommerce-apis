package com.arfaz.ecom.sbecom.controller;

import com.arfaz.ecom.sbecom.model.Category;
import com.arfaz.ecom.sbecom.service.CategoryService;
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

    //we can write as getmapping
    // @RequestMapping(value = "/public/categories", method = RequestMethod.GET)
    @GetMapping("/public/categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return  new ResponseEntity<> (categories, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<String> addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return new ResponseEntity<>("Category Successfully created",HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
            // Call service layer to delete category
            // Service returns a String message
            String resultMessage = categoryService.deleteCategory(categoryId);

            // Wrap the message inside ResponseEntity with HTTP 200 OK
            return new ResponseEntity<>(resultMessage, HttpStatus.OK);
          /*few more ways more return*/
/*            return ResponseEntity.ok(resultMessage);
            return ResponseEntity.status(HttpStatus.OK).body(resultMessage);*/
        }
        catch (ResponseStatusException e) {

            // If service throws ResponseStatusException
            // Extract error message (reason) and HTTP status from exception
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
    @PutMapping("/public/categories/{categoryId}")
public ResponseEntity<String> updateCategory(@PathVariable Long categoryId,
                                             @RequestBody Category category) {
        Category updateCategoryByIdcategory= categoryService.updateCategoryById(categoryId, category);
        return  new ResponseEntity<>("Category Successfully updated",HttpStatus.OK);

    }
}
