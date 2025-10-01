package com.nerosoft.aone.identity.application;

import com.nerosoft.aone.identity.domain.User;
import com.nerosoft.aone.identity.dto.AuthRequestDto;
import com.nerosoft.aone.identity.dto.AuthResponseDto;
import com.nerosoft.aone.identity.event.UserAuthSucceedEvent;
import com.nerosoft.aone.identity.repository.UserRepository;
import com.nerosoft.aone.identity.utils.Cryptography;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.security.auth.login.CredentialException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class AuthApplicationServiceImpl implements AuthApplicationService {

    private final UserRepository repository;
    private final Environment environment;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public AuthApplicationServiceImpl(UserRepository repository, Environment environment, ApplicationEventPublisher publisher) {

        this.repository = repository;
        this.environment = environment;
        this.publisher = publisher;
    }

    @Override
    public CompletableFuture<AuthResponseDto> grant(AuthRequestDto request) throws CredentialException {
        Assert.notNull(request, "request cannot be null");

        User user = null;
        switch (request.getProvider()) {
            case "username":
                user = repository.findByUsername(request.getUsername());
                if (user == null) {
                    throw new CredentialException("User name or password is incorrect");
                }

                // 使用DES加密算法对密码进行加密

                String password = null;
                try {
                    password = Cryptography.AES.encrypt(request.getPassword(), user.getPasswordSalt());
                } catch (Exception e) {
                    throw new CredentialException("Encryption error");
                }

                if (!password.equals(request.getPassword())) {
                    throw new CredentialException("User name or password is incorrect");
                }
                break;
            case "refresh_token":
                throw new CredentialException("Refresh token is not supported yet");

            default:
                throw new IllegalArgumentException("Unknown provider");
        }

        publisher.publishEvent(new UserAuthSucceedEvent(this));

        var token = generateToken(user);

        User finalUser = user;
        return CompletableFuture.completedFuture(new AuthResponseDto() {
            {
                setAccessToken(token);
                setTokenType("Bearer");
                setExpiresIn((long) (3600 * 24));
                setRefreshToken("");
                setUsername(finalUser.getUsername());
                setSubject(finalUser.getId());
                setTokenType("Bearer");
            }
        });
    }

    @Override
    public CompletableFuture<AuthResponseDto> refresh(String refreshToken) throws CredentialException {
        return CompletableFuture.completedFuture(null);
    }

    private String generateToken(User user) {
        Assert.notNull(user, "user cannot be null");
        var signingKey = environment.getProperty("JwtAuthenticationOptions.SigningKey");
        Assert.notNull(signingKey, "SigningKey cannot be null");

        var timestamp = System.currentTimeMillis();

        var builder = Jwts.builder();
        builder.subject(user.getId())
                .id(UUID.randomUUID().toString())
                .issuer(environment.getProperty("JwtAuthenticationOptions.Issuer"))
                .issuedAt(new Date(timestamp))
                .expiration(new Date(timestamp + 3600 * 24)) // 24小时后过期
                .claim("name", user.getUsername());
        builder.signWith(Keys.hmacShaKeyFor(signingKey.getBytes()));
        return builder.compact();
    }
}

