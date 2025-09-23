package it.uniroma3.siw.model;

public class Categoria {
    private String nome;
    private String tipologia;
    private String immagine;

    public Categoria(String nome, String tipologia, String immagine) {
        this.nome = nome;
        this.tipologia = tipologia;
        this.immagine = immagine;
    }

    // Getter
    public String getNome() { return nome; }
    public String getTipologia() { return tipologia; }
    public String getImmagine() { return immagine; }
}

