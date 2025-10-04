package com.nerosoft.aone.identity.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity(name = "user_authority")
public final class UserAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "user_id")
    private long userId;
    private String provider;
    @Column(name = "open_id", length = 64, updatable = false)
    private String openId;
    @Column(length = 200, updatable = false)
    private String name;
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
}
