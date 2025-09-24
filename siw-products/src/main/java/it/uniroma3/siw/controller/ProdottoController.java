package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.service.ProdottoService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/prodotti")
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    @Autowired
    private CommentoService commentoService;


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

   /* @GetMapping("/tipologia/{tipologia}")
    public List<Prodotto> getByTipologia(@PathVariable String tipologia) {
        return prodottoService.findByTipologia(tipologia);
    } */ 

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
    

    @GetMapping("/product/{id}")
    public String showProduct(@PathVariable Long id, Model model,
                              @AuthenticationPrincipal Credenziali credenziali) {
        Optional<Prodotto> prodottoOpt = prodottoService.findById(id);

        if (prodottoOpt.isEmpty()) {
            return "redirect:/error"; // errore!
        }

        Prodotto prodotto = prodottoOpt.get();
        model.addAttribute("prodotto", prodotto);

        // Se l'utente è autenticato e ha ruolo USER, carica il suo commento
        if (credenziali != null && credenziali.getRuolo().equals("USER")) {
            Utente utente = credenziali.getUtente();
            Optional<Commento> commento = commentoService.findByAutoreAndProdotto(utente, prodotto);
            model.addAttribute("commentoUtente", commento.orElse(null));
        }

        // Carica tutti i commenti pubblici per il prodotto
        List<Commento> tuttiCommenti = commentoService.findByProdotto(prodotto);
        model.addAttribute("tuttiCommenti", tuttiCommenti);

        return "dettaglio-prodotto";
    }
    
    @GetMapping("/admin/edit/{id}")
    public String modificaProdotto(@PathVariable Long id, Model model) {
        Optional<Prodotto> prodottoOpt = prodottoService.findById(id);
        if (prodottoOpt.isEmpty()) {
            return "redirect:/error";
        }
        model.addAttribute("prodotto", prodottoOpt.get());
        return "form-modifica-prodotto";
    }

    @PostMapping("/admin/edit/{id}")
    public String salvaModifica(@PathVariable Long id,
                                @RequestParam String descrizione,
                                @RequestParam Double prezzo) {
        Optional<Prodotto> prodottoOpt = prodottoService.findById(id);
        if (prodottoOpt.isPresent()) {
            Prodotto prodotto = prodottoOpt.get();
            prodotto.setDescrizione(descrizione);
            prodotto.setPrezzo(prezzo);
            prodottoService.save(prodotto);
        }
        return "redirect:/prodotti/product/" + id;

    }

    @GetMapping("/search")
   public String searchProductsByName(@RequestParam("name") String name, Model model) {
    List<Prodotto> prodotti = prodottoService.findByNomeContainingIgnoreCase(name);
    model.addAttribute("prodotti", prodotti);
    model.addAttribute("categoria", "Risultati per: " + name);
    return "product-list";
}



}


