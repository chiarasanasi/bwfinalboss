package it.epicode.bwfinalboss.model;

import it.epicode.bwfinalboss.enumeration.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utente implements UserDetails {
    @Id
    @GeneratedValue
    private int id;

    private String email;

    @Column(unique = true)
    private String username;

    private String password;
    private String nome;
    private String cognome;
    private String avatar;


    @ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
    @CollectionTable(
            name = "utente_ruolo",
            joinColumns = @JoinColumn(name = "utente_id")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "ruolo")
    private Set<Role> ruoli = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return ruoli.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



}
