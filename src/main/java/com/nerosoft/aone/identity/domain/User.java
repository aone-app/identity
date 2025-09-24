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
    private String id;
    /**
     * Username
     **/
    @Column(unique = true, updatable = false)
    private String username;
    @Column(name = "password_hash")
    private String passwordHash;
    @Column(name = "password_salt")
    private String passwordSalt;
    private String email;
    private String phone;
    private String nickname;
    @Column(name = "access_failed_count")
    private int accessFailedCount;
    @Column(name = "lockout_end")
    private Date lockoutEnd;
}
