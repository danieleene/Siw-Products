package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CommentoRepository;
import it.uniroma3.siw.repository.ProdottoRepository;
import it.uniroma3.siw.repository.UtenteRepository;
import java.util.Optional;

@Service
public class CommentoService {

    @Autowired
    private CommentoRepository commentoRepository;
    
    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    public Commento save(Commento commento) {
        return commentoRepository.save(commento);
    }

    public Optional<Commento> findById(Long id) {
        return commentoRepository.findById(id);
    }

    public Iterable<Commento> findAll() {
        return commentoRepository.findAll();
    }

    public void deleteById(Long id) {
        commentoRepository.deleteById(id);
    }
    
    public void aggiungiCommento(Long prodottoId, String testo, String emailUtente) {
        Prodotto prodotto = prodottoRepository.findById(prodottoId).orElseThrow();
        Utente utente = utenteRepository.findByEmail(emailUtente).orElseThrow();

        Commento commento = new Commento();
        commento.setTesto(testo);
        commento.setAutore(utente);
        commento.setProdotto(prodotto);

        commentoRepository.save(commento);
    }
    
    public void modificaCommento(Long commentoId, String nuovoTesto, String emailUtente) {
        Commento commento = commentoRepository.findById(commentoId).orElseThrow();

        if (!commento.getAutore().getEmail().equals(emailUtente)) {
            throw new AccessDeniedException("Non puoi modificare commenti altrui.");
        }

        commento.setTesto(nuovoTesto);
        commentoRepository.save(commento);
    }
    

}
