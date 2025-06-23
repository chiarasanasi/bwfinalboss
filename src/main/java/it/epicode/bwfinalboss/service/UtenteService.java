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
import java.util.stream.Collectors;

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


    public Utente saveUtente(UtenteDto utenteDto) throws AlreadyExistException {
        if (utenteRepository.existsByEmail(utenteDto.getEmail())) {
            throw new AlreadyExistException("Email già registrata.");
        }
        if (utenteRepository.existsByUsername(utenteDto.getUsername())) {
            throw new AlreadyExistException("Username già in uso.");
        }
//      per convertire se necessario, grazie gpt.  Set<Role> ruoli = utenteDto.getRuoli() != null && !utenteDto.getRuoli().isEmpty()
//                ? utenteDto.getRuoli().stream().map(Role::valueOf).collect(Collectors.toSet())
//                : Set.of(Role.USER);
        // Creazione oggetto Utente
        Utente utente = new Utente();
        utente.setEmail(utenteDto.getEmail());
        utente.setUsername(utenteDto.getUsername());
        utente.setNome(utenteDto.getNome());
        utente.setCognome(utenteDto.getCognome());
//        utente.setAvatar(utenteDto.getAvatar());
//        utente.setRuoli(UtenteDto.getRuoli);
        // parte della password


        return utenteRepository.save(utente);
    }
}
