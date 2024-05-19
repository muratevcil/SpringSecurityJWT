package com.folksdev.SpringSecurityJWT.service.requests;

public record AuthController(
        String name,
        String password
) {
}
