package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.dto.UtenteDto;
import it.epicode.bwfinalboss.enumeration.Role;
import it.epicode.bwfinalboss.exception.AlreadyExistException;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.exception.UnAuthorizedException;
import it.epicode.bwfinalboss.model.Utente;
import it.epicode.bwfinalboss.repository.UtenteRepository;
import it.epicode.bwfinalboss.security.JwtTool;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Data
public class UtenteService {

    private final UtenteRepository utenteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTool jwtTool;

    public String register(Utente utente) throws AlreadyExistException {
        if (utenteRepository.existsByEmail(utente.getEmail())) {
            throw new AlreadyExistException("Email già registrata.");
        }
        if (utenteRepository.existsByUsername(utente.getUsername())) {
            throw new AlreadyExistException("Username già in uso.");
        }

        utente.setPassword(passwordEncoder.encode(utente.getPassword()));

        if (utente.getRuoli() == null || utente.getRuoli().isEmpty()) {
            utente.setRuoli(Set.of(Role.USER));
        }

        utenteRepository.save(utente);
        return "Registrazione completata!";
    }

    public String login(String username, String password) throws NotFoundException, UnAuthorizedException {
        Utente utente = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente non trovato."));

        if (!passwordEncoder.matches(password, utente.getPassword())) {
            throw new UnAuthorizedException("Password errata.");
        }

        return jwtTool.createToken(utente);
    }

    public Utente getUtenteByUsername(String username) throws NotFoundException {
        return utenteRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("Utente non trovato."));
    }
    public Utente getUtenteByEmail(String email) throws NotFoundException {
        return utenteRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("Utente non trovato con email: " + email));
    }
    public Utente getUtenteById(int id) throws NotFoundException {
        return utenteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Utente con il seguenti id " + id + "non trovato."));
    }

    public Utente saveUser(UtenteDto utenteDto) {
        return null;
    }
}
