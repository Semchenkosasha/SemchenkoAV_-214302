package com.ordermanagement.repo;

import com.ordermanagement.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductsRepo extends JpaRepository<Products, Long> {
    List<Products> findAllByNameContainingAndCategory_Id(String name, Long categoryId);
}
