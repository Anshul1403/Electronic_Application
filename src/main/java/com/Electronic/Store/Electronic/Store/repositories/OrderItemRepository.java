package com.Electronic.Store.Electronic.Store.repositories;

import com.Electronic.Store.Electronic.Store.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Integer> {
}
