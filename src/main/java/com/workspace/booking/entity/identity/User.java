package com.workspace.booking.entity.identity;

import com.workspace.booking.common.enums.YesNo;
import com.workspace.booking.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "WsUser")
@Table(name = "WS_USERS")
public class User extends BaseEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", unique = true, nullable = false)
    private String username;

    @Column(name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_ACTIVE")
    private YesNo isActive;

    @Column(name = "COMPANY_NAME")
    private String companyName;

    @Column(name = "JOB_TITLE")
    private String jobTitle;

    @Lob
    @Column(name = "AVATAR_BLOB")
    private byte[] avatarBlob;

    @Column(name = "AVATAR_MIME_TYPE")
    private String avatarMimeType; // e.g., "image/png"

    @Column(name = "AVATAR_FILENAME")
    private String avatarFileName;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserRole> roles;

}