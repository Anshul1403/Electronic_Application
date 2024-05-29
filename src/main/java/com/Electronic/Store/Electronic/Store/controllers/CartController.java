package com.Electronic.Store.Electronic.Store.controllers;

import com.Electronic.Store.Electronic.Store.dtos.AddItemToCartRequest;
import com.Electronic.Store.Electronic.Store.dtos.ApiResponseMessage;
import com.Electronic.Store.Electronic.Store.dtos.CartDto;
import com.Electronic.Store.Electronic.Store.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemtocart(@RequestBody AddItemToCartRequest request, @PathVariable String userId){

        CartDto cartDto = cartService.addItemToCart(userId, request);

        return new ResponseEntity<>(cartDto, HttpStatus.OK);

    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponseMessage> deleteitemfromcart(
            @PathVariable String userId,
            @PathVariable int itemId
    ){
        cartService.removeItemfromCart(userId,itemId);

        ApiResponseMessage itemIsRemoved = ApiResponseMessage.builder()
                .message("Item is removed")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(itemIsRemoved, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> clearcart(
            @PathVariable String userId){
        cartService.clearcart(userId);

        ApiResponseMessage itemIsRemoved = ApiResponseMessage.builder()
                .message("Item is blank")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(itemIsRemoved, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(
            @PathVariable String userId
    ){
        CartDto cartByUser = cartService.getCartByUser(userId);
        return new ResponseEntity<>(cartByUser, HttpStatus.OK);
    }

}
