package com.workspace.booking.entity.feature;

import com.workspace.booking.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "WS_AMENITIES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Amenity extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String amenityCode;
    private String amenityName;
    private String category;
    private String iconName;
}
