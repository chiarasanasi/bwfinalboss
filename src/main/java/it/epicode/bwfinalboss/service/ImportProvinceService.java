package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.model.Provincia;
import it.epicode.bwfinalboss.repository.ProvinciaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class ImportProvinceService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    @Transactional
    public void importaProvince(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;

            int counter = 1;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                String[] tokens = line.split(";");
                if (tokens.length < 3) continue;

                String sigla = tokens[0].trim();
                String nomeProvincia = tokens[1].trim();
                String regione = tokens[2].trim();

                Provincia provincia = new Provincia();
                provincia.setSigla(sigla);
                provincia.setNome(nomeProvincia);
                provincia.setRegione(regione);

                provincia.setCodiceProvincia(String.format("%03d", counter++));

                provinciaRepository.save(provincia);
                System.out.println("✅ Salvata provincia: " + nomeProvincia + " (" + sigla + ")");
            }
            System.out.println("🎉 Importazione province completata con successo.");
        }
    }
}


