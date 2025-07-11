package com.backendproject.shoppingapi.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backendproject.shoppingapi.model.Shop;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long>, ReportRepository {
    List<Shop> findByUserIdentifier(String userIdentifier);

    List<Shop> findByTotalGreaterThan(Float total);

    List<Shop> findByDateGreaterThan(Date date);
}
