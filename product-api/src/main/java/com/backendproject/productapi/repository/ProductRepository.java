package com.backendproject.productapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.backendproject.productapi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT p" + "FROM product p" + "JOIN category c on p.category.id = c.id" + "where c.id = :categoryId")
    public List<Product> getProductByCategory(@Param("categoryId") Long categoryId);

    public Product findByProductIdentifier(String productIdentifier);
}
