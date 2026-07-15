package com.workspace.booking.entity.feature;

import com.workspace.booking.entity.BaseEntity;
import com.workspace.booking.entity.workspace.Workspace;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "WS_WORKSPACE_AMENITIES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkspaceAmenity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "WORKSPACE_ID")
    private Workspace workspace;

    @ManyToOne @JoinColumn(name = "AMENITY_ID")
    private Amenity amenity;

    private String notes;
}