package com.Electronic.Store.Electronic.Store.dtos;

import com.Electronic.Store.Electronic.Store.entities.Category;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder

@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String productId;
    private String title;

    private String description;
    private int price;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;
    private CategoryDto category;
}
