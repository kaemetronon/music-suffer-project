package com.music.suffer.user.service;

import com.music.suffer.user.model.LoginRequest;
import com.music.suffer.user.model.TokenResponse;

public interface TokenGenerator {
    TokenResponse generate(LoginRequest request);
}
