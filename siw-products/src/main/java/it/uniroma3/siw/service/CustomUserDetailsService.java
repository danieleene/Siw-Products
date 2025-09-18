package it.uniroma3.siw.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private CredenzialiRepository credenzialiRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Credenziali credenziali = credenzialiRepository.findByEmail(email);
        if (credenziali == null) {
            throw new UsernameNotFoundException("Utente non trovato");
        }

        return new User(
            credenziali.getEmail(),
            credenziali.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + credenziali.getRuolo()))
        );
    }
}
