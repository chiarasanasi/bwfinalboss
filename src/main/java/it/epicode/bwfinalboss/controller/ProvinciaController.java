package it.epicode.bwfinalboss.controller;

import it.epicode.bwfinalboss.service.ImportProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/province")
public class ProvinciaController {

    @Autowired
    private ImportProvinceService importProvinceService;

    @PostMapping("/import")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> importaProvince(@RequestParam("file") MultipartFile file) {
        try {
            importProvinceService.importaProvince(file);
            return ResponseEntity.ok("Province importate con successo.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore nell'importazione delle province: " + e.getMessage());
        }
    }
}
