package it.uniroma3.siw.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.repository.CredenzialiRepository;
import java.util.Optional;

@Service
public class CredenzialiService {

    @Autowired
    private CredenzialiRepository credenzialiRepository;

    public Credenziali save(Credenziali credenziali) {
        return credenzialiRepository.save(credenziali);
    }

    public Optional<Credenziali> findById(Long id) {
        return credenzialiRepository.findById(id);
    }

    public Credenziali findByEmail(String email) {
        return credenzialiRepository.findByEmail(email);
    }

    public Iterable<Credenziali> findAll() {
        return credenzialiRepository.findAll();
    }

    public void deleteById(Long id) {
        credenzialiRepository.deleteById(id);
    }
}
