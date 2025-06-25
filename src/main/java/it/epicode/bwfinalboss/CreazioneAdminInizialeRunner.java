package it.epicode.bwfinalboss;

import it.epicode.bwfinalboss.enumeration.Role;
import it.epicode.bwfinalboss.model.Utente;
import it.epicode.bwfinalboss.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CreazioneAdminInizialeRunner implements CommandLineRunner {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (utenteRepository.findByUsernameAndEmail("vincenzo", "vincenzo@gmail.com").isEmpty()) {
            Utente admin = new Utente();
            admin.setNome("Vincenzo");
            admin.setCognome("Napoli");
            admin.setUsername("vincenzo");
            admin.setEmail("vincenzo@gmail.com");
            admin.setPassword(passwordEncoder.encode("a1234"));
            admin.setRuoli(Set.of(Role.USER, Role.ADMIN));
            admin.setAvatar("https://ui-avatars.com/api/?name=Vincenzo+Napoli");

            utenteRepository.save(admin);
            System.out.println("Utente admin creato con successo.");
        } else {
            System.out.println("Utente admin già presente.");
        }

    }
}
