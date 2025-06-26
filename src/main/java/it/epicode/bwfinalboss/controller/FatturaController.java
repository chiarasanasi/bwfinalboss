package it.epicode.bwfinalboss.controller;

import it.epicode.bwfinalboss.dto.ClienteDto;
import it.epicode.bwfinalboss.dto.FatturaDto;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.model.Cliente;
import it.epicode.bwfinalboss.model.Fattura;
import it.epicode.bwfinalboss.repository.FatturaRepository;
import it.epicode.bwfinalboss.service.FatturaService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/fatture")
@RequiredArgsConstructor
public class FatturaController {

    @Autowired
    private FatturaService fatturaService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("")
    public Fattura creaFattura(@RequestBody @Validated FatturaDto fatturaDto, BindingResult bindingResult) throws ValidationException, NotFoundException {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors()
                    .stream()
                    .map(e -> e.getDefaultMessage())
                    .reduce("", (s1, s2) -> s1 + " " + s2));
        }
        return fatturaService.creaFattura(fatturaDto);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("")
    public List<Fattura> getFatture() {
        return fatturaService.findAll();
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{clienteId}")
    public ResponseEntity<List<FatturaDto>> getFattureCliente(@PathVariable int clienteId) {
        List<FatturaDto> dtos = fatturaService.getFattureCliente(clienteId);
        return ResponseEntity.ok(dtos);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaFattura(@PathVariable int id) {
        try {
            fatturaService.eliminaFattura(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/data")
    public ResponseEntity<List<Fattura>> getFattureByData(@RequestParam("data") String data) {
        LocalDate parsedDate = LocalDate.parse(data);
        return ResponseEntity.ok(fatturaService.getFattureByData(parsedDate));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/anno")
    public ResponseEntity<List<Fattura>> getFattureByAnno(@RequestParam("anno") int anno) {
        return ResponseEntity.ok(fatturaService.getFattureByAnno(anno));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/importo")
    public ResponseEntity<List<Fattura>> getFattureByImportoRange(
            @RequestParam("min") int min,
            @RequestParam("max") int max) {
        return ResponseEntity.ok(fatturaService.getFattureByImportoRange(min, max));
    }
}