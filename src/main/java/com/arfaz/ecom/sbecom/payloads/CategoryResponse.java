package com.arfaz.ecom.sbecom.payloads;

import lombok.Data;

import java.util.List;

@Data
public class CategoryResponse {
    List<CategoryDTO>  content;
    Integer pageNumber;
    Integer pageSize;
    private Long totalElements;
    private Integer totalpages;
    private boolean lastPage;
}
