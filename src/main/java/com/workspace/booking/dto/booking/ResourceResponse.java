package com.workspace.booking.dto.booking;

import java.math.BigDecimal;

public record ResourceResponse(
        Long id,
        String resourceName,
        String resourceType,
        String description,
        Integer quantityAvailable,
        BigDecimal pricePerUnit,
        String priceUnit,
        String currency
) {}
