package it.uniroma3.siw.model;


import jakarta.persistence.*;
import java.util.List;

@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cognome;
    private String email;

    @ManyToMany
    private List<Prodotto> prodottiPreferiti;

    @OneToMany(mappedBy = "autore")
    private List<Commento> commenti;

    @OneToOne(mappedBy = "utente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Credenziali credenziali;

    // Costruttori
    public Utente() {}

    public Utente(String nome, String cognome, String email) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
    }

    // Getter e Setter
    public Long getId() { return id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Prodotto> getProdottiPreferiti() { return prodottiPreferiti; }
    public void setProdottiPreferiti(List<Prodotto> prodottiPreferiti) { this.prodottiPreferiti = prodottiPreferiti; }

    public List<Commento> getCommenti() { return commenti; }
    public void setCommenti(List<Commento> commenti) { this.commenti = commenti; }

    public Credenziali getCredenziali() { return credenziali; }
    public void setCredenziali(Credenziali credenziali) {
        this.credenziali = credenziali;
        if (credenziali != null) {
            credenziali.setUtente(this); // mantiene la coerenza bidirezionale
        }
    }
}
