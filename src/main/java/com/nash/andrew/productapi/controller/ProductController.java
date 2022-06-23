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

    // TODO - Change this to POST "/" and make a Product the @RequestBody, can pull the inventoryId from that (and maybe make it required). Dropping the "/book/" pathing will help the API signature make more sense. 
    @PostMapping("/book/{inventory-id}")
    public Product bookProduct(@PathVariable("inventory-id") Long inventoryId) {
        return productService.bookProduct(inventoryId);
    }

}
