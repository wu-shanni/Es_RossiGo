package org.example;

public class Noleggio {
    public int oraI;
    public int oraF;

    public Noleggio(Utente u, Veicolo v, int oraI, int oraF){
        this.oraI = oraI;
        this.oraF = oraF;
    }

    public int Durata(int oraI, int oraF){
        return (oraI-oraF);
    }
}
