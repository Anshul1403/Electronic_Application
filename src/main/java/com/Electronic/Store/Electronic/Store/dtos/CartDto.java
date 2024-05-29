package com.Electronic.Store.Electronic.Store.dtos;

import com.Electronic.Store.Electronic.Store.entities.CartItem;
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
public class CartDto {

    private Date createdAt;
    private String cartId;
    private UserDto user;
    private List<CartItemDto> items  = new ArrayList<>();

}
