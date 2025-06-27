package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.dto.FatturaDto;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.model.Cliente;
import it.epicode.bwfinalboss.model.Fattura;
import it.epicode.bwfinalboss.repository.ClienteRepository;
import it.epicode.bwfinalboss.repository.FatturaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Data
public class FatturaService {

    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @PersistenceContext
    private EntityManager em;

    public Fattura creaFattura(FatturaDto dto) throws NotFoundException {
        Cliente cliente = clienteRepository.findById(dto.getClienteId()).orElseThrow(() -> new NotFoundException("Cliente non trovato"));
        Fattura fattura = new Fattura();
        fattura.setData(dto.getData());
        fattura.setImporto(dto.getImporto());
        fattura.setNumero(dto.getNumero());
        fattura.setStato(dto.getStato());
        fattura.setCliente(cliente);

        return fatturaRepository.save(fattura);
    }

    public List<Fattura> findAll() {
        return fatturaRepository.findAll();
    }

    public List<FatturaDto> getFattureCliente(int clienteId) {
        List<Fattura> fatture = fatturaRepository.findByClienteId(clienteId);
        return fatture.stream()
                .map(this::convertToDto)
                .toList();
    }

    @Transactional
    public void eliminaFattura(int id) throws NotFoundException {
        Fattura f = fatturaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Fattura non trovata con id: " + id));

        f.setCliente(null);


        Fattura managedFattura = em.merge(f);
        em.remove(managedFattura);
    }

    private FatturaDto convertToDto(Fattura f) {
        FatturaDto dto = new FatturaDto();
        dto.setData(f.getData());
        dto.setImporto(f.getImporto());
        dto.setNumero(f.getNumero());
        dto.setStato(f.getStato());
        return dto;
    }

    public List<Fattura> getFattureByData(LocalDate data) {
        return fatturaRepository.findByData(data);
    }

    public List<Fattura> getFattureByAnno(int anno) {
        return fatturaRepository.findByAnno(anno);
    }

    public List<Fattura> getFattureByImportoRange(int min, int max) {
        return fatturaRepository.findByImportoBetween(min, max);
    }

    public List<Fattura> filtraFatture(Integer clienteId, String stato) {
        if (clienteId != null && stato != null) {
            return fatturaRepository.findByClienteIdAndStato(clienteId, stato);
        } else if (clienteId != null) {
            return fatturaRepository.findByClienteId(clienteId);
        } else if (stato != null) {
            return fatturaRepository.findByStato(stato);
        } else {
            return fatturaRepository.findAll();
        }
    }

}