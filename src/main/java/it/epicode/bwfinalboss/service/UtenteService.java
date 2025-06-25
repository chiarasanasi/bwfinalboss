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
import org.hibernate.query.Page;
import org.springframework.context.annotation.Lazy;

import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
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
        // Creazione oggetto Utente
        Utente utente = new Utente();
        utente.setEmail(utenteDto.getEmail());
        utente.setUsername(utenteDto.getUsername());
        utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));
        utente.setNome(utenteDto.getNome());
        utente.setCognome(utenteDto.getCognome());
        utente.setAvatar("https://ui-avatars.com/api/?name=" + utenteDto.getNome()+ "+" + utenteDto.getCognome());

        if (utente.getRuoli() == null || utente.getRuoli().isEmpty()) {
            utente.setRuoli(Set.of(Role.USER));
        }


        return utenteRepository.save(utente);
    }
    public Utente updateUtente(int id, UtenteDto utenteDto) throws NotFoundException, AlreadyExistException {
        Utente utenteEsistente = getUtenteById(id);


        if (!utenteEsistente.getEmail().equals(utenteDto.getEmail()) && utenteRepository.existsByEmail(utenteDto.getEmail())) {
            throw new AlreadyExistException("Email " + utenteDto.getEmail() + " già in uso da un altro utente.");
        }
        if (!utenteEsistente.getUsername().equals(utenteDto.getUsername()) && utenteRepository.existsByUsername(utenteDto.getUsername())) {
            throw new AlreadyExistException("Username " + utenteDto.getUsername() + " già in uso da un altro utente.");
        }


        utenteEsistente.setEmail(utenteDto.getEmail());
        utenteEsistente.setUsername(utenteDto.getUsername());
        utenteEsistente.setNome(utenteDto.getNome());
        utenteEsistente.setCognome(utenteDto.getCognome());
        // utenteEsistente.setAvatar(utenteDto.getAvatar());

        return utenteRepository.save(utenteEsistente);
    }
    public void deleteUtente(int id) throws NotFoundException {
        Utente utente = getUtenteById(id);
        utenteRepository.delete(utente);
    }
    public Utente addRoleToUtente(int id, Role role) throws NotFoundException {
        Utente utente = getUtenteById(id);
        Set<Role> ruoli = utente.getRuoli();
        if (ruoli == null) {
            ruoli = new java.util.HashSet<>();
        }
        ruoli.add(role);
        utente.setRuoli(ruoli);
        return utenteRepository.save(utente);
    }
    public Utente removeRoleFromUtente(int id, Role role) throws NotFoundException {
        Utente utente = getUtenteById(id);
        Set<Role> ruoli = utente.getRuoli();
        if (ruoli != null) {
            ruoli.remove(role);
            utente.setRuoli(ruoli);
        }
        return utenteRepository.save(utente);
    }
    public Utente updateUtenteAvatar(int id, String avatarUrl) throws NotFoundException {
        Utente utente = getUtenteById(id);
        utente.setAvatar(avatarUrl);
        return utenteRepository.save(utente);
    }

}
