package com.nash.andrew.productapi.repository;

import com.nash.andrew.productapi.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Basic repo for the no DB requirement, I believe using a database with JPA or some other ORM would make this app much cleaner
@Component
public class ProductRepository {
    List<Product> products = new ArrayList<Product>();

    {
        products.add(Product.builder()
                .id(1L)
                .content("One Admission to the Lunar Rovers")
                .experienceDetails("We're whalers on the moon")
                .inventoryId(1L)
                .build());

        products.add(Product.builder()
                .id(2L)
                .content("One Admission to Space Mountain")
                .experienceDetails("(Not as good as the Lunar Rover ride)")
                .inventoryId(2L)
                .build());
    }

    public synchronized Product save(Product product) {
        // simple autoIncrement ... but wouldn't behave the same as a database when certain elements are removed (only, last)
        Long idValue = products.isEmpty() ? 0 : products.get(products.size() - 1).getId() + 1;
        product.setId(idValue);
        products.add(product);
        return product;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Product getProductById(Long productId) {
        return products.stream()
                .filter(product -> Objects.equals(product.getId(), productId))
                .findFirst()
                .orElse(null);
    }

    // not implementing delete because no requirement was given and it would break our dinky just-a-list 'database'
}
