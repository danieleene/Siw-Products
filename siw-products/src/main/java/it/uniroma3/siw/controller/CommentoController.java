package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import it.uniroma3.siw.model.Commento;
import it.uniroma3.siw.service.CommentoService;
import java.util.Optional;

@RestController
@RequestMapping("/commenti")
public class CommentoController {

    @Autowired
    private CommentoService commentoService;

    @PostMapping("/create")
    public Commento createCommento(@RequestBody Commento commento) {
        return commentoService.save(commento);
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
}
