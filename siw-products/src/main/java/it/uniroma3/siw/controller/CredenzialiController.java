package it.uniroma3.siw.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import it.uniroma3.siw.model.Credenziali;
import it.uniroma3.siw.service.CredenzialiService;
import java.util.Optional;

@RestController
@RequestMapping("/credenziali")
public class CredenzialiController {

    @Autowired
    private CredenzialiService credenzialiService;

    @PostMapping("/create")
    public Credenziali createCredenziali(@RequestBody Credenziali credenziali) {
        return credenzialiService.save(credenziali);
    }

    @GetMapping("/{id}")
    public Optional<Credenziali> getCredenziali(@PathVariable Long id) {
        return credenzialiService.findById(id);
    }

    @GetMapping("/all")
    public Iterable<Credenziali> getAllCredenziali() {
        return credenzialiService.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCredenziali(@PathVariable Long id) {
        credenzialiService.deleteById(id);
    }
}
