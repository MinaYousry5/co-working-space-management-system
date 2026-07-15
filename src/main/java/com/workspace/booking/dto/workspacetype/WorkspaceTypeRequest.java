package com.workspace.booking.dto.workspacetype;

import com.workspace.booking.common.enums.YesNo;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkspaceTypeRequest {
    @NotBlank(message = "Type code is required")
    private String typeCode;

    @NotBlank(message = "Type name is required")
    private String typeName;

    private String description;
    private String iconName;
    private YesNo isActive;
}
