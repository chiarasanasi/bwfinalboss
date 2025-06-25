package it.epicode.bwfinalboss.controller;

import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.model.Provincia;
import it.epicode.bwfinalboss.service.ImportProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/province")
public class ProvinciaController {

    @Autowired
    private ImportProvinceService importProvinceService;

    @PostMapping("/import")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> importaProvince(@RequestParam("file") MultipartFile file) {
        try {
            importProvinceService.importaProvince(file);
            return ResponseEntity.ok("Province importate con successo.");
        } catch (Exception e) {
            return ResponseEntity.status(500)
                    .body("Errore nell'importazione delle province: " + e.getMessage());
        }
    }
}
