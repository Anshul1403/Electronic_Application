package com.Electronic.Store.Electronic.Store.controllers;

import com.Electronic.Store.Electronic.Store.dtos.*;
import com.Electronic.Store.Electronic.Store.services.FileService;
import com.Electronic.Store.Electronic.Store.services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private FileService fileService;
    @Value("${product.image.path}")
    private String imagePath;


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


    //upload Image
@PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse> uploadProductImage(@PathVariable String productId,
                                                            @RequestParam("productImage")MultipartFile image) throws IOException {
    String filename = fileService.uploadFile(image, imagePath);
    ProductDto productDto = productService.get(productId);
    productDto.setProductImageName(filename);

    ProductDto updated = productService.update(productDto, productId);

    ImageResponse productImageIsSuccessfullyUploaded = ImageResponse.builder().imageName(updated.getProductImageName()).message("Product Image is successfully uploaded").status(HttpStatus.CREATED).build();

    return new ResponseEntity<>(productImageIsSuccessfullyUploaded,HttpStatus.CREATED);
}


    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable("productId") String productId, HttpServletResponse response) throws IOException {

        ProductDto productDto = productService.get(productId);

        InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource,response.getOutputStream());


    }




}
