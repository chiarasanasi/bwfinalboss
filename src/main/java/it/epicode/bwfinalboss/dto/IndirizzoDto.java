package it.epicode.bwfinalboss.dto;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndirizzoDto {

    private Long id;
    private String via;
    private String civico;
    private String localita;
    private String cap;
   // private Long comuneId;
    //private Long clienteId;
}
