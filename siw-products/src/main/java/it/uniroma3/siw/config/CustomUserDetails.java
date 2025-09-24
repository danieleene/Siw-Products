package it.uniroma3.siw.config;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.uniroma3.siw.model.Credenziali;

public class CustomUserDetails implements UserDetails {

    private final Credenziali credenziali;

    public CustomUserDetails(Credenziali credenziali) {
        this.credenziali = credenziali;
    }

    public Credenziali getCredenziali() {
        return credenziali;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + credenziali.getRuolo()));
    }

    @Override
    public String getPassword() {
        return credenziali.getPassword();
    }

    @Override
    public String getUsername() {
        return credenziali.getEmail(); // o getUsername() se usi quello
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
