package com.backendproject.productapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backendproject.productapi.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
