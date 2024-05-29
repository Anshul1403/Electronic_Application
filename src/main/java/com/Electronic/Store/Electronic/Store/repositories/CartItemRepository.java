package com.Electronic.Store.Electronic.Store.repositories;

import com.Electronic.Store.Electronic.Store.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
}
