package com.nash.andrew.productapi.model;

import com.nash.andrew.productapi.api.model.Inventory;
import lombok.*;

import javax.persistence.*;

@Data @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Builder @Entity @Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(name = "experience_details", nullable = false)
    private String experienceDetails;

    @Column(name = "inventory_id", nullable = false)
    private Long inventoryId;

    public Product(Inventory inventory) {
        this.content = inventory.getContent();
        this.experienceDetails = inventory.getExperienceDetails();
        this.inventoryId = inventory.getId();
    }
}
