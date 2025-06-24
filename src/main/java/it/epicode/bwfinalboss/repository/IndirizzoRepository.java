package it.epicode.bwfinalboss.repository;


import it.epicode.bwfinalboss.model.Indirizzo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndirizzoRepository extends JpaRepository<Indirizzo, Long> {
}
