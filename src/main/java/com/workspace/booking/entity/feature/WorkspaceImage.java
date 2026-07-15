package com.workspace.booking.entity.feature;

import com.workspace.booking.entity.BaseEntity;
import com.workspace.booking.entity.workspace.Workspace;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "WS_WORKSPACE_IMAGES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WorkspaceImage extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "WORKSPACE_ID")
    private Workspace workspace;

    @Lob
    private byte[] imageBlob;
    private String mimeType;
    private String fileName;
    private String altText;
    private Integer displayOrder;
    private Integer isPrimary;
    private Long fileSizeKb;
}
