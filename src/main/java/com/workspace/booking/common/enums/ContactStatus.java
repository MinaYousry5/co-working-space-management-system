package com.workspace.booking.common.enums;

public enum ContactStatus {
    OPEN(0),
    CLOSED(1);

    private final Integer value;

    ContactStatus(Integer value) {
        this.value = value;
    }

    public Integer value() {
        return value;
    }

    public static ContactStatus fromValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (ContactStatus status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unsupported contact status value: " + value);
    }
}
