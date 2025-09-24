package com.nerosoft.aone.identity.application;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component("google")
public class GoogleAuthProvider extends BaseAuthProvider {
    private final RestTemplate rest = new RestTemplate();

    @Autowired
    public GoogleAuthProvider(Environment env) {
        super(env,"google");
    }

    @Override
    public OAuthResult authenticate(String authCode) throws RestClientException {
        var token = getToken(authCode);

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);
        headers.set("User-Agent", "A.one");
        var entity = new HttpEntity<>(headers);
        var future = rest.getForEntity("https://www.googleapis.com/oauth2/v3/userinfo", JSONObject.class, entity);

        var json = future.getBody();

        if (json == null) {
            throw new RestClientException("Invalid response from Google");
        }

        var sub = json.getString("sub");
        var googleEmail = json.getString("email");
        var name = json.getString("name");
        var picture = json.getString("picture");

        return new OAuthResult() {
            @Override
            public String getId() {
                return sub;
            }

            @Override
            public String getEmail() {
                return googleEmail;
            }

            @Override
            public String getUsername() {
                return googleEmail;
            }

            @Override
            public String getNickname() {
                return name;
            }

            @Override
            public String getAvatar() {
                return picture;
            }
        };
    }

    private String getToken(String code) throws RestClientException {
        var headers = new HttpHeaders();
        headers.setAccept(java.util.List.of(org.springframework.http.MediaType.APPLICATION_JSON));
        var entity = new HttpEntity<>(
                "code=" + code +
                        "&client_id=" + clientId +
                        "&client_secret=" + clientSecret +
                        "&redirect_uri=" + redirectUri +
                        "&grant_type=authorization_code",
                headers);
        var future = rest.postForEntity("https://oauth2.googleapis.com/token", entity, JSONObject.class);
        var json = future.getBody();
        if (json == null) {
            throw new RestClientException("Invalid response from Google");
        }
        return json.getString("access_token");
    }
}
