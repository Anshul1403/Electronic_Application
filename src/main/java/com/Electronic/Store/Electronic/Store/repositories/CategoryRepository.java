package com.Electronic.Store.Electronic.Store.repositories;

import com.Electronic.Store.Electronic.Store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
}
