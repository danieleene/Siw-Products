package it.uniroma3.siw.repository;

import org.springframework.data.repository.CrudRepository;
import it.uniroma3.siw.model.Prodotto;
import java.util.List;

public interface ProdottoRepository extends CrudRepository<Prodotto, Long> {
	
    List<Prodotto> findByNomeContainingIgnoreCase(String nome);
    List<Prodotto> findByTipologia(String tipologia);
}