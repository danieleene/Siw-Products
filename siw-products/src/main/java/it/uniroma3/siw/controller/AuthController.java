package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import it.uniroma3.siw.dto.RegistrationForm;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private CredenzialiService credenzialiService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@RequestBody RegistrationForm form) {
        // Crea Utente
        Utente utente = new Utente();
        utente.setNome(form.getNome());
        utente.setCognome(form.getCognome());
        utente.setEmail(form.getEmail());

        // Crea Credenziali
        Credenziali credenziali = new Credenziali();
        credenziali.setEmail(form.getEmail());
        credenziali.setPassword(passwordEncoder.encode(form.getPassword()));
        credenziali.setRuolo("USER");
        credenziali.setUtente(utente); // collega le due entità

        // Salva
        utenteService.save(utente);
        credenzialiService.save(credenziali);

        return "redirect:/";
    }
}
