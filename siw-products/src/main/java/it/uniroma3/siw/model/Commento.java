package it.uniroma3.siw.model;


import jakarta.persistence.*;

@Entity
public class Commento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String testo;

    @ManyToOne
    private Utente autore;   //può essere anche null

    @ManyToOne
    private Prodotto prodotto;

    // Costruttori
    public Commento() {}

    public Commento(String testo, Utente autore, Prodotto prodotto) {
        this.testo = testo;
        this.autore = autore;
        this.prodotto = prodotto;
    }

    // Getter e Setter
    public Long getId() { return id; }

    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }

    public Utente getAutore() { return autore; }
    public void setAutore(Utente autore) { this.autore = autore; }

    public Prodotto getProdotto() { return prodotto; }
    public void setProdotto(Prodotto prodotto) { this.prodotto = prodotto; }
}
