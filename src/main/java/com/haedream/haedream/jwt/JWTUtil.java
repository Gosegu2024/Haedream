package com.haedream.haedream.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Jwts;

@Component
public class JWTUtil {

    // 객체변수
    private SecretKey secretKey;

    public JWTUtil(@Value("${spring.jwt.secret}") String secret) {

        secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }

    // Id 추출
    public String getId(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("id",
                String.class);
    }

    // Role 추출
    public String getRole(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().get("role",
                String.class);
    }

    // 토큰 만료일 확인
    public Boolean isExpired(String token) {

        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration()
                .before(new Date());
    }

    // 토큰 생성 메서드
    public String createJwt(String id, String role, Long expiredMs) {

        return Jwts.builder()
                // 토큰 생성에 필요한 키 지정
                .claim("id", id)
                .claim("role", role)
                // 토큰 발행일
                .issuedAt(new Date(System.currentTimeMillis()))
                // 토큰 만료일
                .expiration(new Date(System.currentTimeMillis() + expiredMs))
                // 암호화
                .signWith(secretKey)
                // 토큰 컴팩트
                .compact();
               
    }
}
