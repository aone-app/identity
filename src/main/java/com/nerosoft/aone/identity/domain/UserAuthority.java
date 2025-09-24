package com.nerosoft.aone.identity.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public final class UserAuthority {
    @Id
    private String id;
    @Column(name = "user_id")
    private String userId;
    private String provider;
    @Column(name = "open_id")
    private String openId;
    private String name;
    @Column(updatable = false, name = "created_at")
    private Date createdAt;
}
