package com.nerosoft.aone.identity.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * This class represents a Token entity
 */
@Data
@Entity
public final class Token {
    /** token id **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    /** token type: access_token, refresh_token, etc.**/
    @Column(length = 20, nullable = false, updatable = false)
    private String type;
    /** the SHA256 hash of the token **/
    @Column(length = 512, nullable = false, updatable = false)
    private String key;
    /** the user subject which the token issues for **/
    private long subject;
    /** the time issues at **/
    private Date issues;
    /** the time expires at **/
    private Date expires;

    public static Token create(String type, String key, long subject, Date issues, Date expires) {
        var entity = new Token();
        entity.setType(type);
        entity.setKey(key);
        entity.setSubject(subject);
        entity.setIssues(issues);
        entity.setExpires(expires);
        return entity;
    }
}
