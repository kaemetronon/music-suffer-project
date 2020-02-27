package com.music.suffer.user.service;

import com.music.suffer.user.model.User;
import com.music.suffer.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class JwtTokenParser implements TokenParser {
    private final String BEARER_PREFIX = "Bearer ";
    private final UserRepository userRepository;

    @Override
    public User parse(String token) {
        if (!StringUtils.hasText(token) || !token.startsWith(BEARER_PREFIX)) {
            throw new RuntimeException("Invalid token!");
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey("secret")
                    .parseClaimsJws(token.substring(BEARER_PREFIX.length()))
                    .getBody();
            Long userId = claims.get("userId", Long.class);
            return userRepository.findById(userId).orElseThrow(() ->
                    new RuntimeException("Invalid token: no user found"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean validate(String token) {
        return parse(token) != null;
    }
}
