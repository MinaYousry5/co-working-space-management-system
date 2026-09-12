package com.workspace.booking.entity.finance;

import com.workspace.booking.common.enums.ManualDepositMethod;
import com.workspace.booking.common.enums.PaymentStatus;
import com.workspace.booking.common.enums.PaymentTransactionType;
import com.workspace.booking.entity.BaseEntity;
import com.workspace.booking.entity.booking.Booking;
import com.workspace.booking.entity.identity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "WS_PAYMENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKING_ID", nullable = false)
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "TRANSACTION_TYPE", nullable = false)
    private PaymentTransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private PaymentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "DEPOSIT_METHOD")
    private ManualDepositMethod depositMethod;

    @Column(name = "PAID_TO_NUMBER")
    private String paidToNumber;

    @Column(name = "SENDER_NUMBER")
    private String senderNumber;

    @Column(name = "REFERENCE_CODE")
    private String referenceCode;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "REFUND_AMOUNT")
    private BigDecimal refundAmount;

    @Lob
    @Column(name = "SCREENSHOT_BLOB")
    private byte[] screenshotBlob;

    @Column(name = "SCREENSHOT_MIME_TYPE")
    private String screenshotMimeType;

    @Column(name = "SCREENSHOT_FILENAME")
    private String screenshotFilename;

    @Column(name = "REASON_OF_REJECT")
    private String reasonOfReject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ADMIN_DECISION_BY")
    private User adminDecisionBy;

    @Column(name = "ADMIN_DECISION_AT")
    private LocalDateTime adminDecisionAt;
}
