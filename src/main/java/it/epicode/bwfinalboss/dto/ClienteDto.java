package it.epicode.bwfinalboss.dto;

import it.epicode.bwfinalboss.enumeration.TipoCliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ClienteDto {
    @NotBlank(message = "Ragione sociale è obbligatoria")
    private String ragioneSociale;
    @NotBlank(message = "Partita IVA è obbligatoria")
    private String partitaIva;
    @NotBlank(message = "Email è obbligatoria")
    @Email(message = "Email non valida")
    private String email;
    @NotNull(message = "Data inserimento è obbligatoria")
    private LocalDate dataInserimento;
    @NotNull(message = "Data ultimo contatto è obbligatoria")
    private LocalDate dataUltimoContatto;
    @NotNull(message = "Fatturato annuale è obbligatorio")
    private Long fatturatoAnnuale;
    @Email(message = "PEC non valida")
    private String pec;
    @NotBlank(message = "Telefono è obbligatorio")
    private String telefono;
    @Email(message = "Email contatto non valida")
    private String emailContatto;
    @NotBlank(message = "Nome contatto è obbligatorio")
    private String nomeContatto;
    @NotBlank(message = "Cognome contatto è obbligatorio")
    private String cognomeContatto;
    @NotBlank(message = "Telefono contatto è obbligatorio")
    private String telefonoContatto;
    private String logoAziendaleUrl;
    @NotBlank(message = "Tipo cliente è obbligatorio")
    private TipoCliente tipoCliente;
    // Se vuoi che il client possa inviare o ricevere non solo i dati base del cliente
    // ma anche gli indirizzi collegati, allora devi includere questa lista.
    private List<IndirizzoDto> indirizzi;
}
