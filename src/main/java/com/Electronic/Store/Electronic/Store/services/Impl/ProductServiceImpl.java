package com.Electronic.Store.Electronic.Store.services.Impl;

import com.Electronic.Store.Electronic.Store.dtos.CategoryDto;
import com.Electronic.Store.Electronic.Store.dtos.PageableResponse;
import com.Electronic.Store.Electronic.Store.dtos.ProductDto;
import com.Electronic.Store.Electronic.Store.dtos.UserDto;
import com.Electronic.Store.Electronic.Store.entities.Category;
import com.Electronic.Store.Electronic.Store.entities.Product;
import com.Electronic.Store.Electronic.Store.entities.User;
import com.Electronic.Store.Electronic.Store.exceptions.ResourceNotFoundException;
import com.Electronic.Store.Electronic.Store.repositories.ProductRepository;
import com.Electronic.Store.Electronic.Store.services.ProductService;
import org.hibernate.boot.model.source.internal.hbm.Helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;


    @Override
    public ProductDto create(ProductDto productDto) {


        String productId = UUID.randomUUID().toString();
        productDto.setProductId(productId);

        Product product = mapper.map(productDto, Product.class);

        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct,ProductDto.class);

    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        product.setProductId(productDto.getProductId());
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setAddedDate(productDto.getAddedDate());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());

        Product saved = productRepository.save(product);
        return mapper.map(saved,ProductDto.class);


    }

    @Override
    public void delete(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

        productRepository.delete(product);

    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        List<Product> products = page.getContent();
        List<ProductDto> dtolist = products.stream().map((product) -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
         PageableResponse<ProductDto> response  = new PageableResponse<>();
        response.setContent(dtolist);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastpage(page.isLast());


        return response;
    }

//    @Override
//    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String sortDir) {
//        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
//
//        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
//        Page<Product> page = productRepository.FindByLiveTrue(pageable);
//        List<Product> products = page.getContent();
//        List<ProductDto> dtolist = products.stream().map((product) -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
//        PageableResponse<ProductDto> response  = new PageableResponse<>();
//        response.setContent(dtolist);
//        response.setPageNumber(page.getNumber());
//        response.setPageSize(page.getSize());
//        response.setTotalElements(page.getTotalElements());
//        response.setTotalPages(page.getTotalPages());
//        response.setLastpage(page.isLast());
//
//
//        return response;
//    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle,pageable);
        List<Product> products = page.getContent();
        List<ProductDto> dtolist = products.stream().map((product) -> mapper.map(product, ProductDto.class)).collect(Collectors.toList());
        PageableResponse<ProductDto> response  = new PageableResponse<>();
        response.setContent(dtolist);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastpage(page.isLast());


        return response;
    }


    @Override
    public ProductDto get(String productId) {

        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product Not Found"));

return mapper.map(product,ProductDto.class);


    }


}
