package com.workspace.booking.dto.workspacetype;

import com.workspace.booking.common.enums.YesNo;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class WorkspaceTypeResponse {
    private Long id;
    private String typeCode;
    private String typeName;
    private String description;
    private String iconName;
    private YesNo isActive;

    private LocalDateTime createdOn;
    private String createdBy;
}
