//package com.workspace.booking.entity.booking;
//
//import com.workspace.booking.common.enums.*;
//import com.workspace.booking.entity.BaseEntity;
//import com.workspace.booking.entity.workspace.Location;
//import jakarta.persistence.*;
//import lombok.*;
//
//import java.math.BigDecimal;
//
//@Entity
//@Table(name = "WS_RESOURCES")
//@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
//public class Resource extends BaseEntity {
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne @JoinColumn(name = "RESOURCE_TYPE_ID")
//    private ResourceType resourceType;
//
//    private String resourceName;
//    private String description;
//    private Integer quantityTotal;
//    private Integer quantityAvailable;
//    private BigDecimal pricePerUnit;
//
//    @Enumerated(EnumType.STRING)
//    private PriceUnit priceUnit;
//
//    private String currency;
//
//    @Enumerated(EnumType.STRING)
//    private YesNo isActive;
//}
