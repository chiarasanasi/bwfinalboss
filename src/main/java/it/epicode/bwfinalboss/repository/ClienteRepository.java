package it.epicode.bwfinalboss.repository;

import it.epicode.bwfinalboss.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}