package com.cptvolt.expensetracker.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

// This comment serves to remind me constantly of this little thing I keep forgetting
// to declare, I keep losing my beans I swear to god
@Service
public class JwtService {

    private static final String SECRET_KEY = "supersecretkeysupersecretkey12345678232131423412312415"; // To switch for environment var

    public String extractUsername(String token) {
        return extractClaim(token, claims -> claims.getSubject()); //I don't like replacing lambdas ok?
    }

    public <T> T extractClaim(String token, Function<io.jsonwebtoken.Claims, T> claimsResolver) {
        final var claims = Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours seems fine
                .signWith(getSignInKey(), SignatureAlgorithm.HS256) // CAREFUL WITH AUTO HERE, I HAD IT AS ES256 AND CRIED
                .compact();
    }

    public boolean isTokenValid(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return extractClaim(token, claims -> claims.getExpiration().before(new Date()));

    }
}
