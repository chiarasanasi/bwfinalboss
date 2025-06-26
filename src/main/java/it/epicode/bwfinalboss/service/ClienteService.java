package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.dto.ClienteDto;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.model.Cliente;
import it.epicode.bwfinalboss.model.Indirizzo;
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
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Autowired
    private EmailService emailService;

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

        Cliente savedCliente = clienteRepository.save(cliente);
        emailService.inviaBenvenuto(savedCliente);


        return savedCliente;
    }

    public List<Cliente> findAll() {
        List<Cliente> clienti = clienteRepository.findAll();
        for (Cliente c : clienti) {
            c.setIndirizzi(new ArrayList<>(c.getIndirizzi()));
        }
        return clienti;
    }

    public Cliente findById(int id) throws NotFoundException {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con id " + id));
        cliente.setIndirizzi(new ArrayList<>(cliente.getIndirizzi()));
        return cliente;
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

        Cliente updatedCliente = clienteRepository.save(cliente);
        updatedCliente.setIndirizzi(new ArrayList<>(updatedCliente.getIndirizzi()));
        return updatedCliente;
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
