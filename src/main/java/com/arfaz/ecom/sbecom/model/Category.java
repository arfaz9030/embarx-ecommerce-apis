package com.arfaz.ecom.sbecom.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity(name ="categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
//not blank will throw 500 error if I provide "" value which is blank
    @NotBlank
    @Size(min = 5, message = " enter atleast 5 characters")
    // here we get default message as category must min 5 if we enter one char so we'll customize
    private String categoryName;

    //if we want to test we comment below getter, setter then jpa will give null value to the respective fields
    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
