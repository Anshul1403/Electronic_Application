package com.Electronic.Store.Electronic.Store.services.Impl;

import com.Electronic.Store.Electronic.Store.dtos.OrderDto;
import com.Electronic.Store.Electronic.Store.dtos.PageableResponse;
import com.Electronic.Store.Electronic.Store.entities.*;
import com.Electronic.Store.Electronic.Store.exceptions.BadApiRequest;
import com.Electronic.Store.Electronic.Store.exceptions.ResourceNotFoundException;
import com.Electronic.Store.Electronic.Store.repositories.CartRepository;
import com.Electronic.Store.Electronic.Store.repositories.CategoryRepository;
import com.Electronic.Store.Electronic.Store.repositories.OrderRepository;
import com.Electronic.Store.Electronic.Store.repositories.UserRepository;
import com.Electronic.Store.Electronic.Store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public OrderDto create(OrderDto orderDto, String userId,String cartId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Cart is not found with given id"));
        List<CartItem> cartitems = cart.getItems();

        if(cartitems.size()==0){
          throw new BadApiRequest("invalid number of items in cart");
        }

        Order order = Order.builder()
                .billingAddress(orderDto.getBillingAddress())
                .billingphone(orderDto.getBillingphone())
                .billingAddress(orderDto.getBillingAddress())
                .orderedDate(new Date())
                .deliveredDate(orderDto.getDeliveredDate())
                .paymentStatus(orderDto.getPaymentStatus())
                .orderStatus(orderDto.getOrderStatus())
                .orderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        AtomicReference<Integer> orderAmount = new AtomicReference<>();
        List<OrderItem> orderItems = cartitems.stream().map(cartItem -> {
         //   CartItem -> OrderItem
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getPrice())
                    .order(order)
                    .build();
            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItem(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        cartRepository.save(cart);
        Order savedOrder = orderRepository.save(order);

        return mapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

    }

    @Override
    public List<OrderDto> getOrderofUser(String userId) {
        return List.of();
    }

    @Override
    public PageableResponse<OrderDto> getAllOrder(int pageNumber, int pageSize, String sortBy, String sortDir) {
        return null;
    }
}
