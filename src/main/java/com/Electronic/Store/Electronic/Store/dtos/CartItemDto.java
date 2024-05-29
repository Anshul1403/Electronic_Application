package com.Electronic.Store.Electronic.Store.dtos;

import com.Electronic.Store.Electronic.Store.entities.Cart;
import com.Electronic.Store.Electronic.Store.entities.CartItem;
import com.Electronic.Store.Electronic.Store.entities.Product;
import com.Electronic.Store.Electronic.Store.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {


    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalprice;


}
