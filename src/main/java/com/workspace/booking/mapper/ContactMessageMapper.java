package com.workspace.booking.mapper;

import com.workspace.booking.common.enums.ContactStatus;
import com.workspace.booking.dto.contact.ContactMessageResponse;
import com.workspace.booking.entity.engagement.ContactMessage;
import org.springframework.stereotype.Component;

@Component
public class ContactMessageMapper {

    public ContactMessageResponse toResponse(ContactMessage entity) {
        return new ContactMessageResponse(
                entity.getId(),
                entity.getUser() == null ? null : entity.getUser().getId(),
                entity.getCustomerName(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getMessage(),
                entity.getStatus(),
                entity.getStatus() == ContactStatus.OPEN,
                entity.getCreatedOn(),
                entity.getUpdatedOn()
        );
    }
}
