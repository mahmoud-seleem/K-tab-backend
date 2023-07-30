package com.example.Backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.activation.spi.MailcapRegistryProvider;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import org.slf4j.Logger;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.PrivateKey;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

@Service
public class JwtService {
    @Autowired
    @Value("${SIGNATURE_SECRET_KEY}")
    private String signatureKey;

    private Key getSecretKey(){
        return new SecretKeySpec(Decoders.BASE64.decode(signatureKey),
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
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(48)))
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


    public String parseJwt(String headerAuth) {
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
    public String generateJwtToken(Authentication authentication) {

        AppUserDetails userPrincipal = (AppUserDetails) authentication.getPrincipal();
        return Jwts
                .builder()
                .setSubject((userPrincipal.getUsername()))
                .claim("userId",userPrincipal.getUserId())
                .claim("userType",userPrincipal.getUserType())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(48)))
                .signWith(getSecretKey())
                .compact();
    }

    public String getUserEmailFromJwtToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        return parseJwt(headerAuth);
    }
    public  UUID getUserId(HttpServletRequest request) {
        String token = parseJwt(request);
        return UUID.fromString(
                Jwts.parserBuilder()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("userId",String.class));
    }
    public String getUserType(HttpServletRequest request) {
        String token = parseJwt(request);
        return Jwts.parserBuilder()
                        .setSigningKey(getSecretKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody()
                        .get("userType",String.class);
    }
    public boolean validateJwtToken(String jwtToken,UserDetails userDetails) {
        try {
            Jwts.parserBuilder().setSigningKey(getSecretKey()).build().parseClaimsJws(jwtToken);
            return isTokenValid(jwtToken,userDetails);
        } catch (SignatureException e) {
        } catch (MalformedJwtException e) {
        } catch (ExpiredJwtException e) {
        } catch (UnsupportedJwtException e) {
        } catch (IllegalArgumentException e) {
        }
        return false;
    }
}
