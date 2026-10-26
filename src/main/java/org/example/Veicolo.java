package org.example;

abstract class Veicolo {
    protected int codice;
    protected int lvlBatteria;
    public String stato;

    public Veicolo(int codice){
        this.codice = codice;
    }

    public abstract Double calcolaCosto(int minuti);

    public abstract Double consumoBatteriaPerMinuto();


}
