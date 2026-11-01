package org.example;

class Bici extends Veicolo {
    protected int marce;
    private static final double tariffa = 0.15;
    private static final double consumo = 0.5; // 0.5% al minuto

    public Bici(String codice, double batteria, int marce) {
        super(codice, batteria);
        this.marce = marce;
    }

    @Override
    public Double calcolaCosto(int minuti) {
        return minuti * tariffa;
    }

    @Override
    public double consumoBatteriaPerMinuto() {
        return consumo;
    }

    public int getNumeroMarce() {
        return marce;
    }
}