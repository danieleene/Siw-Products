package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.service.CommentoService;
import it.uniroma3.siw.service.ProdottoService;

import java.util.Optional;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;

import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.repository.CredenzialiRepository;

@RestController
@RequestMapping("/commenti")
public class CommentoController {

    @Autowired
    private CommentoService commentoService;
    
    @Autowired
    private ProdottoService prodottoService;
    
    @Autowired
    private CredenzialiRepository credenzialiRepository;

    @PostMapping("/create/{prodottoId}")
    public ResponseEntity<?> createCommento(@PathVariable Long prodottoId,
                                            @RequestBody String testo,
                                            @AuthenticationPrincipal Credenziali credenziali) {
        Utente autore = credenziali.getUtente();
        Optional<Prodotto> prodottoOpt = prodottoService.findById(prodottoId);

        if (prodottoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Prodotto non trovato.");
        }

        Prodotto prodotto = prodottoOpt.get();

        Optional<Commento> esistente = commentoService.findByAutoreAndProdotto(autore, prodotto);
        if (esistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Hai già commentato questo prodotto. Elimina il commento per poterlo riscrivere.");
        }

        Commento nuovo = new Commento(testo, autore, prodotto);
        commentoService.save(nuovo);
        return ResponseEntity.ok(nuovo);
    }


    @GetMapping("/{id}")
    public Optional<Commento> getCommento(@PathVariable Long id) {
        return commentoService.findById(id);
    }

    @GetMapping("/all")
    public Iterable<Commento> getAllCommenti() {
        return commentoService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCommento(@PathVariable Long id) {
        commentoService.deleteById(id);
    }
    
    @DeleteMapping("/delete/{prodottoId}")
    public ResponseEntity<?> deleteCommentoByAutoreAndProdotto(@PathVariable Long prodottoId,
                                                                @AuthenticationPrincipal Credenziali credenziali) {
        Utente autore = credenziali.getUtente();
        Optional<Prodotto> prodottoOpt = prodottoService.findById(prodottoId);

        if (prodottoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Prodotto non trovato.");
        }

        Prodotto prodotto = prodottoOpt.get();
        Optional<Commento> commentoOpt = commentoService.findByAutoreAndProdotto(autore, prodotto);

        if (commentoOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body("Nessun commento trovato da parte tua per questo prodotto.");
        }

        commentoService.deleteByAutoreAndProdotto(autore, prodotto);
        return ResponseEntity.ok("Commento eliminato con successo.");
    }

    @GetMapping("/commenti/form/{prodottoId}")
    public String mostraFormCommento(@PathVariable Long prodottoId, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof User user)) {
            return "redirect:/login";
        }

        String email = user.getUsername(); // se usi email come username
        Credenziali credenziali = credenzialiRepository.findByEmail(email);
        if (credenziali == null) {
            return "redirect:/login";
        }

        Utente utente = credenziali.getUtente();
        Optional<Prodotto> prodottoOpt = prodottoService.findById(prodottoId);
        if (prodottoOpt.isEmpty()) {
            return "redirect:/error";
        }

        Prodotto prodotto = prodottoOpt.get();
        Optional<Commento> commento = commentoService.findByAutoreAndProdotto(utente, prodotto);

        model.addAttribute("prodotto", prodotto);
        model.addAttribute("commentoEsistente", commento.orElse(null));
        return "commentoForm";
    }

    
    @PostMapping("/commenti/form/create/{prodottoId}")
    public String creaCommentoDaForm(@PathVariable Long prodottoId,
                                     @RequestParam String testo) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof User user)) {
            return "redirect:/login";
        }

        String email = user.getUsername(); // se usi email come username
        Credenziali credenziali = credenzialiRepository.findByEmail(email);
        if (credenziali == null) {
            return "redirect:/login";
        }

        Utente autore = credenziali.getUtente();
        Optional<Prodotto> prodottoOpt = prodottoService.findById(prodottoId);
        if (prodottoOpt.isEmpty()) {
            return "redirect:/error";
        }

        Prodotto prodotto = prodottoOpt.get();
        Commento commento = new Commento();
        commento.setAutore(autore);
        commento.setProdotto(prodotto);
        commento.setTesto(testo);
        commentoService.save(commento);

        return "redirect:/";
    }




}
