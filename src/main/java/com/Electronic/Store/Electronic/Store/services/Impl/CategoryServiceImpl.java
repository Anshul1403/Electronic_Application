package com.Electronic.Store.Electronic.Store.services.Impl;

import com.Electronic.Store.Electronic.Store.dtos.CategoryDto;
import com.Electronic.Store.Electronic.Store.dtos.PageableResponse;
import com.Electronic.Store.Electronic.Store.dtos.UserDto;
import com.Electronic.Store.Electronic.Store.entities.Category;
import com.Electronic.Store.Electronic.Store.entities.User;
import com.Electronic.Store.Electronic.Store.exceptions.ResourceNotFoundException;
import com.Electronic.Store.Electronic.Store.repositories.CategoryRepository;
import com.Electronic.Store.Electronic.Store.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);

        Category category = mapper.map(categoryDto, Category.class);

        Category savedCategory = categoryRepository.save(category);

        return mapper.map(savedCategory,CategoryDto.class);


    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category Not Found"));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category updatedCategory = categoryRepository.save(category);
        return mapper.map(updatedCategory,CategoryDto.class);

    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber,int pageSize,String sortBy,String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<Category> page = categoryRepository.findAll(pageable);
        List<Category> categories = page.getContent();
        List<CategoryDto> dtolist = categories.stream().map((category) -> mapper.map(category, CategoryDto.class)).collect(Collectors.toList());

        PageableResponse<CategoryDto> response  = new PageableResponse<>();
        response.setContent(dtolist);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastpage(page.isLast());


        return response;


    }

    @Override
    public void delete(String categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category Not Found"));
        categoryRepository.delete(category);

    }

    @Override
    public CategoryDto getCategorydetailsByID(String categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category Not Found"));

        return mapper.map(category,CategoryDto.class);
    }
}
