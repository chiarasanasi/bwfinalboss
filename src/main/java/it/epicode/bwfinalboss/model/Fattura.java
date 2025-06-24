package it.epicode.bwfinalboss.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "fatture")
@NoArgsConstructor
@AllArgsConstructor
public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate data;

    private int importo;

    private String numero;

    @Column(length = 60)
    private String stato;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

}
