package it.uniroma3.siw.model;

import jakarta.persistence.*;

@Entity
public class Credenziali {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String ruolo; //    "USER" o "ADMIN"

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "utente_id")
    private Utente utente;

    // Costruttori
    public Credenziali() {}

    public Credenziali(String email, String password, String ruolo) {
        this.email = email;
        this.password = password;
        this.ruolo = ruolo;
    }

    // Getter e Setter
    public Long getId() { return id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRuolo() { return ruolo; }
    public void setRuolo(String ruolo) { this.ruolo = ruolo; }

    public Utente getUtente() { return utente; }
    public void setUtente(Utente utente) {
        //NON CHIAMARE this.utente = utente;
        if (utente != null) {
           //NON CHIAMARE utente.setCredenziali(this); // mantiene la coerenza bidirezionale
        }
    }
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}

