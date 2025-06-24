package it.epicode.bwfinalboss.repository;

import it.epicode.bwfinalboss.model.Provincia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, Integer> {
    Optional<Provincia> findByNome(String nome);
    Optional<Provincia> findBySigla(String sigla);
}


