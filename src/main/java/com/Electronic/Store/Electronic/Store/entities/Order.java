package com.Electronic.Store.Electronic.Store.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    private String orderId;
    //PENDING ,DELIVERED ,DISPATCHED
    //we CAN USE ENUM
    private String orderStatus;
    //PAID,NOTPAID
    private String paymentStatus;

    private int orderAmount;
   @Column(length = 1000)
    private String billingAddress;


    private String billingphone;
    private String billingName;

    private Date orderedDate;
    private Date deliveredDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

   @OneToMany(mappedBy = "order",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItem> orderItem = new ArrayList<>();


}
