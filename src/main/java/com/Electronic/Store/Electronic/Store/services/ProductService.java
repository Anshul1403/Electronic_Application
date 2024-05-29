package com.Electronic.Store.Electronic.Store.services;

import com.Electronic.Store.Electronic.Store.dtos.PageableResponse;
import com.Electronic.Store.Electronic.Store.dtos.ProductDto;

import java.util.List;

public interface ProductService {

    //create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto,String productId);
    //delete

  void delete(String productId);

    ProductDto get(String productId);

    PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir);




    //getAll :live
   // PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir);

    //Search Product
    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir);

    //create product with category


    ProductDto createWithCategory(ProductDto productDto,String categoryId);

    ProductDto assignProductToCategory(String productId, String categoryId);

    PageableResponse<ProductDto> getProductWithCategory(String categoryId,int pageNumber, int pageSize, String sortBy,String sortDir);

}
