package org.example;

abstract class Veicolo {
    protected String codice;
    protected Double lvlBatteria;
    public StatoVeicolo stato;

    public Veicolo(String codice, Double lvlBatteria) {
        this.codice = codice;
        this.lvlBatteria = lvlBatteria;
        this.stato = StatoVeicolo.LIBERO;
    }

    public abstract Double calcolaCosto(int minuti);
    public abstract double consumoBatteriaPerMinuto();

    public String getCodice() { return codice; }
    public double getBatteria() { return lvlBatteria; }
    public void setBatteria(double batteria) { this.lvlBatteria = Math.clamp(lvlBatteria, 0, 100); }
    public StatoVeicolo getStato() { return stato; }
    public void setStato(StatoVeicolo stato) { this.stato = stato; }
}
