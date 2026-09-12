package com.workspace.booking.dto.payment;

import com.workspace.booking.common.enums.ManualDepositMethod;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public record PaymentRequest(
                              @NotNull ManualDepositMethod depositMethod,
                              @NotNull String paidToNumber,
                              @NotNull String senderNumber,
                              @NotNull String referenceCode,
                              @NotNull BigDecimal amount,
                              @NotNull MultipartFile screenshot) {
}
