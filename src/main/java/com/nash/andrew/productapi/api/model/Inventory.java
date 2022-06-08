package com.nash.andrew.productapi.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Inventory {
    private Long id;
    private String content; // in the real world this is probably something other than a string, just keeping it simple here
    private String experienceDetails;
}
