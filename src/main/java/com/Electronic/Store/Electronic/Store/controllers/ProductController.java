package com.Electronic.Store.Electronic.Store.controllers;

import com.Electronic.Store.Electronic.Store.dtos.ApiResponseMessage;
import com.Electronic.Store.Electronic.Store.dtos.PageableResponse;
import com.Electronic.Store.Electronic.Store.dtos.ProductDto;
import com.Electronic.Store.Electronic.Store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;


    //create
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto){
        ProductDto createdProduct = productService.create(productDto);

        return  new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }


    //update

    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable String productId ,@RequestBody ProductDto productDto){
        ProductDto updatedproduct = productService.update(productDto,productId);

        return  new ResponseEntity<>(updatedproduct, HttpStatus.CREATED);
    }


    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable("productId") String productId){

        productService.delete(productId);
        ApiResponseMessage errMessage  = ApiResponseMessage.builder().message("Product has been deleted").success(true).build();
        return new ResponseEntity<>(errMessage, HttpStatus.OK);
    }



    //get Single
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> GetProduct(@PathVariable String productId){
        ProductDto productDto = productService.get(productId);

        return  new ResponseEntity<>(productDto, HttpStatus.OK);
    }



    //Get All

    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>> GetAllProduct(
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "0",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {

        PageableResponse<ProductDto> all = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(all, HttpStatus.OK);
    }



//    @GetMapping("/live")
//    public ResponseEntity<PageableResponse<ProductDto>> GetAllProductLive(
//            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
//            @RequestParam(value = "pageSize",defaultValue = "0",required = false) int pageSize,
//            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
//            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
//    {
//
//        PageableResponse<ProductDto> allLive = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
//        return  new ResponseEntity<>(allLive, HttpStatus.OK);
//    }

    //search
    @GetMapping("/search/{query}")
    public ResponseEntity<PageableResponse<ProductDto>> GetAllProductTitle(
            @PathVariable String query,
            @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "0",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir)
    {
        PageableResponse<ProductDto> productDtoPageableResponse = productService.searchByTitle(query, pageNumber, pageSize, sortBy, sortDir);
        return  new ResponseEntity<>(productDtoPageableResponse, HttpStatus.OK);
    }

}
