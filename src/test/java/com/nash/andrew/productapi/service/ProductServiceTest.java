package com.nash.andrew.productapi.service;

import com.nash.andrew.productapi.api.InventoryClient;
import com.nash.andrew.productapi.api.model.Inventory;
import com.nash.andrew.productapi.exception.RecordNotFoundException;
import com.nash.andrew.productapi.model.Product;
import com.nash.andrew.productapi.repository.ProductRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
@ContextConfiguration(classes = {ProductService.class})
@Tag("UnitTest")
class ProductServiceTests {

    @MockBean
    private ProductRepository productRepo;

    @MockBean
    private InventoryClient inventoryClient;

    @Autowired
    private ProductService productService;

    // look into mention of reactive programming in requirements
    // begin documentation after that

    private final long INVENTORY_ID = 42L;
    private final String CONTENT = "One Admission to the Lunar Rovers";
    private final String EXPERIENCE_DETAILS = "We're whalers on the moon";

    private final Inventory TEST_INVENTORY = Inventory.builder()
            .id(INVENTORY_ID)
            .content(CONTENT)
            .experienceDetails(EXPERIENCE_DETAILS)
            .build();

    private final Product TEST_PRODUCT = Product.builder()
            .content(CONTENT)
            .experienceDetails(EXPERIENCE_DETAILS)
            .inventoryId(INVENTORY_ID)
            .build();

    @Test
    void givenInventoryId_whenInventoryFound_thenBookProduct() throws IOException {

        givenCondition(true);

        Product result = productService.bookProduct(INVENTORY_ID);

        assertEquals(INVENTORY_ID, result.getInventoryId());
        assertEquals(CONTENT, result.getContent());
        assertEquals(EXPERIENCE_DETAILS, result.getExperienceDetails());
    }

    @Test
    void givenInventoryId_whenInventoryNotFound_thenFailure() throws IOException {

        givenCondition(false);

        assertThrows(RecordNotFoundException.class, () -> {
            Product result = productService.bookProduct(INVENTORY_ID);
        });
    }

    private void givenCondition(boolean inventoryFound) throws IOException {

        when(productRepo.save(any(Product.class))).thenAnswer(i -> {
            Product product = i.getArgument(0);
            return product;
        });

        when(inventoryClient.getInventory(anyLong())).thenReturn(inventoryFound ? TEST_INVENTORY : null);
    }
}
