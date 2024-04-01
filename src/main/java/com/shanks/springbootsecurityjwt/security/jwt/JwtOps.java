package com.shanks.springbootsecurityjwt.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtOps {
    @Value("${shanks.JwtSecret}")
    String secret;
    @Value("${shanks.JwtExp}")
    int exp;
    public String getUsername(String token)
    {
        return getClaim(token,Claims :: getSubject);
    }
    public Date getExpiration(String token)
    {
        return getClaim(token,Claims :: getExpiration);
    }
    public boolean isExpired(Date date)
    {
        return date.before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails)
    {
        //getUsername() from Interface
        return userDetails.getUsername().equals(getUsername(token)) && !isExpired(getExpiration(token));
    }
    public <T>T getClaim(String token,Function<Claims,T> claimsFunction)
    {
        Claims claims = getALlClaims(token);
        return claimsFunction.apply(claims);
    }
    public Claims getALlClaims(String token)
    {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String createToken(String username)
    {
        Map<String,Object> claims=new HashMap<>();
        return generateToken(claims,username);
    }
    public String generateToken(Map<String,Object> claims,String username)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+exp))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    public Key getSignKey()
    {
        byte []b=Decoders.BASE64URL.decode(secret);
        return Keys.hmacShaKeyFor(b);
    }
}
