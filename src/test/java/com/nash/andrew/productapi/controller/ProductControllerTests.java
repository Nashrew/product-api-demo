package com.nash.andrew.productapi.controller;

import com.nash.andrew.productapi.exception.RecordNotFoundException;
import com.nash.andrew.productapi.model.Product;
import com.nash.andrew.productapi.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {ProductController.class})
public class ProductControllerTests {

    private static final Long DEFAULT_INVENTORY_ID = 42L;
    private static final Long SECONDARY_INVENTORY_ID = 43L;
    private static final Long INVALID_INVENTORY_ID = 13L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductService productService;
    @Test
    void givenProductsRequest_retrieveProducts_thenSuccess() throws Exception {
        when(productService.getProducts())
                .thenReturn(List.of(
                        Product.builder()
                                .id(1L)
                                .content("One Admission to the Lunar Rovers")
                                .experienceDetails("We're whalers on the moon")
                                .inventoryId(DEFAULT_INVENTORY_ID)
                                .build(),
                        Product.builder()
                                .id(2L)
                                .content("One Admission to Space Mountain")
                                .experienceDetails("(Not as good as the Lunar Rover ride)")
                                .inventoryId(SECONDARY_INVENTORY_ID)
                                .build()));

        mockMvc.perform(
                        get("/products")
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenSingleProductRequest_retrieveProduct_thenSuccess() throws Exception {
        when(productService.getProduct(any(Long.class)))
                .thenReturn(Product.builder()
                                .id(1L)
                                .content("One Admission to the Lunar Rovers")
                                .experienceDetails("We're whalers on the moon")
                                .inventoryId(DEFAULT_INVENTORY_ID)
                                .build());

        mockMvc.perform(
                        get("/products/" + DEFAULT_INVENTORY_ID)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenValidInventory_bookProductWithInventory_thenSuccess() throws Exception {
        when(productService.bookProduct(any(Long.class)))
                .thenAnswer(i -> {
                    Long inventoryId = i.getArgument(0);
                    return Product.builder()
                            .id(1L)
                            .content("One Admission to the Lunar Rovers")
                            .experienceDetails("We're whalers on the moon")
                            .inventoryId(inventoryId)
                            .build();
                });

        mockMvc.perform(
                        post("/products/book/" + DEFAULT_INVENTORY_ID)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void givenInvalidInventory_bookProductWithInventory_thenFail() throws Exception {
        when(productService.bookProduct(any(Long.class)))
                .thenAnswer(i -> {
                    throw new RecordNotFoundException("Inventory not found");
                });

        mockMvc.perform(
                        post("/products/book/" + INVALID_INVENTORY_ID)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }
}
