package com.music.suffer.library.repository;

import com.music.suffer.library.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByActivationCode(String activationCode);
}
