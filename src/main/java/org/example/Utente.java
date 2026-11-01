package org.example;

public class Utente {
    private String id;
    private String nome;
    private String email;
    private String codF;
    private Double credito;

    public Utente(String id, String nome, String email, String codF, Double credito) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.codF = codF;
        this.credito = credito;
    }

    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getEmail() { return email;}
    public String getCodF() { return codF;}
    public Double getCredito() { return credito;}
    public void setCredito(Double credito) { this.credito = credito; }
}
