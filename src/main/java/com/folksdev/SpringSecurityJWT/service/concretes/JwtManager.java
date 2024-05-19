package com.folksdev.SpringSecurityJWT.service.concretes;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtManager{

    @Value("${jwt.key}")
    private String SECRET;

    public String generateToken(String userName){
        Map<String, Object> claims = new HashMap<>();
        claims.put("murat","jwt");
        return createToken(claims,userName);
    };

    public boolean validateToken(String token, UserDetails userDetails){
        String userName   = extractUser(token);
        Date expiration = extractExpiration(token);
        return userDetails.getUsername().equals(userName) && !expiration.before(new Date());
    };

    public Date extractExpiration(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
        return claims.getExpiration();
    };
    public String extractUser(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJwt(token)
                .getBody();
        return claims.getSubject();
    };
    private String createToken(Map<String, Object> claims, String userName){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() * 1000 * 60 * 2))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    };
    private Key getSignKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    };

}
