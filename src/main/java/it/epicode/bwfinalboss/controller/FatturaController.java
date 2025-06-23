package it.epicode.bwfinalboss.controller;

import it.epicode.bwfinalboss.dto.FatturaDto;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.service.FatturaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fatture")
@RequiredArgsConstructor
public class FatturaController {

    private final FatturaService fatturaService;

    @PostMapping
    public ResponseEntity<String> creaFattura(@RequestBody FatturaDto dto) {
        try {
            String response = fatturaService.creaFattura(dto);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<FatturaDto> getFattura(@PathVariable long id) {
        try {
            FatturaDto dto = fatturaService.getFattura(id);
            return ResponseEntity.ok(dto);
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<FatturaDto>> getFattureCliente(@PathVariable Long clienteId) {
        List<FatturaDto> dtos = fatturaService.getFattureCliente(clienteId);
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminaFattura(@PathVariable Long id) {
        try {
            fatturaService.eliminaFattura(id);
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}