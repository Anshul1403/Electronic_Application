package com.Electronic.Store.Electronic.Store.services;

import com.Electronic.Store.Electronic.Store.dtos.AddItemToCartRequest;
import com.Electronic.Store.Electronic.Store.dtos.CartDto;

public interface CartService {

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    // remove item
    void removeItemfromCart(String userId, int cartItem);

    void clearcart(String userId);

    CartDto getCartByUser(String userId);
}
