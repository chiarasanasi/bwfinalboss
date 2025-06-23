package it.epicode.bwfinalboss.model;

import it.epicode.bwfinalboss.enumeration.TipoCliente;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Cliente {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "ragione_sociale")
    private String ragioneSociale;
    @Column(name = "partita_iva")
    private String partitaIva;
    private String email;
    @Column(name = "data_inserimento")
    private LocalDate dataInserimento;
    @Column(name = "data_ultimo_contatto")
    private LocalDate dataUltimoContatto;
    @Column(name = "fatturato_annuale")
    private Long fatturatoAnnuale;
    private String pec;
    private String telefono;
    @Column(name = "email_contatto")
    private String emailContatto;
    @Column(name = "nome_contatto")
    private String nomeContatto;
    @Column(name = "cognome_contatto")
    private String cognomeContatto;
    @Column(name = "telefono_contatto")
    private String telefonoContatto;
    private String logoAziendaleUrl;
    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    @OneToMany(mappedBy = "cliente")
    private List<Fattura> fatture;
    @OneToMany(mappedBy = "cliente")
    private List<Indirizzo> indirizzi;
}
