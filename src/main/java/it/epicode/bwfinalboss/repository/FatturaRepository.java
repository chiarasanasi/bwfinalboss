package it.epicode.bwfinalboss.repository;

import it.epicode.bwfinalboss.model.Fattura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, Integer> {
    List<Fattura> findByClienteId(Integer clienteId);
    List<Fattura> findByStato(String stato);
    List<Fattura> findByClienteIdAndStato(Integer clienteId, String stato);

    List<Fattura> findByClienteId(int clienteId);

    Optional<Fattura> findByNumero(String numero);
}
