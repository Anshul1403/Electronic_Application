package com.Electronic.Store.Electronic.Store.services;

import com.Electronic.Store.Electronic.Store.dtos.CategoryDto;
import com.Electronic.Store.Electronic.Store.dtos.PageableResponse;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto,String categoryId);

    //getAll
    PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);
    //delete
    void delete(String categoryId);
    //get Single Category details
    CategoryDto  getCategorydetailsByID(String categoryId);


    //search
}
