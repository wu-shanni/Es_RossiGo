package org.example;

class Bici extends Veicolo{
    protected int marce;
    protected Double tariffa;

    public Bici(int codice, int marce, Double tariffa) {
        super(codice);
        this.marce = marce;
        this.tariffa = tariffa;
    }

    @Override
    public Double calcolaCosto(int minuti) {
        return 0.0;
    }

    @Override
    public Double consumoBatteriaPerMinuto() {
        return tariffa;
    }
}
