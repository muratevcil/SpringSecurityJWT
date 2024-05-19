package com.folksdev.SpringSecurityJWT.service.requests;

import java.util.Set;


import com.folksdev.SpringSecurityJWT.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String name;
    private String username;
    private String password;
    private Set<Role> authorities;
}