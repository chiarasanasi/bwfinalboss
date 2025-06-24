package it.epicode.bwfinalboss.controller;



import it.epicode.bwfinalboss.dto.IndirizzoDto;
import it.epicode.bwfinalboss.model.Indirizzo;
import it.epicode.bwfinalboss.service.IndirizzoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/indirizzi")
@RequiredArgsConstructor
public class IndirizzoController {

    private final IndirizzoService indirizzoService;

    @PostMapping
    public ResponseEntity<Indirizzo> creaIndirizzo(@RequestBody IndirizzoDto dto) {
        Indirizzo nuovoIndirizzo = indirizzoService.creaIndirizzo(dto);
        return ResponseEntity.ok(nuovoIndirizzo);
    }

}
