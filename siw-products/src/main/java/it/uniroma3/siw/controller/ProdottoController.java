package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.service.ProdottoService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/prodotti")
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    @GetMapping
    public Iterable<Prodotto> getAllProdotti() {
        return prodottoService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Prodotto> getProdotto(@PathVariable Long id) {
        return prodottoService.findById(id);
    }

    @GetMapping("/nome/{nome}")
    public List<Prodotto> getByNome(@PathVariable String nome) {
        return prodottoService.findByNome(nome);
    }

    @GetMapping("/tipologia/{tipologia}")
    public List<Prodotto> getByTipologia(@PathVariable String tipologia) {
        return prodottoService.findByTipologia(tipologia);
    }

    @PostMapping
    public Prodotto createProdotto(@RequestBody Prodotto prodotto) {
        return prodottoService.save(prodotto);
    }

    @PutMapping("/{id}")
    public Prodotto updateProdotto(@PathVariable Long id, @RequestBody Prodotto prodotto) {
        prodotto.setId(id);
        return prodottoService.save(prodotto);
    }

    @DeleteMapping("/{id}")
    public void deleteProdotto(@PathVariable Long id) {
        prodottoService.deleteById(id);
    }
}
