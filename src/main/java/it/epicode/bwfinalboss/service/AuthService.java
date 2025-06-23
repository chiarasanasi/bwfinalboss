package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.dto.LoginDto;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.model.Utente;
import it.epicode.bwfinalboss.repository.UtenteRepository;
import it.epicode.bwfinalboss.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {


    @Autowired
    private UtenteRepository utenteRepository;
    @Autowired
    private JwtTool jwtTool;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public  String login(LoginDto loginDto) throws NotFoundException {
        Utente utente = utenteRepository.findByUsernameAndEmail((loginDto.getUsername()) , loginDto.getEmail()).orElseThrow(()->new NotFoundException("L'utente con questo username " + loginDto.getUsername() + " non trovato."));

        if((passwordEncoder.matches(loginDto.getPassword(), utente.getPassword()))){
            // l'utente è autenticato, perche username e password combaciano
            return jwtTool.createToken(utente);
        }else {
            throw  new NotFoundException("Utente con questo username/password non trovato");
        }
    }

}