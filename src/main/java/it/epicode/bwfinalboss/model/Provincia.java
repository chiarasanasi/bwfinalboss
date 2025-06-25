package it.epicode.bwfinalboss.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Provincia {

    @Id
    @GeneratedValue
    private int id;

    private String sigla;

    private String nome;

    private String regione;

    @Column(name = "codice_provincia", unique = true)
    private String codiceProvincia;

    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL)
    private Set<Comune> comuni = new HashSet<>();
}
