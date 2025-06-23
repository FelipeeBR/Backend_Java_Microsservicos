package com.backendproject.productapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendproject.productapi.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> getProductByCategoryId(long categoryId);

    public Product findByProductIdentifier(String productIdentifier);
}
