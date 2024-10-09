package com.t3h.e_commerce.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtils {

    @Value("${token.secret-key}")
    String SECRET_KEY;

    @Value("${token.access.expiration}")
    long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${token.refresh.expiration}")
    long REFRESH_TOKEN_EXPIRATION_TIME;

    public String generateToken(UserDetails userDetails, long expiration){
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("authorities", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    public String generateAccessToken(UserDetails userDetails){
        return generateToken(userDetails, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String generateRefreshToken(UserDetails userDetails){
        return generateToken(userDetails, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String getUserEmailFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
        final Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJwt(token).getBody();
        return claimResolver.apply(claims);
    }

    public boolean validate(UserDetails userDetails, String token){
        String userEmail = getUserEmailFromToken(token);
        return userEmail.equalsIgnoreCase(userDetails.getUsername()) && !isExpired(token);
    }

    public boolean isExpired(String token){
        Date expiry = getClaimFromToken(token, Claims::getExpiration);
        return expiry.before(new Date());
    }
}
