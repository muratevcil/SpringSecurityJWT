package com.folksdev.SpringSecurityJWT.repository;

import com.folksdev.SpringSecurityJWT.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findUserByUsername(String userName);

    Optional<User> findByUsername(String username);
}
