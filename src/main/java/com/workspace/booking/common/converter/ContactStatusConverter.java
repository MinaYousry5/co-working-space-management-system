package com.workspace.booking.common.converter;

import com.workspace.booking.common.enums.ContactStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class ContactStatusConverter implements AttributeConverter<ContactStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ContactStatus attribute) {
        return attribute == null ? null : attribute.value();
    }

    @Override
    public ContactStatus convertToEntityAttribute(Integer dbData) {
        return ContactStatus.fromValue(dbData);
    }
}
