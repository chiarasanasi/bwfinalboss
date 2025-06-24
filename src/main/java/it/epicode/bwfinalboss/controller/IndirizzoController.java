package it.epicode.bwfinalboss.controller;



import it.epicode.bwfinalboss.dto.IndirizzoDto;
import it.epicode.bwfinalboss.model.Indirizzo;
import it.epicode.bwfinalboss.service.IndirizzoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/indirizzi")
@RequiredArgsConstructor
public class IndirizzoController {

    private final IndirizzoService indirizzoService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping("")
    public ResponseEntity<Indirizzo> creaIndirizzo(@RequestBody @Validated IndirizzoDto dto) {
        Indirizzo nuovoIndirizzo = indirizzoService.creaIndirizzo(dto);
        return ResponseEntity.ok(nuovoIndirizzo);
    }

}
