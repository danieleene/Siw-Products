package it.uniroma3.siw.repository;


import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Commento;


public interface CommentoRepository extends CrudRepository<Commento, Long> {
   Optional<Commento> findByAutoreAndProdotto(Utente autore, Prodotto prodotto);

    void deleteByAutoreAndProdotto(Utente autore, Prodotto prodotto);
    List<Commento> findByProdotto(Prodotto prodotto);
}

