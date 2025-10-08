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
import org.springframework.context.ApplicationContext;
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
    private final ApplicationContext applicationContext;

    @Autowired
    public AuthApplicationServiceImpl(UserRepository repository, Environment environment, ApplicationEventPublisher publisher, ApplicationContext applicationContext) {

        this.repository = repository;
        this.environment = environment;
        this.publisher = publisher;
        this.applicationContext = applicationContext;
    }

    @Override
    public CompletableFuture<AuthResponseDto> grant(AuthRequestDto request) throws CredentialException {
        Assert.notNull(request, "request cannot be null");

        User user;
        switch (request.getProvider()) {
            case "username": {
                var optional = repository.findByUsername(request.getUsername());

                if (optional.isEmpty()) {
                    throw new CredentialException("User name or password is incorrect");
                }

                user = optional.get();
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
            }
            break;
            case "email": {
                // 检查校验码是否正确

                var optional = repository.findByEmail(request.getUsername());
                if (optional.isEmpty()) {
                    throw new CredentialException("Email is incorrect");
                }

                user = optional.get();

            }
            break;
            case "phone": {
                // 检查校验码是否正确

                var optional = repository.findByPhone(request.getUsername());
                if (optional.isEmpty()) {
                    throw new CredentialException("Phone is incorrect");
                }

                user = optional.get();

            }
            case "github":
            case "google":
            case "microsoft": {
                var provider = applicationContext.getBean(request.getProvider() + "AuthProvider", AuthProvider.class);
                var profile = provider.authenticate(request.getUsername());
                if (profile == null) {
                    throw new CredentialException("Third-party authentication failed");
                }
                var optional = repository.findByOpenId(request.getProvider(), profile.getId());
                if (optional.isEmpty()) {
                    throw new CredentialException("No user is associated with the third-party account");
                }
                user = optional.get();
            }
            break;
            case "refresh_token":
                throw new CredentialException("Refresh token is not supported yet");

            default:
                throw new IllegalArgumentException("Unknown provider");
        }

        publisher.publishEvent(new UserAuthSucceedEvent(this));

        var token = generateToken(user);

        var response = new AuthResponseDto();
        response.setAccessToken(token);
        response.setTokenType("Bearer");
        response.setExpiresIn((long) (3600 * 24));
        response.setRefreshToken("");
        response.setUsername(user.getUsername());
        response.setSubject(String.valueOf(user.getId()));
        response.setTokenType("Bearer");

        return CompletableFuture.completedFuture(response);
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
        builder.subject(String.valueOf(user.getId()))
                .id(UUID.randomUUID().toString())
                .issuer(environment.getProperty("JwtAuthenticationOptions.Issuer"))
                .issuedAt(new Date(timestamp))
                .expiration(new Date(timestamp + 3600 * 24)) // 24小时后过期
                .claim("name", user.getUsername());
        builder.signWith(Keys.hmacShaKeyFor(signingKey.getBytes()));
        return builder.compact();
    }
}

