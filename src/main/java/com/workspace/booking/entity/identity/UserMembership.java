package com.workspace.booking.entity.identity;

import com.workspace.booking.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "WS_USER_MEMBERSHIPS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserMembership extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne @JoinColumn(name = "PLAN_ID")
    private MembershipPlan plan;
}