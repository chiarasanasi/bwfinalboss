package it.epicode.bwfinalboss.model;

import jakarta.persistence.*;

@Entity
public class Indirizzo {

    @Id
    @GeneratedValue
    private int id;

    private String via;
    private String civico;
    private String cap;
    private String localita;

    @ManyToOne
    @JoinColumn(name = "comune_id")
    private Comune comune;
}
