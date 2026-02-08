package com.vnote.VNote.services;

import com.vnote.VNote.exceptions.InvalidToken;
import com.vnote.VNote.modules.base.JWTPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.InvalidClaimException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class JWTService {

    private final SecretKey jwtSecret;

    public JWTService(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtSecret));
    }


    private String generateToken(JWTPayload jwtPayload) {
        final Date now = new Date();
        final Date expiration = new Date(now.getTime() + jwtPayload.getExpiry());
        return Jwts.builder()
                .subject(String.valueOf(jwtPayload.getUserId()))
                .claim("type", jwtPayload.getType())
                .issuedAt(now)
                .expiration(expiration)
                .signWith(jwtSecret, Jwts.SIG.HS256)
                .compact();

    }

    public String generateAccessToken(UUID userId) {
        return this.generateToken(JWTPayload.builder()
                .userId(userId)
                .type("access")
                .expiry(JWTServiceConfig.ACCESS_TOKEN_VALIDITY)
                .build()
        );

    }

    public String generateRefreshToken(UUID userId) {
        return this.generateToken(JWTPayload.builder()
                .userId(userId)
                .type("refresh")
                .expiry(JWTServiceConfig.REFRESH_TOKEN_VALIDITY)
                .build()
        );
    }

    public boolean validateAccessToken(String accessToken) {
        final Claims claims = this.parseAllClaims(accessToken);
        if (claims == null) return false;
        try {
            final String tokenType = claims.get("type").toString();
            return tokenType.equals("access");
        } catch (Exception e) {
            return false;
        }

    }

    public boolean validateRefreshToken(String accessToken) {
        final Claims claims = this.parseAllClaims(accessToken);
        if (claims == null) return false;
        try {
            final String tokenType = claims.get("type").toString();
            return tokenType.equals("refresh");
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserIdFromToken(String token) throws InvalidToken {
        final String rawToken;
        if(token.startsWith("Bearer ")) rawToken = token.split(" ")[0];
        else rawToken = token;

        final Claims claims = this.parseAllClaims(rawToken);
        if(claims == null) throw new InvalidToken("Invalid token.");
        return claims.getSubject();
    }

    private Claims parseAllClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(this.jwtSecret)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }
}


class JWTServiceConfig {
    public static long ACCESS_TOKEN_VALIDITY = 15L * 60L * 1000L;
    public static long REFRESH_TOKEN_VALIDITY = 30L * 60L * 60L * 1000L;
}
