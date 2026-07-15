package com.workspace.booking.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class BookingOverlapException extends RuntimeException {
    public BookingOverlapException(String msg) { super(msg); }
}


