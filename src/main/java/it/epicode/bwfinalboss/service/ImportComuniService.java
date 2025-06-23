package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.model.Comune;
import it.epicode.bwfinalboss.model.Provincia;
import it.epicode.bwfinalboss.repository.ComuneRepository;
import it.epicode.bwfinalboss.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImportComuniService {

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public void importaComuni(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }

                String[] tokens = line.split(";");
                if (tokens.length < 4) continue;

                String codiceProvincia = tokens[0].trim();
                String nomeComune = tokens[2].trim();
                String nomeProvincia = tokens[3].trim();

                Provincia provincia = provinciaRepository.findBySigla(codiceProvincia).orElse(null);

                if (provincia != null) {
                    Comune comune = new Comune();
                    comune.setNome(nomeComune);
                    comune.setProvincia(provincia);

                    comuneRepository.save(comune);
                }
            }

            System.out.println("Comuni importati con successo.");
        }
    }
}
