package com.Electronic.Store.Electronic.Store.repositories;

import com.Electronic.Store.Electronic.Store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findByTitleContaining(String subtitle,Pageable pageable);

    Page<Product> FindByLiveTrue(Pageable pageable);
}
