package com.backendproject.productapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backendproject.productapi.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
