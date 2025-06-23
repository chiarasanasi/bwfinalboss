package it.epicode.bwfinalboss.controller;

import it.epicode.bwfinalboss.service.ImportComuniService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/comuni")
@RequiredArgsConstructor
public class ComuneController {

    private final ImportComuniService importComuniService;

    @PostMapping("/import")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> importaComuni(@RequestParam("file") MultipartFile file) {
        try {
            importComuniService.importaComuni(file);
            return ResponseEntity.ok("Comuni importati con successo.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore nell'importazione dei comuni: " + e.getMessage());
        }
    }
}
