package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.dto.FatturaDto;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.model.Fattura;
import it.epicode.bwfinalboss.repository.FatturaRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Data
public class FatturaService {

    private final FatturaRepository fatturaRepository;

    public String creaFattura(FatturaDto dto) {
        Fattura fattura = new Fattura();
        fattura.setData(dto.getData());
        fattura.setImporto(dto.getImporto());
        fattura.setNumero(dto.getNumero());
        fattura.setStato(dto.getStato());


        fatturaRepository.save(fattura);
        return "Fattura salvata con successo!";
    }

    public FatturaDto getFattura(long id) throws NotFoundException {
        Fattura fattura = fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura non trovata con id: " + id));
        return convertToDto(fattura);
    }

    public List<FatturaDto> getFattureCliente(Long clienteId) {
        List<Fattura> fatture = fatturaRepository.findByClienteId(clienteId);
        return fatture.stream()
                .map(this::convertToDto)
                .toList();
    }

    public void eliminaFattura(Long id) throws NotFoundException {
        if (!fatturaRepository.existsById(id)) {
            throw new NotFoundException("Fattura non trovata con id: " + id);
        }
        fatturaRepository.deleteById(id);
    }

    private FatturaDto convertToDto(Fattura f) {
        FatturaDto dto = new FatturaDto();
        dto.setData(f.getData());
        dto.setImporto(f.getImporto());
        dto.setNumero(f.getNumero());
        dto.setStato(f.getStato());
        return dto;
    }
}