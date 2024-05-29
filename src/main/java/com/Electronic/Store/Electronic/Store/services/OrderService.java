package com.Electronic.Store.Electronic.Store.services;

import com.Electronic.Store.Electronic.Store.dtos.OrderDto;
import com.Electronic.Store.Electronic.Store.dtos.PageableResponse;
import com.Electronic.Store.Electronic.Store.dtos.ProductDto;

import java.util.List;

public interface OrderService {

//create order

    OrderDto create(OrderDto orderDto,String userId,String cartId);
    //remove order
void removeOrder(String orderId);
//get order of user
    List<OrderDto> getOrderofUser(String userId);

//get all orders

    PageableResponse<OrderDto> getAllOrder(int pageNumber, int pageSize, String sortBy, String sortDir);






}
