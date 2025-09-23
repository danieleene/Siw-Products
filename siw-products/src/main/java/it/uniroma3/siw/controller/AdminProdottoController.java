package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.service.ProdottoService;

@Controller
@RequestMapping("/admin/prodotto")
public class AdminProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("prodotto", new Prodotto());
        return "form-prodotto";
    }

    @PostMapping("/save")
    public String saveProdotto(@ModelAttribute Prodotto prodotto) {
        prodottoService.save(prodotto);
        return "redirect:/product/" + prodotto.getTipologia().toLowerCase();
    }
    
    @GetMapping("/delete/{id}")
    public String deleteProdotto(@PathVariable Long id) {
        prodottoService.deleteById(id);
        return "redirect:/"; 
    }

}
