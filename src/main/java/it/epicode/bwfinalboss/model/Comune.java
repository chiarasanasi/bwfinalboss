package it.epicode.bwfinalboss.model;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Comune {

    @Id
    @GeneratedValue
    private Long id;

    private int cap;

    private String nome;

    @ManyToOne
    @JoinColumn(name = "provincia_id")
    private Provincia provincia;

    @OneToMany(mappedBy = "comune")
    private Set<Indirizzo> indirizzi = new HashSet<>();
}
