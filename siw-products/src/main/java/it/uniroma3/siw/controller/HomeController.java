package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.service.ProdottoService;



@Controller
public class HomeController {

    @Autowired
    private ProdottoService prodottoService;

    @GetMapping("/")
    public String index(Model model) {
        Iterable<Prodotto> prodotti = prodottoService.findAll();
        model.addAttribute("prodotti", prodotti);
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login"; 
    }
    
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }


}
