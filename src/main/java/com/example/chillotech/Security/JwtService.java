package com.example.chillotech.Security;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import com.example.chillotech.Entity.User;
import com.example.chillotech.Service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class JwtService {
    private final String  ENCRYPTION_KEY = "13c8c8197e54273bf29cbaea1fd500c3b874c3a12a795fc1f3c1dd10ceab9fd7";
    private UserService userService;

    public Map<String, String> generate(String username){
        User user = (User) this.userService.loadUserByUsername(username);
        return this.generateJwt(user);
    }
    
    public Map<String, String> generateJwt(User user){
        long currentTime = System.currentTimeMillis();
        long expirationTime = currentTime + 30 * 60 * 1000;

        Map<String, Object> claims = Map.of(
            "nom", user.getNom(),
            Claims.SUBJECT, user.getEmail(),
            Claims.EXPIRATION, new Date(expirationTime)
        );

        String bearer = Jwts.builder()
            .setIssuedAt(new Date(currentTime))
            .setExpiration(new Date(expirationTime))
            .setSubject(user.getEmail())
            .setClaims(claims)
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
        return Map.of("bearer", bearer);
    }

    //j'ai utilisé encryption key generator sur le web pour avoir la chaine de caractère
    public Key getKey(){
        byte[] decode = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decode);
    }

    /**public String extractUsername(String token) {
        Claims claims = this.getAllClaims(token);
        return claims.getSubject();
    }*/
    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isExpired(String token) {
        Date dateExpiration = this.getClaim(token, Claims::getExpiration);
        return dateExpiration.before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
