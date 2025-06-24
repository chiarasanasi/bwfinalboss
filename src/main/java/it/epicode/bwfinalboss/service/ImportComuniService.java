package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.model.Comune;
import it.epicode.bwfinalboss.model.Provincia;
import it.epicode.bwfinalboss.repository.ComuneRepository;
import it.epicode.bwfinalboss.repository.ProvinciaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ImportComuniService {

    @Autowired
    private ComuneRepository comuneRepository;

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Transactional
    public void importaComuni(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                line = line.replace("\uFEFF", "");
                System.out.println("Linea letta: " + line);

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] tokens = line.split(";");
                System.out.println("Tokens: " + java.util.Arrays.toString(tokens));

                if (tokens.length < 4) continue;

                String codiceProvincia = tokens[0].trim();
                String nomeComune = tokens[2].trim();
                String nomeProvincia = tokens[3].trim();

                Provincia provincia = provinciaRepository.findByNome(nomeProvincia).orElse(null);

                if (provincia != null) {
                    Comune comune = new Comune();
                    comune.setNome(nomeComune);
                    comune.setProvincia(provincia);

                    comuneRepository.save(comune);
                    System.out.println("Salvato comune: " + nomeComune + " (Provincia: " + nomeProvincia + ")");
                } else {
                    System.out.println("Provincia non trovata per nome: " + nomeProvincia + ", comune: " + nomeComune);
                }
            }
            System.out.println("Comuni importati con successo.");
        }
    }
}

