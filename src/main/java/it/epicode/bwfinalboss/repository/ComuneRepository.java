package it.epicode.bwfinalboss.repository;

import it.epicode.bwfinalboss.model.Comune;
import it.epicode.bwfinalboss.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ComuneRepository extends JpaRepository<Comune, Long> {
    Optional<Comune> findByNomeAndProvincia(String nome, Provincia provincia);
}
