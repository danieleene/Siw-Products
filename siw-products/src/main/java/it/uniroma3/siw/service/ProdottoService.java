package it.uniroma3.siw.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.repository.ProdottoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProdottoService {

    @Autowired
    private ProdottoRepository prodottoRepository;

    public Iterable<Prodotto> findAll() {
        return prodottoRepository.findAll();
    }

    public Optional<Prodotto> findById(Long id) {
        return prodottoRepository.findById(id);
    }

    public List<Prodotto> findByNome(String nome) {
        return prodottoRepository.findByNomeContainingIgnoreCase(nome);
    }

    public List<Prodotto> findByTipologia(String tipologia) {
        return prodottoRepository.findByTipologia(tipologia);
    }

    public Prodotto save(Prodotto prodotto) {
        return prodottoRepository.save(prodotto);
    }

    public void deleteById(Long id) {
        prodottoRepository.deleteById(id);
    }
    
    public void update(Prodotto prodotto) {
        Prodotto esistente = prodottoRepository.findById(prodotto.getId()).orElseThrow();

        esistente.setNome(prodotto.getNome());
        esistente.setDescrizione(prodotto.getDescrizione());
        esistente.setPrezzo(prodotto.getPrezzo());
        esistente.setTipologia(prodotto.getTipologia());

        prodottoRepository.save(esistente);
    }
    
    public void aggiungiSimile(Long idProdotto, Long idSimile) {
        Prodotto principale = prodottoRepository.findById(idProdotto).orElseThrow();
        Prodotto simile = prodottoRepository.findById(idSimile).orElseThrow();

        principale.getProdottiSimili().add(simile);
        prodottoRepository.save(principale);
    }
    
    public void rimuoviSimile(Long idProdotto, Long idSimile) {
        Prodotto principale = prodottoRepository.findById(idProdotto).orElseThrow();
        Prodotto simile = prodottoRepository.findById(idSimile).orElseThrow();

        principale.getProdottiSimili().remove(simile);
        prodottoRepository.save(principale);
    }

    public List<Prodotto> findByNomeContainingIgnoreCase(String nome) {
    return prodottoRepository.findByNomeContainingIgnoreCase(nome);
}

}

