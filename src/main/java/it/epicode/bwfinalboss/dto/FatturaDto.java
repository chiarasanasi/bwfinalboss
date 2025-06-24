package it.epicode.bwfinalboss.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FatturaDto {
    private LocalDate data;

    private int importo;

    private String numero;

    private String Stato;
}
