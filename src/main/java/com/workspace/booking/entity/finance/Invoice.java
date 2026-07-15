package com.workspace.booking.entity.finance;

import com.workspace.booking.common.enums.*;
import com.workspace.booking.entity.BaseEntity;
import com.workspace.booking.entity.booking.Booking;
import com.workspace.booking.entity.identity.User;
import com.workspace.booking.entity.identity.UserMembership;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "WS_INVOICES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Invoice extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne @JoinColumn(name = "BOOKING_ID")
    private Booking booking;

    @ManyToOne @JoinColumn(name = "MEMBERSHIP_ID")
    private UserMembership membership;

    private String invoiceNumber;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    private BigDecimal subtotal;
    private BigDecimal taxRate;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    private String currency;
    private LocalDateTime dueDate;
    private LocalDateTime paidAt;

    @Lob
    private String notes;

    @Lob
    private byte[] pdfBlob;
    private String pdfMimeType;
    private String pdfFilename;
    private LocalDateTime issuedAt;
}