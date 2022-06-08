package com.nash.andrew.productapi.model;

import com.nash.andrew.productapi.api.model.Inventory;
import lombok.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    private Long id;

    private String content;

    private String experienceDetails;

    private Long inventoryId;

    public Product(Inventory inventory) {
        this.content = inventory.getContent();
        this.experienceDetails = inventory.getExperienceDetails();
        this.inventoryId = inventory.getId();
    }
}
