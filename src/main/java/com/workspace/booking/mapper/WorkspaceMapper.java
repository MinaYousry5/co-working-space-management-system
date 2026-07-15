package com.workspace.booking.mapper;

import com.workspace.booking.dto.Workspace.WorkspaceResponse;
import com.workspace.booking.entity.workspace.Workspace;
import org.springframework.stereotype.Component;

@Component
public class WorkspaceMapper {

    public WorkspaceResponse toResponse(Workspace entity) {
        return new WorkspaceResponse(
                entity.getId(),
                entity.getWorkspaceName(),
                entity.getWorkspaceCode(),
                entity.getDescription(),
                entity.getCapacity(),
                entity.getPriceHourly(),
                entity.getWorkspaceType() != null ? entity.getWorkspaceType().getTypeName() : null,
                entity.getFloorNumber(),
                entity.getRoomNumber(),
                entity.getIsAvailable(),
                entity.getIsFeatured()
        );
    }
}