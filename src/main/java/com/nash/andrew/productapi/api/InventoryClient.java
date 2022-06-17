package com.nash.andrew.productapi.api;

import com.nash.andrew.productapi.api.model.Inventory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Component
public class InventoryClient {

    private String inventoryUrl;

    public InventoryClient(@Value("${app.inventoryUrl}") String url) {
        inventoryUrl = url;
    }

    public Inventory getInventory(Long id) {
        WebClient webClient = WebClient.create(inventoryUrl);
        return webClient.get()
                .uri("/api/inventories/" + id)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError, response -> (Mono.empty()))
                .bodyToMono(Inventory.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(3)))
                .block();
    }
}
