package modello;

import servizio.Utente;

public abstract class Veicolo {
    private String codice;
    private int livelloBatteria;
    private String stato;
    private double tariffa;
    private double consumo;

    public Veicolo(String codice, int livelloBatteria, String stato, double tariffa, double consumo) {
        this.codice = codice;
        this.livelloBatteria = livelloBatteria;
        this.stato = stato;
        this.tariffa = tariffa;
        this.consumo = consumo;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public void setLivelloBatteria(int livelloBatteria) {
        this.livelloBatteria = livelloBatteria;
    }

    public String getCodice() {
        return codice;
    }

    public int getLivelloBatteria() {
        return livelloBatteria;
    }

    public String getStato() {
        return stato;
    }

    public double getTariffa() { return tariffa; }

    public double getConsumo() { return consumo; }

    public abstract double calcolaCosto(int minuti);
    public abstract double consumoBatteriaPerMinuto();

}
