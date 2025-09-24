package com.nerosoft.aone.identity.application;

import lombok.Data;

@Data
public class OAuthResult {
    public String id;
    public String username;
    public String nickname;
    public String avatar;
    public String email;
    public String phone;
}
