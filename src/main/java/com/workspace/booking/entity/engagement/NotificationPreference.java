package com.workspace.booking.entity.engagement;

import com.workspace.booking.entity.BaseEntity;
import com.workspace.booking.entity.identity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "WS_NOTIFICATION_PREFERENCES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationPreference extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    private String eventType;
    private Integer emailEnabled;
    private Integer smsEnabled;
    private Integer pushEnabled;
    private Integer inAppEnabled;
}
