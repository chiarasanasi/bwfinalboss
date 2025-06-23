package it.epicode.bwfinalboss.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.model.Utente;
import it.epicode.bwfinalboss.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTool {

    @Autowired
    private UtenteService utenteService;

    @Value("${jwt.duration}")
    private Long durata;

    @Value("${jwt.secret}")
    private String chiaveSegreta;


    public String createToken(Utente utente) {
        return Jwts.builder()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + durata))
                .subject(utente.getEmail())
                .claim("username", utente.getUsername())
                .claim("ruoli", utente.getRuoli().stream().map(Enum::name).collect(Collectors.toList()))
                .signWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .compact();
    }


    public void validateToken(String token) {
        Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parse(token);
    }


    public Utente getUserFromToken(String token) throws NotFoundException {
        String email = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(chiaveSegreta.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        return utenteService.getUtenteByEmail(email);  // Assicurati che esista
    }
}
