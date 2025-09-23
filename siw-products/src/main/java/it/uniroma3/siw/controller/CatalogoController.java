package it.uniroma3.siw.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import it.uniroma3.siw.model.Prodotto;
import it.uniroma3.siw.service.ProdottoService;

@Controller
public class CatalogoController {

    @Autowired
    private ProdottoService prodottoService;

    @GetMapping("/product/{tipologia}")
    public String prodottiPerCategoria(@PathVariable String tipologia, Model model) {
        List<Prodotto> prodotti = prodottoService.findByTipologia(tipologia.toUpperCase());
        model.addAttribute("prodotti", prodotti);
        model.addAttribute("categoria", tipologia);
        return "product-list";
    }
}
