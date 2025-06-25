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
import java.util.ArrayList;
import java.util.List;

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
            List<String> comuniSaltati = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                line = line.replace("\uFEFF", "");

                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] tokens = line.split(";");
                if (tokens.length < 4) continue;

                String codiceProvincia = tokens[0].trim();
                String nomeComune = tokens[2].trim();

                Provincia provincia = provinciaRepository.findByCodiceProvincia(codiceProvincia).orElse(null);

                if (provincia != null) {
                    Comune comune = new Comune();
                    comune.setNome(nomeComune);
                    comune.setProvincia(provincia);

                    comuneRepository.save(comune);
                } else {
                    comuniSaltati.add(nomeComune + " (codice provincia: " + codiceProvincia + ")");
                }
            }

            if (!comuniSaltati.isEmpty()) {
                System.out.println("⚠️ Comuni NON importati:");
                comuniSaltati.forEach(System.out::println);
            } else {
                System.out.println("✅ Tutti i comuni importati correttamente.");
            }
        }
    }
}


