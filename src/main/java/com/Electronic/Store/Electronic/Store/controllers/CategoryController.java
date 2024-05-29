package com.Electronic.Store.Electronic.Store.controllers;

import com.Electronic.Store.Electronic.Store.dtos.ApiResponseMessage;
import com.Electronic.Store.Electronic.Store.dtos.CategoryDto;
import com.Electronic.Store.Electronic.Store.dtos.PageableResponse;
import com.Electronic.Store.Electronic.Store.dtos.ProductDto;
import com.Electronic.Store.Electronic.Store.services.CategoryService;
import com.Electronic.Store.Electronic.Store.services.ProductService;
import jakarta.servlet.http.PushBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;


    //create
    @PostMapping()
    public ResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = categoryService.create(categoryDto);

        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }


    //update
    @PutMapping("/{categoryId}")
   public ResponseEntity<CategoryDto> update(@RequestBody CategoryDto categoryDto, @PathVariable String categoryId){
        CategoryDto update = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(update, HttpStatus.OK);
    }


    //delete
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> delete( @PathVariable String categoryId){
        categoryService.delete(categoryId);

        ApiResponseMessage re = ApiResponseMessage.builder().message("Category is deleted successfully").success(true).status(HttpStatus.OK).build();

        return new ResponseEntity<>(re,HttpStatus.OK);
    }

    //getAll
   @GetMapping()
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "0",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){
        PageableResponse<CategoryDto> all = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(all,HttpStatus.OK);


    }

    //getSingle
@GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId){

    CategoryDto categorydetailsByID = categoryService.getCategorydetailsByID(categoryId);
    return new ResponseEntity<>(categorydetailsByID,HttpStatus.OK);
}

    //create with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductWithCategory(@RequestBody ProductDto productDto, @PathVariable String categoryId){
        ProductDto withCategory = productService.createWithCategory(productDto, categoryId);

        return  new ResponseEntity<>(withCategory, HttpStatus.CREATED);
    }
    //create with category
    @PutMapping("/{categoryId}/products/{productId}")
    public ResponseEntity<ProductDto> updateProductWithCategory(@PathVariable String productId, @PathVariable String categoryId){
        ProductDto productDto = productService.assignProductToCategory(productId, categoryId);

        return  new ResponseEntity<>(productDto, HttpStatus.CREATED);
    }


    @GetMapping("/{categoryId}/products")
    public ResponseEntity<PageableResponse<ProductDto>> getProductWithCategory(
            @PathVariable String categoryId,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "0",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir
    ){

        PageableResponse<ProductDto> productWithCategory = productService.getProductWithCategory(categoryId,pageNumber, pageSize, sortBy, sortDir);
         return new ResponseEntity<>(productWithCategory,HttpStatus.OK);

    }


}
