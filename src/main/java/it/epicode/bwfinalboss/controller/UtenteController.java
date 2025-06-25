package it.epicode.bwfinalboss.controller;

import it.epicode.bwfinalboss.enumeration.Role;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.model.Utente;
import it.epicode.bwfinalboss.service.CloudinaryService;
import it.epicode.bwfinalboss.service.UtenteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/utenti")
@RequiredArgsConstructor
public class UtenteController {

    private final UtenteService utenteService;
    private final CloudinaryService cloudinaryService;

    @PostMapping("/{id}/avatar")
    public ResponseEntity<String> uploadAvatar(@PathVariable int id,
                                               @RequestParam("file") MultipartFile file) {
        try {
            String avatarUrl = cloudinaryService.uploadFile(file);
            utenteService.updateUtenteAvatar(id, avatarUrl);
            return ResponseEntity.ok("Avatar caricato con successo: " + avatarUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore durante l'upload: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/ruoli")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Utente> aggiornaRuoli(@PathVariable int id, @RequestBody Set<Role> nuoviRuoli) throws NotFoundException {
        Utente aggiornato = utenteService.aggiornaRuoli(id, nuoviRuoli);
        return ResponseEntity.ok(aggiornato);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Utente> getAllUtenti() {
        return utenteService.getAll();
    }
}
