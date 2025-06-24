package it.epicode.bwfinalboss.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Builder
public class FatturaDto {
    @NotNull(message = "Data è obbligatoria")
    private LocalDate data;
    @NotNull(message = "importo è obbligatoria")
    private int importo;
    @NotNull(message = "numero è obbligatoria")
    private String numero;
    @NotNull(message = "stato è obbligatoria")
    private String stato;
    private int clienteId;
}
