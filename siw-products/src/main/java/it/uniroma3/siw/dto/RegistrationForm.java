package it.uniroma3.siw.dto;



public class RegistrationForm {
    private String nome;
    private String cognome;
    private String email;
    private String password;

    //Costruttore vuoto
    public RegistrationForm() {}
    
    // Getter e Setter
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCognome() { return cognome; }
    public void setCognome(String cognome) { this.cognome = cognome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getRole() {return role; }
	public void setRole(String role) {this.role = role; }
	
}

