package com.workspace.booking.service;

import com.workspace.booking.common.enums.ContactStatus;
import com.workspace.booking.dto.contact.ContactMessageCreateRequest;
import com.workspace.booking.dto.contact.ContactMessageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContactMessageService {
    ContactMessageResponse create(ContactMessageCreateRequest request);

    Page<ContactMessageResponse> getAll(Pageable pageable);

    Page<ContactMessageResponse> getByUserId(Long userId, Pageable pageable);

    Page<ContactMessageResponse> getByStatus(ContactStatus status, Pageable pageable);

    ContactMessageResponse close(Long id);
}
