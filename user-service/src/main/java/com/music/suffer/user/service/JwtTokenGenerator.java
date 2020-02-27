package com.music.suffer.user.service;

import com.music.suffer.user.model.LoginRequest;
import com.music.suffer.user.model.TokenResponse;
import com.music.suffer.user.model.User;
import com.music.suffer.user.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtTokenGenerator implements TokenGenerator {
    private final String TOKEN_TYPE = "Bearer";
    private final UserRepository userRepository;

    @Override
    public TokenResponse generate(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() ->
                new RuntimeException("User with email " + request.getEmail() + " not found"));

        Map<String, Object> claims = new HashMap<>();
        claims.put("jti", user.getId().toString());
        claims.put("email", user.getEmail());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        claims.put("role", user.getAuthorities());

        return new TokenResponse()
                .setAccessToken(generateJwt(claims, 3600L))
                .setRefreshToken(generateJwt(claims, 86400L))
                .setJti(user.getId().toString())
                .setTokenType(TOKEN_TYPE);
    }

    private String generateJwt(Map<String, Object> claims, long expiration) {
        Date expirationDate = Date.from(Instant.now().plusSeconds(expiration));
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, "secret")
                .compact();
    }
}
