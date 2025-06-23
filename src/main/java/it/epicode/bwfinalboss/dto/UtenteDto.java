package it.epicode.bwfinalboss.dto;

import it.epicode.bwfinalboss.enumeration.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtenteDto {
    private String email;
    private String username;
    private String password;
    private String nome;
    private String cognome;
    private String avatar;
    private Set<String> ruoli;
}
