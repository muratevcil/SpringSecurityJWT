package com.folksdev.SpringSecurityJWT.service.concretes;

import java.util.Optional;

import com.folksdev.SpringSecurityJWT.model.User;
import com.folksdev.SpringSecurityJWT.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.folksdev.SpringSecurityJWT.service.requests.CreateUserRequest;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;




@Service
@AllArgsConstructor

public class UserManager implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<User> getByUserName(String username){
        return userRepository.findUserByUsername(username);
    }

    public User createUser(CreateUserRequest createUserRequest) {
        User newUser = User.builder()
                .name(createUserRequest.getName())
                .username(createUserRequest.getUsername())
                .password(passwordEncoder.encode(createUserRequest.getPassword()))
                .authorities(createUserRequest.getAuthorities())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialNonExpired(true)
                .isEnabled(true)
                .build();
        return userRepository.save(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }
}
