package com.workspace.booking.dto.Workspace;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record WorkspaceSearchRequest(
        @NotNull @Future LocalDateTime startDatetime,
        @NotNull @Future LocalDateTime endDatetime,
        @Min(1) Integer minCapacity,
        Long typeId,
        Long locationId,
        int page,
        int size
) {
    public WorkspaceSearchRequest {
        if (page < 0) page = 0;
        if (size <= 0) size = 10;
    }
}
