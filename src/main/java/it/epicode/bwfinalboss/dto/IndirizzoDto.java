package it.epicode.bwfinalboss.dto;


import it.epicode.bwfinalboss.enumeration.TipoIndirizzo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndirizzoDto {
    @NotBlank(message = "via è obbligatoria")
    private String via;
    @NotBlank(message = "civico è obbligatoria")
    private String civico;
    @NotBlank(message = "localita è obbligatoria")
    private String localita;
    @NotBlank(message = "cap è obbligatoria")
    private String cap;
    private TipoIndirizzo tipo;
    private int comuneId;
    private String provinciaSigla;
    private String comune;
   // private Long comuneId;
    //private Long clienteId;
}
