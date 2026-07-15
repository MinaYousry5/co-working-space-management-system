package com.workspace.booking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseEntity {

    @Column(name = "ROW_VERSION", nullable = false)
    private Integer rowVersion;

    @Column(name = "CREATED_ON", nullable = false)
    private LocalDateTime createdOn;

    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;

    @Column(name = "UPDATED_ON", nullable = false)
    private LocalDateTime updatedOn;

    @Column(name = "UPDATED_BY", nullable = false)
    private String updatedBy;

    @PrePersist
    protected void onCreate() {
        var now = LocalDateTime.now();
        rowVersion = rowVersion == null ? 1 : rowVersion;
        createdOn = createdOn == null ? now : createdOn;
        updatedOn = updatedOn == null ? now : updatedOn;
        createdBy = createdBy == null ? "SYSTEM" : createdBy;
        updatedBy = updatedBy == null ? createdBy : updatedBy;
    }

    @PreUpdate
    protected void onUpdate() {
        rowVersion = rowVersion == null ? 1 : rowVersion + 1;
        updatedOn = LocalDateTime.now();
        updatedBy = updatedBy == null ? "SYSTEM" : updatedBy;
    }
}
