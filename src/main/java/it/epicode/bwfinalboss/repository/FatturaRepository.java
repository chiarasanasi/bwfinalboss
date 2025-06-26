package it.epicode.bwfinalboss.repository;

import it.epicode.bwfinalboss.model.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, Integer> {

    List<Fattura> findByClienteId(int clienteId);
    Optional<Fattura> findByNumero(String numero);
    List<Fattura> findByData(LocalDate data);
    @Query("SELECT f FROM Fattura f WHERE YEAR(f.data) = :anno")
    List<Fattura> findByAnno(@Param("anno") int anno);
    List<Fattura> findByImportoBetween(int min, int max);
    List<Fattura> findByClienteId(Integer clienteId);
    List<Fattura> findByStato(String stato);
    List<Fattura> findByClienteIdAndStato(Integer clienteId, String stato);
}