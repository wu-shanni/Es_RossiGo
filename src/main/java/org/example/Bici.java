package org.example;

class Bici extends Veicolo{
    protected int marce;
    protected Double tariffa;
    protected Double durata;

    public Bici(int codice, int marce, Double tariffa,Noleggio n) {
        super(codice);
        this.marce = marce;
        this.tariffa = tariffa;
        durata = (double) n.Durata(n.oraI, n.oraF);
    }

    @Override
    public Double calcolaCosto(int minuti) {
        return tariffa/durata;
    }

    @Override
    public String consumoBatteriaPerMinuto() {
        return (double) lvlBatteria + " %";
    }
}
