package it.epicode.bwfinalboss.repository;

import it.epicode.bwfinalboss.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    List<Cliente> findAllByOrderByRagioneSocialeAsc();

    List<Cliente> findAllByOrderByFatturatoAnnualeDesc();

    List<Cliente> findAllByOrderByDataInserimentoAsc();

    List<Cliente> findAllByOrderByDataUltimoContattoDesc();

    List<Cliente> findByFatturatoAnnualeBetween(int min, int max);

    List<Cliente> findByDataInserimentoBetween(LocalDate start, LocalDate end);

    List<Cliente> findByDataUltimoContattoBetween(LocalDate start, LocalDate end);

    List<Cliente> findByRagioneSocialeContainingIgnoreCase(String nomeParziale);
}