package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.dto.ClienteDto;
import it.epicode.bwfinalboss.dto.IndirizzoDto;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.model.Cliente;
import it.epicode.bwfinalboss.model.Comune;
import it.epicode.bwfinalboss.model.Indirizzo;
import it.epicode.bwfinalboss.model.Provincia;
import it.epicode.bwfinalboss.repository.ClienteRepository;
import it.epicode.bwfinalboss.repository.ComuneRepository;
import it.epicode.bwfinalboss.repository.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;


    public Cliente saveCliente(ClienteDto clienteDto) {

        Cliente cliente = new Cliente();
        cliente.setRagioneSociale(clienteDto.getRagioneSociale());
        cliente.setPartitaIva(clienteDto.getPartitaIva());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setDataInserimento(clienteDto.getDataInserimento());
        cliente.setDataUltimoContatto(clienteDto.getDataUltimoContatto());
        cliente.setFatturatoAnnuale(clienteDto.getFatturatoAnnuale());
        cliente.setPec(clienteDto.getPec());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setEmailContatto(clienteDto.getEmailContatto());
        cliente.setNomeContatto(clienteDto.getNomeContatto());
        cliente.setCognomeContatto(clienteDto.getCognomeContatto());
        cliente.setTelefonoContatto(clienteDto.getTelefonoContatto());
        String ragioneSocialeEnc = URLEncoder.encode(cliente.getRagioneSociale(), StandardCharsets.UTF_8);
        cliente.setLogoAziendaleUrl("https://ui-avatars.com/api/?name=" + ragioneSocialeEnc);

        cliente.setTipoCliente(clienteDto.getTipoCliente());

        if (clienteDto.getIndirizzi() != null && !clienteDto.getIndirizzi().isEmpty()) {
            List<Indirizzo> indirizzi = new ArrayList<>();

            for (IndirizzoDto indDto : clienteDto.getIndirizzi()) {
                Indirizzo ind = new Indirizzo();
                ind.setVia(indDto.getVia());
                ind.setCivico(indDto.getCivico());
                ind.setLocalita(indDto.getLocalita());
                ind.setCap(indDto.getCap());
                ind.setTipo(indDto.getTipo());

                Provincia provincia = provinciaRepository.findBySigla(indDto.getProvinciaSigla())
                        .orElseThrow(() -> new RuntimeException("Provincia non trovata: " + indDto.getProvinciaSigla()));

                Comune comune = comuneRepository.findByNomeAndProvincia(indDto.getComune(), provincia)
                        .orElseThrow(() -> new RuntimeException("Comune non trovato: " + indDto.getComune() + " in provincia " + indDto.getProvinciaSigla()));

                ind.setComune(comune);

                ind.setCliente(cliente);

                indirizzi.add(ind);
            }

            cliente.setIndirizzi(indirizzi);
        }

        Cliente savedCliente = clienteRepository.save(cliente);
        System.out.println("Indirizzi salvati: " + savedCliente.getIndirizzi().size());

        return savedCliente;
    }


    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(int id) throws NotFoundException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con id " + id));
    }

    public Cliente updateCliente(int id, ClienteDto clienteDto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente non trovato con id: " + id));

        cliente.setRagioneSociale(clienteDto.getRagioneSociale());
        cliente.setPartitaIva(clienteDto.getPartitaIva());
        cliente.setEmail(clienteDto.getEmail());
        cliente.setDataInserimento(clienteDto.getDataInserimento());
        cliente.setDataUltimoContatto(clienteDto.getDataUltimoContatto());
        cliente.setFatturatoAnnuale(clienteDto.getFatturatoAnnuale());
        cliente.setPec(clienteDto.getPec());
        cliente.setTelefono(clienteDto.getTelefono());
        cliente.setEmailContatto(clienteDto.getEmailContatto());
        cliente.setNomeContatto(clienteDto.getNomeContatto());
        cliente.setCognomeContatto(clienteDto.getCognomeContatto());
        cliente.setTelefonoContatto(clienteDto.getTelefonoContatto());
        cliente.setLogoAziendaleUrl(clienteDto.getLogoAziendaleUrl());
        cliente.setTipoCliente(clienteDto.getTipoCliente());

        if (clienteDto.getIndirizzi() != null && !clienteDto.getIndirizzi().isEmpty()) {
            List<Indirizzo> indirizzi = new ArrayList<>();
            for (IndirizzoDto indDto : clienteDto.getIndirizzi()) {
                Indirizzo ind = new Indirizzo();
                ind.setVia(indDto.getVia());
                ind.setCivico(indDto.getCivico());
                ind.setLocalita(indDto.getLocalita());
                ind.setCap(indDto.getCap());
                ind.setTipo(indDto.getTipo());

                Provincia provincia = provinciaRepository.findBySigla(indDto.getProvinciaSigla())
                        .orElseThrow(() -> new RuntimeException("Provincia non trovata: " + indDto.getProvinciaSigla()));

                Comune comune = comuneRepository.findByNomeAndProvincia(indDto.getComune(), provincia)
                        .orElseThrow(() -> new RuntimeException("Comune non trovato: " + indDto.getComune() + " in provincia " + indDto.getProvinciaSigla()));

                ind.setComune(comune);
                ind.setCliente(cliente);
                indirizzi.add(ind);
            }
            cliente.setIndirizzi(indirizzi);
        } else {

            cliente.getIndirizzi().clear();
        }

        return clienteRepository.save(cliente);
    }



    public void deleteCliente(int id) throws NotFoundException {
        Cliente cliente = findById(id);
        clienteRepository.delete(cliente);
    }

    public List<Cliente> getClientiOrdinatiPer(String criterio) {
        return switch (criterio.toLowerCase()) {
            case "nome" -> clienteRepository.findAllByOrderByRagioneSocialeAsc();
            case "fatturato" -> clienteRepository.findAllByOrderByFatturatoAnnualeDesc();
            case "data_inserimento" -> clienteRepository.findAllByOrderByDataInserimentoAsc();
            case "ultimo_contatto" -> clienteRepository.findAllByOrderByDataUltimoContattoDesc();
            default -> throw new IllegalArgumentException("Criterio di ordinamento non valido: " + criterio);
        };
    }

    public List<Cliente> filtraPerFatturato(int min, int max) {
        return clienteRepository.findByFatturatoAnnualeBetween(min, max);
    }

    public List<Cliente> filtraPerDataInserimento(LocalDate start, LocalDate end) {
        return clienteRepository.findByDataInserimentoBetween(start, end);
    }

    public List<Cliente> filtraPerUltimoContatto(LocalDate start, LocalDate end) {
        return clienteRepository.findByDataUltimoContattoBetween(start, end);
    }

    public List<Cliente> filtraPerNome(String nomeParziale) {
        return clienteRepository.findByRagioneSocialeContainingIgnoreCase(nomeParziale);
    }


}
