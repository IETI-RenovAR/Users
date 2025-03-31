package org.adaschool.project.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.adaschool.project.dto.TokenDTO;
import org.adaschool.project.model.RoleEnum;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static org.adaschool.project.utils.Constants.CLAIMS_ROLES_KEY;


@Component
public class JwtUtil {

    private final JwtConfig jwtConfig;

    public JwtUtil(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public TokenDTO generateToken(String email, List<RoleEnum> roles) {

        Date expirationDate = jwtConfig.getExpirationDate();

        List<String> roleNames = roles.stream()
                .map(Enum::name)
                .toList();

        String token = Jwts.builder().subject(email)
                .issuedAt(new Date())
                .expiration(expirationDate)
                .claim(CLAIMS_ROLES_KEY, roleNames)
                .signWith(jwtConfig.getSigningKey())
                .compact();
        return new TokenDTO(token, expirationDate);
    }

    public Claims extractAndVerifyClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
