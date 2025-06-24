package it.epicode.bwfinalboss.model;

import jakarta.persistence.*;
import jdk.jfr.Unsigned;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Provincia {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String sigla;
    private String nome;
    private String regione;

    @OneToMany(mappedBy = "provincia", cascade = CascadeType.ALL)
    private Set<Comune> comuni = new HashSet<>();
}


