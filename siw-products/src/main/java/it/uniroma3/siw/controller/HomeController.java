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
        // Categorie dinamiche
        List<Categoria> categorie = List.of(
            new Categoria("Articoli da cucina", "cucina", "1-cucina.jpg"),
            new Categoria("Articoli da giardino", "giardino", "1-giardino.jpg"),
            new Categoria("Per il bagno", "bagno", "1-bagno.jpg")
        );
        model.addAttribute("categorie", categorie);

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

