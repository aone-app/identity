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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    /** token type: access_token, refresh_token, etc.**/
    private String type;
    /** the SHA256 hash of the token **/
    private String key;
    /** the user subject which the token issues for **/
    private String subject;
    /** the time issues at **/
    private Date issues;
    /** the time expires at **/
    private Date expires;
}
