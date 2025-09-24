package com.nerosoft.aone.identity.application;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component("github")
@ConfigurationProperties(prefix = "auth.github")
public class GithubAuthProvider extends BaseAuthProvider {
    private final RestTemplate rest = new RestTemplate();

    @Autowired
    public GithubAuthProvider(Environment env) {
        super(env, "github");
    }

    @Override
    public OAuthResult authenticate(String authCode) throws RestClientException {
        var token = getToken(authCode);

        var user = getUser(token);

        return new OAuthResult() {
            @Override
            public String getId() {
                return user.getString("id");
            }

            @Override
            public String getUsername() {
                return user.getString("login");
            }

            @Override
            public String getEmail() {
                return user.getString("email");
            }

            @Override
            public String getNickname() {
                return user.getString("name");
            }

            @Override
            public String getAvatar() {
                return user.getString("avatar_url");
            }
        };
    }

    private JSONObject getUser(String token) throws RestClientException {
        var headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));
        headers.set("User-Agent", "A.one");
        var entity = new HttpEntity<>(headers);
        var future = rest.getForEntity("https://api.github.com/user", JSONObject.class, entity);
        var json = future.getBody();
        if (json == null || !json.has("id")) {
            throw new RestClientException("Invalid response from GitHub");
        }
        return json;
    }

    private String getToken(String code) throws RestClientException {
        var headers = new HttpHeaders();
        headers.setAccept(java.util.List.of(MediaType.APPLICATION_JSON));

        var params = new java.util.HashMap<String, String>();
        params.put("client_id", clientId);
        params.put("client_secret", clientSecret);
        params.put("code", code);

        var future = rest.postForEntity("https://github.com/login/oauth/access_token", params, JSONObject.class, headers);
        if (!future.getStatusCode().is2xxSuccessful()) {
            throw new RestClientException("Invalid code received");
        }
        var json = future.getBody();
        if (json == null || !json.has("access_token")) {
            throw new RestClientException("Invalid response from GitHub");
        }
        return json.getString("access_token");
    }
}
