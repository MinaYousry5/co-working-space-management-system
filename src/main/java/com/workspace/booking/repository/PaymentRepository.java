package com.workspace.booking.repository;

import com.workspace.booking.common.enums.PaymentStatus;
import com.workspace.booking.common.enums.PaymentTransactionType;
import com.workspace.booking.entity.finance.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Page<Payment> findAllByOrderByCreatedOnDesc(Pageable pageable);

    Page<Payment> findByStatusOrderByCreatedOnDesc(PaymentStatus status, Pageable pageable);

    Page<Payment> findByTransactionTypeOrderByCreatedOnDesc(PaymentTransactionType transactionType, Pageable pageable);

    Optional<Payment> findFirstByBookingIdAndTransactionTypeAndStatus(
            Long bookingId,
            PaymentTransactionType transactionType,
            PaymentStatus status
    );

    boolean existsByBookingIdAndTransactionType(Long bookingId, PaymentTransactionType transactionType);
}
