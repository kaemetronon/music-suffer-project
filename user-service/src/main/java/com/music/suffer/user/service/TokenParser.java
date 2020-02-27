package com.music.suffer.user.service;

import com.music.suffer.user.model.User;

public interface TokenParser {
    User parse(String token);

    boolean validate(String token);
}
