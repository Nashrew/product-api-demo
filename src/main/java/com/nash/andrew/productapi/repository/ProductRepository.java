package com.nash.andrew.productapi.repository;

import com.nash.andrew.productapi.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    public List<Product> findAll();
    public List<Product> findAllByInventoryId(Long inventoryId);
}