package com.nash.andrew.productapi.service;

import com.nash.andrew.productapi.api.InventoryClient;
import com.nash.andrew.productapi.api.model.Inventory;
import com.nash.andrew.productapi.exception.RecordNotFoundException;
import com.nash.andrew.productapi.model.Product;
import com.nash.andrew.productapi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private InventoryClient inventoryClient;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElse(null);
    }

    public Product bookProduct(Long inventoryId) {
        Inventory inventory = inventoryClient.getInventory(inventoryId);

        if(inventory == null)
            throw new RecordNotFoundException("Inventory not found");

        Product newProduct = Product.builder()
                                .inventoryId(inventory.getId())
                                .content(inventory.getContent())
                                .experienceDetails(inventory.getExperienceDetails())
                                .build();
        return productRepository.save(newProduct);
    }
}
