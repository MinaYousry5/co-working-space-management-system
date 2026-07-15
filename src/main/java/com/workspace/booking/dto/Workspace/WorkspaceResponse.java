package com.workspace.booking.dto.Workspace;

import com.workspace.booking.common.enums.YesNo;

import java.math.BigDecimal;
import java.util.List;

public record WorkspaceResponse(
        Long id,
        String workspaceName,
        String workspaceCode,
        String description,
        Integer capacity,
        BigDecimal priceHourly,
        String typeName,
        Integer floorNumber,
        String roomNumber,
        YesNo isAvailable,
        YesNo isFeatured
) {}