package com.workspace.booking.entity.booking;

import com.workspace.booking.common.enums.*;
import com.workspace.booking.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "WS_RESOURCE_TYPES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ResourceType extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ResourceTypeCode typeCode;

    private String typeName;
    private String description;
}
