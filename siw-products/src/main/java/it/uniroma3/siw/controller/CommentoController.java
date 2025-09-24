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
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        // Estrai credenziali e utente autenticato
        Credenziali credenziali = userDetails.getCredenziali();
        Utente autore = credenziali.getUtente();

        // Recupera il prodotto
        Optional<Prodotto> prodottoOpt = prodottoService.findById(prodottoId);
        if (prodottoOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Prodotto non trovato.");
        }
        Prodotto prodotto = prodottoOpt.get();

        // Verifica se l'utente ha già commentato questo prodotto
        Optional<Commento> esistente = commentoService.findByAutoreAndProdotto(autore, prodotto);
        if (esistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Hai già commentato questo prodotto. Elimina il commento per poterlo riscrivere.");
        }

        // Crea e salva il nuovo commento
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

    @GetMapping("/commenta/{id}")
    public String mostraFormCommento(@PathVariable Long id,
                                     @AuthenticationPrincipal CustomUserDetails userDetails,
                                     Model model) {
        Credenziali credenziali = userDetails.getCredenziali();

        if (credenziali == null || !credenziali.getRuolo().equals("USER")) {
            return "redirect:/error";
        }

        Optional<Prodotto> prodottoOpt = prodottoService.findById(id);
        if (prodottoOpt.isEmpty()) {
            return "redirect:/error";
        }

        model.addAttribute("prodotto", prodottoOpt.get());
        model.addAttribute("commento", new Commento());
        return "commentoForm";
    }

    //SOLO PER TEST!
    
   /* @GetMapping("/commenta/{id}")
    public String mostraFormCommento(@PathVariable Long id,
                                     @AuthenticationPrincipal Credenziali credenziali,
                                     Model model) {
        System.out.println("🔍 ID ricevuto nel controller: " + id);
        System.out.println("🔍 Credenziali: " + credenziali);
        if (credenziali != null) {
            System.out.println("🔍 Ruolo: " + credenziali.getRuolo());
        }

        Optional<Prodotto> prodottoOpt = prodottoService.findById(id);
        System.out.println("🔍 Prodotto trovato? " + prodottoOpt.isPresent());

        if (credenziali == null || !credenziali.getRuolo().equals("USER") || prodottoOpt.isEmpty()) {
            return "redirect:/error";
        }

        model.addAttribute("prodotto", prodottoOpt.get());
        model.addAttribute("commento", new Commento());
        return "commentoForm";
    } */
    
    
    @PostMapping("/create/{id}")
    public String creaCommento(@PathVariable Long id,
                               @ModelAttribute("commento") Commento commento,
                               @AuthenticationPrincipal CustomUserDetails userDetails) {

        Credenziali credenziali = userDetails.getCredenziali();

        if (credenziali == null || !credenziali.getRuolo().equals("USER")) {
            return "redirect:/error";
        }

        Utente autore = credenziali.getUtente();
        Prodotto prodotto = prodottoService.findById(id).orElse(null);

        if (prodotto == null) {
            return "redirect:/error";
        }

        commento.setAutore(autore);
        commento.setProdotto(prodotto);

        commentoService.save(commento);

        return "redirect:/prodotti/product/" + id;
    }




}

