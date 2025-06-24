package it.epicode.bwfinalboss.service;

import com.cloudinary.Cloudinary;
import it.epicode.bwfinalboss.dto.ClienteDto;
import it.epicode.bwfinalboss.exception.NotFoundException;
import it.epicode.bwfinalboss.model.Cliente;
import it.epicode.bwfinalboss.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private Cloudinary cloudinary;

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
        cliente.setLogoAziendaleUrl(clienteDto.getLogoAziendaleUrl());
        cliente.setTipoCliente(clienteDto.getTipoCliente());

//        if (clienteDto.getIndirizzi() != null) {
//            List<Indirizzo> indirizzi = clienteDto.getIndirizzi().stream()
//                    .map(iDto -> {
//                        Indirizzo ind = new Indirizzo();
//                        ind.setVia(iDto.getVia());
//                        ind.setCivico(iDto.getCivico());
//                        ind.setLocalita(iDto.getLocalita());
//                        ind.setCap(iDto.getCap());
//                        ind.setComune(iDto.getComune());
//                        ind.setTipo(iDto.getTipo());
//                        ind.setCliente(cliente);
//                        return ind;
//                    }).toList();
//            cliente.setIndirizzi(indirizzi);
//        }

        return clienteRepository.save(cliente);
    }

    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    public Cliente findById(int id) throws NotFoundException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente non trovato con id " + id));
    }

    public Cliente updateCliente(int id, ClienteDto clienteDto) throws NotFoundException {
        Cliente cliente = findById(id);

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

//        if (clienteDto.getIndirizzi() != null) {
//            cliente.getIndirizzi().clear();
//            List<Indirizzo> indirizzi = clienteDto.getIndirizzi().stream()
//                    .map(iDto -> {
//                        Indirizzo ind = new Indirizzo();
//                        ind.setVia(iDto.getVia());
//                        ind.setCivico(iDto.getCivico());
//                        ind.setLocalita(iDto.getLocalita());
//                        ind.setCap(iDto.getCap());
//                        ind.setComune(iDto.getComune());
//                        ind.setTipo(iDto.getTipo());
//                        ind.setCliente(cliente);
//                        return ind;
//                    })
//                    .collect(Collectors.toList());
//            cliente.setIndirizzi(indirizzi);
//        }

        return clienteRepository.save(cliente);
    }

    public void deleteCliente(int id) throws NotFoundException {
        Cliente cliente = findById(id);
        clienteRepository.delete(cliente);
    }
}
