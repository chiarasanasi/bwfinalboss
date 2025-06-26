package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.dto.IndirizzoDto;
import it.epicode.bwfinalboss.model.Cliente;
import it.epicode.bwfinalboss.model.Comune;
import it.epicode.bwfinalboss.model.Indirizzo;
import it.epicode.bwfinalboss.repository.ClienteRepository;
import it.epicode.bwfinalboss.repository.ComuneRepository;
import it.epicode.bwfinalboss.repository.IndirizzoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndirizzoService{

    @Autowired
    private IndirizzoRepository indirizzoRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ComuneRepository comuneRepository;


    public Indirizzo creaIndirizzo(IndirizzoDto indirizzoDto) {
        Indirizzo indirizzo = new Indirizzo();
        indirizzo.setVia(indirizzoDto.getVia());
        indirizzo.setCivico(indirizzoDto.getCivico());
        indirizzo.setLocalita(indirizzoDto.getLocalita());
        indirizzo.setCap(indirizzoDto.getCap());
        indirizzo.setTipo(indirizzoDto.getTipo());

        Comune comune = comuneRepository.findById(indirizzoDto.getComuneId())
                .orElseThrow(() -> new EntityNotFoundException("Comune non trovato"));
        indirizzo.setComune(comune);

        Cliente cliente = clienteRepository.findById(indirizzoDto.getClienteId())
                .orElseThrow(() -> new EntityNotFoundException("Cliente non trovato"));
        indirizzo.setCliente(cliente);


        return indirizzoRepository.save(indirizzo);
    }
}



