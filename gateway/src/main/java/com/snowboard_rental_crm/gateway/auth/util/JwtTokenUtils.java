package com.snowboard_rental_crm.gateway.auth.util;


import com.snowboard_rental_crm.auth_data.enumeration.TokenType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtTokenUtils {

    @Value("${security.jwt.secretKey}")
    private String secretKey;

    @Value("${security.jwt.amountToAdd}")
    private int amountToAdd;

    public Claims parseToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException |
                 ExpiredJwtException ex) {
            log.error("Invalid JWT Token", ex);
            throw new BadCredentialsException("Invalid JWT token", ex);
        }
    }

    public JwtToken generateToken(CustomUserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername()).setId(String.valueOf(userDetails.getUserId()));
        claims.put("tokenType", TokenType.ACCESS_TOKEN);

        String token = createToken(claims);
        return new JwtToken(token);
    }

    public JwtToken generateRefreshToken(CustomUserDetails userDetails) {
        Claims claims = Jwts.claims().setSubject(userDetails.getUsername()).setId(String.valueOf(userDetails.getUserId()));
        claims.put("tokenType", TokenType.REFRESH_TOKEN);

        String token = createRefreshToken(claims);
        return new JwtToken(token);
    }

    private String createToken(Claims claims) {
        Instant expirationDateTime = Instant.now().plus(amountToAdd, ChronoUnit.DAYS);
        long expirationDateTimeL = expirationDateTime.toEpochMilli();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expirationDateTimeL))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createRefreshToken(Claims claims) {
        Instant expirationDateTime = Instant.now().plus(System.currentTimeMillis(), ChronoUnit.MINUTES);
        long expirationDateTimeL = expirationDateTime.toEpochMilli();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(expirationDateTimeL))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
