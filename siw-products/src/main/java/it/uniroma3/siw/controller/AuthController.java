package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import it.uniroma3.siw.dto.RegistrationForm;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.model.Utente;
import it.uniroma3.siw.service.CredenzialiService;
import it.uniroma3.siw.service.UtenteService;


@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private CredenzialiService credenzialiService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute RegistrationForm form) {
    	
    	//SOLO TEST DA ELIMINARE
    	System.out.println(">>> Metodo registerUser invocato");

    	
        // Crea Utente
        Utente utente = new Utente();
        utente.setNome(form.getNome());
        utente.setCognome(form.getCognome());
        utente.setEmail(form.getEmail());

        
        
        // Crea Credenziali
        Credenziali credenziali = new Credenziali();
        credenziali.setUsername(form.getEmail());
        credenziali.setPassword(new BCryptPasswordEncoder().encode(form.getPassword()));
        credenziali.setEmail(form.getEmail());



        
        // Questo perchè il ruolo è selezionabile nel form
        if (form.getRole() != null && form.getRole().equals("ADMIN")) {
            credenziali.setRuolo("ADMIN");
        } else {
            credenziali.setRuolo("USER");
        }


        Utente utenteSalvato = utenteService.save(utente);
        credenziali.setUtente(utenteSalvato);
        credenzialiService.save(credenziali);
        


        return "redirect:/";
    }
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        RegistrationForm form = new RegistrationForm();
        form.setRole("USER"); // valore di default per evitare errori nel select
        model.addAttribute("form", form);
        return "register";
    }


}

