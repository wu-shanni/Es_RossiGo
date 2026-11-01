package org.example;

public class Monopattino extends Veicolo {
    private int velocitaMax;
    private boolean zonaPedonale;
    private static final double tariffa = 0.3;
    private static final double consumo = 1;

    public Monopattino(String codice, Double lvlBatteria, int velocitaMax, boolean zonaPedonale) {
        super(codice, lvlBatteria);
        this.velocitaMax = velocitaMax;
        this.zonaPedonale = zonaPedonale;
    }

    @Override
    public Double calcolaCosto(int minuti) {
        return minuti * tariffa;
    }

    @Override
    public double consumoBatteriaPerMinuto() {
        return consumo;
    }

    public int getVelocitaMax(){
        return velocitaMax;
    }

    public boolean isZonaPedonale(){
        return zonaPedonale;
    }
}
