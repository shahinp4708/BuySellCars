package com.buysellcars.utils.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {

    private Key getSigningKey(){
        String SECRET = "A247DB24C8AE4B8A8CB259637754BF2FA247DB24C8AE4B8A8CB259637754BF2F";
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    private <T> T extractClaims(String token, Function<Claims,T> claimResolver){
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    public String extractUserName(String token){
        return extractClaims(token, Claims::getSubject);
    }
    private Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    public  boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private String generateToken(Map<String,Object> extractClaims,UserDetails userDetails){
       return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
               .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60*24))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    public  String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
}
