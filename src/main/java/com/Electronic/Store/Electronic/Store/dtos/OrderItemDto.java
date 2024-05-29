package com.Electronic.Store.Electronic.Store.dtos;

import com.Electronic.Store.Electronic.Store.entities.Order;
import com.Electronic.Store.Electronic.Store.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class OrderItemDto {


    private int orderItemid;

    private int quantity;

    private int totalPrice;

    private ProductDto product;


}
