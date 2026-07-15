package com.workspace.booking.dto.Workspace;

import java.math.BigDecimal;
import java.util.List;

public record WorkspaceAvailabilityResponse(
    Long id,
    String name,
    String typeName,
    Integer capacity,
    BigDecimal pricePerHour,
    String imageUrl,
    List<String> amenityNames,
    boolean available
) {}

