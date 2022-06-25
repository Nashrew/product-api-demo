package com.nash.andrew.productapi.controller;

import com.nash.andrew.productapi.model.Product;
import com.nash.andrew.productapi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{product-id}")
    public Product getProduct(@PathVariable("product-id") Long productId) {
        return productService.getProduct(productId);
    }

    @PostMapping
    public Product bookProduct(@RequestParam String inventoryId) {
        return productService.bookProduct(Long.valueOf(inventoryId));
    }

}
