package it.uniroma3.siw.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.UtenteRepository;

import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    public Utente save(Utente utente) {
        return utenteRepository.save(utente);
    }

    public Optional<Utente> findById(Long id) {
        return utenteRepository.findById(id);
    }

    public Iterable<Utente> findAll() {
        return utenteRepository.findAll();
    }

    public void deleteById(Long id) {
        utenteRepository.deleteById(id);
    }
}
