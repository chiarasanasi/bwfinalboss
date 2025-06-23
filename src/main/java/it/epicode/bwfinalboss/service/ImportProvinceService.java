package it.epicode.bwfinalboss.service;

import it.epicode.bwfinalboss.model.Provincia;
import it.epicode.bwfinalboss.repository.ProvinciaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImportProvinceService {

    @Autowired
    private ProvinciaRepository provinciaRepository;

    public void importaProvince(MultipartFile file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(file.getInputStream()))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) { isFirstLine = false; continue; }

                String[] tokens = line.split(";");
                if (tokens.length < 3) continue;

                String sigla = tokens[0].trim();
                String nome = tokens[1].trim();
                String regione = tokens[2].trim();

                Provincia provincia = new Provincia();
                provincia.setSigla(sigla);
                provincia.setNome(nome);
                provincia.setRegione(regione);
                provinciaRepository.save(provincia);
            }

            System.out.println("Province importate con successo.");
        }
    }
}
