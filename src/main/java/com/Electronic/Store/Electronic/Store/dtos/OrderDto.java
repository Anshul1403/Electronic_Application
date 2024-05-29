package com.Electronic.Store.Electronic.Store.dtos;

import com.Electronic.Store.Electronic.Store.entities.OrderItem;
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
@ToString
public class OrderDto {



    private String orderId;

    private String orderStatus="PENDING";

    private String paymentStatus="NOTPAID";

    private int orderAmount;

    private String billingAddress;


    private String billingphone;
    private String billingName;

    private Date orderedDate=new Date();
    private Date deliveredDate;




     private List<OrderItemDto> orderItem = new ArrayList<>();

}
