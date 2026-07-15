package com.workspace.booking.entity.workspace;

import com.workspace.booking.common.enums.*;
import com.workspace.booking.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "WS_WORKSPACE_TYPES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkspaceType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String typeCode;

    @Column(name = "TYPE_NAME", nullable = false)
    private String typeName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ICON_NAME")
    private String iconName;

    @Enumerated(EnumType.STRING)
    private YesNo isActive;
}
