package com.nash.andrew.productapi.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.nash.andrew.productapi.api.model.Inventory;
import org.apache.hc.core5.http.HttpHeaders;
import org.eclipse.jetty.http.HttpStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringJUnitConfig
@Tag("UnitTest")
public class InventoryClientTest {

    public static WireMockServer wireMockRule = new WireMockServer(options().dynamicPort());

    static final Long DEFAULT_INVENTORY_ID = 42L;
    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private InventoryClient inventoryClient;

    @TestConfiguration
    static class WebClientTestConfiguration {
        @Bean
        InventoryClient customerSrvClient(@Value("${app.inventoryUrl}") String url) {
            return new InventoryClient(url);
        }
    }

    @BeforeAll
    public static void beforeAll() {
        wireMockRule.start();
    }

    @AfterAll
    public static void afterAll() {
        wireMockRule.stop();
    }

    @AfterEach
    public void afterEach() {
        wireMockRule.resetAll();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("app.inventoryUrl", wireMockRule::baseUrl);
    }

    @Test
    void givenInventoryExists_whenRetrieveInventory_thenSuccess() throws IOException {

        wireMockRule.stubFor(get(urlEqualTo("/api/inventories/" + DEFAULT_INVENTORY_ID))
                .willReturn(aResponse()
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody(mapper.writeValueAsString(
                                        Inventory.builder()
                                                .id(DEFAULT_INVENTORY_ID)
                                                .content("One admission to space mountain")
                                                .experienceDetails("Fun roller coaster in the dark!")
                                                .build()
                                )
                        ))
        );

        Inventory inventory = inventoryClient.getInventory(DEFAULT_INVENTORY_ID);

        wireMockRule.verify(
                getRequestedFor(urlEqualTo("/api/inventories/" + DEFAULT_INVENTORY_ID))
        );
        assertThat(inventory, is(notNullValue()));
        assertThat(inventory.getId(), is(DEFAULT_INVENTORY_ID));
    }

    @Test
    void givenInventoryNotExists_whenRetrieveInventory_thenFail() throws IOException {

        wireMockRule.stubFor(get(urlEqualTo("/api/inventories/" + DEFAULT_INVENTORY_ID))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.NOT_FOUND_404))
        );

        Inventory inventory = inventoryClient.getInventory(DEFAULT_INVENTORY_ID);

        wireMockRule.verify(
                getRequestedFor(urlEqualTo("/api/inventories/" + DEFAULT_INVENTORY_ID))
        );
        assertThat(inventory, is(nullValue()));
    }
}
