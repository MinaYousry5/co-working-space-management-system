package com.workspace.booking.entity.engagement;

import com.workspace.booking.common.enums.*;
import com.workspace.booking.entity.BaseEntity;
import com.workspace.booking.entity.identity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "WS_NOTIFICATIONS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Notification extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "USER_ID")
    private User user;

    private String eventType;

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;

    private String subject;

    @Lob
    private String body;

    private String referenceType;
    private Long referenceId;
    private Integer isRead;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;

    @Enumerated(EnumType.STRING)
    private NotificationDeliveryStatus deliveryStatus;

    private String failureReason;
}
