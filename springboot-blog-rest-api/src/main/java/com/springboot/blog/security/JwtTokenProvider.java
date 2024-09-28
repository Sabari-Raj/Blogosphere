package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app-jwt-expiration-milliseconds}")
    private Long jwtExpirationDate;

    public String generateToken(Authentication authentication)
    {
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate =new Date(currentDate.getTime()+jwtExpirationDate);
        String token = Jwts.builder().setSubject(username).setIssuedAt(currentDate).setExpiration(expirationDate)
                .signWith(key()).compact();
        return token;
    }
    private Key key()
    {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public String getUsername(String token)
    {
        return Jwts.parserBuilder()
            .setSigningKey(key()).build().parseClaimsJws(token)
                .getBody().getSubject();
    }
    public boolean validateToken(String Token)
    {
        try {
            Jwts.parserBuilder().setSigningKey(key())
                    .build().parse(Token);
            return true;
        }
        catch (MalformedJwtException malformedJwtException){
            throw new BlogAPIException("Invalid Jwt Token", HttpStatus.BAD_REQUEST);
        }
        catch(ExpiredJwtException expiredJwtException){
            throw  new BlogAPIException("Expired JWT token", HttpStatus.BAD_REQUEST);
        }
        catch(UnsupportedJwtException unsupportedJwtException){
            throw  new BlogAPIException("Unsupported JWT exception", HttpStatus.BAD_REQUEST);
        }
        catch(IllegalArgumentException illegalArgumentException){
            throw  new BlogAPIException("Jwt claims string is null or empty", HttpStatus.BAD_REQUEST);
        }

    }
}
