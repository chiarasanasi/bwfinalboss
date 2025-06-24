package it.epicode.bwfinalboss.controller;

import it.epicode.bwfinalboss.service.ImportComuniService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/import/comuni")
public class ComuneController {

    @Autowired
    private ImportComuniService importComuniService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> importaComuni(@RequestParam("file") MultipartFile file) {
        try {
            importComuniService.importaComuni(file);
            return ResponseEntity.ok("Comuni importati con successo.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Errore durante l'importazione: " + e.getMessage());
        }
    }
}

