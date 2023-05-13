package com.example.Backend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.activation.spi.MailcapRegistryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    @Autowired
    @Value("${SIGNATURE_SECRET_KEY}")
    private String signatureKey;

    private Key getSecretKey(){
        return new SecretKeySpec(Base64.getDecoder().decode(signatureKey),
                SignatureAlgorithm.HS256.getJcaName());
    }
    public String extractEmail(String jwtToken) {
        return extractClaim(jwtToken,Claims::getSubject);
    }

    public String generateJwtToken(UserDetails userDetails){
        return generateJwtToken(new HashMap<>(),userDetails);
    }

    public boolean isTokenValid(String jwtToken,UserDetails userDetails){
        String userEmail = extractEmail(jwtToken);
        return (userEmail.equals(userDetails.getUsername())) && ! isTokenExpired(jwtToken);
    }

    private boolean isTokenExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken){
        return extractClaim(jwtToken,Claims::getExpiration);
    }
    public String generateJwtToken(Map<String,Object> extraClaims,
            UserDetails userDetails){


        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
                .signWith(getSecretKey())
                .compact();

    }

    public <T> T extractClaim(String jwtToken, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaims(jwtToken);
        return claimResolver.apply(claims);
    }

    public Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private byte[] getSigningKey() {
        return Decoders.BASE64.decode(signatureKey);
    }
}
