package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CommentoService;
import it.uniroma3.siw.service.ProdottoService;
import it.uniroma3.siw.service.UtenteService;
import java.util.Optional;

@Controller
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;
    
    @Autowired
    private CommentoService commentoService;
    
    @Autowired
    private ProdottoService prodottoService;

    @PostMapping("/create")
    public @ResponseBody Utente createUtente(@RequestBody Utente utente) {
        return utenteService.save(utente);
    }

    @GetMapping("/{id}")
    public @ResponseBody Optional<Utente> getUtente(@PathVariable Long id) {
        return utenteService.findById(id);
    }

    @GetMapping("/all")
    public @ResponseBody Iterable<Utente> getAllUtenti() {
        return utenteService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public @ResponseBody void deleteUtente(@PathVariable Long id) {
        utenteService.deleteById(id);
    }
    
    @PostMapping("/commenta")
    @PreAuthorize("hasRole('USER')")
    public String commentaProdotto(@RequestParam Long prodottoId,
                                    @RequestParam String testo,
                                    Authentication auth) {
        commentoService.aggiungiCommento(prodottoId, testo, auth.getName());
        return "redirect:/prodotto/" + prodottoId;
    }
    
    @PostMapping("/modifica-commento")
    @PreAuthorize("hasRole('USER')")
    public String modificaCommento(@RequestParam Long commentoId,
                                   @RequestParam String nuovoTesto,
                                   Authentication auth) {
        commentoService.modificaCommento(commentoId, nuovoTesto, auth.getName());
        return "redirect:/utente/dashboard";
    }
    
    @PostMapping("/aggiungi-prodotto")
    @PreAuthorize("hasRole('ADMIN')")
    public String aggiungiProdotto(@ModelAttribute Prodotto prodotto) {
        prodottoService.save(prodotto);
        return "redirect:/utente/dashboard";
    }
    
    @PostMapping("/aggiorna-prodotto")
    @PreAuthorize("hasRole('ADMIN')")
    public String aggiornaProdotto(@ModelAttribute Prodotto prodotto) {
        prodottoService.update(prodotto);
        return "redirect:/utente/dashboard";
    }
    
    @PostMapping("/rimuovi-prodotto")
    @PreAuthorize("hasRole('ADMIN')")
    public String rimuoviProdotto(@RequestParam Long id) {
        prodottoService.deleteById(id);
        return "redirect:/utente/dashboard";
    }
    
    @PostMapping("/aggiungi-simile")
    @PreAuthorize("hasRole('ADMIN')")
    public String aggiungiProdottoSimile(@RequestParam Long idProdotto,
                                         @RequestParam Long idSimile) {
        prodottoService.aggiungiSimile(idProdotto, idSimile);
        return "redirect:/utente/dashboard";
    }

    @PostMapping("/rimuovi-simile")
    @PreAuthorize("hasRole('ADMIN')")
    public String rimuoviProdottoSimile(@RequestParam Long idProdotto,
                                        @RequestParam Long idSimile) {
        prodottoService.rimuoviSimile(idProdotto, idSimile);
        return "redirect:/utente/dashboard";
    }


}
