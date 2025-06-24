package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.dto.IndirizzoDto;
import it.epicode.bwfinalboss.model.Indirizzo;
import it.epicode.bwfinalboss.repository.IndirizzoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class IndirizzoService{

    private final IndirizzoRepository indirizzoRepository;

    public Indirizzo creaIndirizzo(IndirizzoDto dto) {
        Indirizzo indirizzo = Indirizzo.builder()
                .via(dto.getVia())
                .civico(dto.getCivico())
                .localita(dto.getLocalita())
                .cap(dto.getCap())
                .build();


        return indirizzoRepository.save(indirizzo);
    }
}



