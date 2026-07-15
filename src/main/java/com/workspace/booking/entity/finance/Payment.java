package com.workspace.booking.entity.finance;

import com.workspace.booking.common.enums.*;
import com.workspace.booking.entity.BaseEntity;
import com.workspace.booking.entity.identity.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "WS_PAYMENTS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "INVOICE_ID")
    private Invoice invoice;

    @ManyToOne @JoinColumn(name = "USER_ID")
    private User user;

    private BigDecimal amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentGateway gateway;

    private String gatewayTxnId;

    @Lob
    private String gatewayResponse;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String failureReason;
    private BigDecimal refundAmount;
    private LocalDateTime refundedAt;
    private LocalDateTime paidAt;
}