package it.uniroma3.siw.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
public class Prodotto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private double prezzo;

    @Column(length = 1000)
    private String descrizione;

    private String tipologia;

    @ManyToMany
    private List<Prodotto> prodottiSimili = new ArrayList<>();

    // Costruttori
    public Prodotto() {}

    public Prodotto(String nome, double prezzo, String descrizione, String tipologia) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.tipologia = tipologia;
    }

    // Getter e Setter
    public Long getId() {
    	return id;
    	}
    
    public void setId(Long id) {
		this.id = id;
	}

    public String getNome() {
    	return nome;
    	}
    
    public void setNome(String nome) {
    	this.nome = nome;
    	}

    public double getPrezzo() {
    	return prezzo;
    	}
    
    public void setPrezzo(double prezzo) {
    	this.prezzo = prezzo;
    	}

    public String getDescrizione() {
    	return descrizione;
    	}
    
    public void setDescrizione(String descrizione) {
    	this.descrizione = descrizione;
    	}

    public String getTipologia() {
    	return tipologia;
    	}
    
    public void setTipologia(String tipologia) {
    	this.tipologia = tipologia;
    	}

    public List<Prodotto> getProdottiSimili() {
    	return prodottiSimili;
    	}
    
    public void setProdottiSimili(List<Prodotto> prodottiSimili) {
    	this.prodottiSimili = prodottiSimili;
    	}

}
