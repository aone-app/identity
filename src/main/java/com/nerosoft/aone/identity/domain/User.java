package com.nerosoft.aone.identity.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
public final class User {
    /**
     * User id
     **/
    @Id
    private long id;
    /**
     * Username
     **/
    @Column(unique = true, length = 128, updatable = false)
    private String username;
    @Column(name = "password_hash", length = 500)
    private String passwordHash;
    @Column(name = "password_salt", length = 32)
    private String passwordSalt;
    @Column(length = 255)
    private String email;
    @Column(length = 20)
    private String phone;
    @Column(length = 200)
    private String nickname;
    @Column(name = "access_failed_count")
    private int accessFailedCount;
    @Column(name = "lockout_end")
    private Date lockoutEnd;
}
