package com.music.suffer.user.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String jti;
}
