package com.workspace.booking.entity.identity;

import com.workspace.booking.common.enums.TokenType;
import com.workspace.booking.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "WS_ACCESS_TOKENS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessToken extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "token_type")
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    @Column(name = "EXPIRE_AT", nullable = false)
    private LocalDateTime expireAt;

    private Long revoked;



    private String tokenHash;
}