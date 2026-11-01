package org.example;
import java.time.LocalDateTime;

public class Noleggio {
    private Utente utente;
    private Veicolo veicolo;
    public LocalDateTime oraI;
    public LocalDateTime oraF;

    public Noleggio(Utente utente, Veicolo veicolo, LocalDateTime oraI) {
        this.utente = utente;
        this.veicolo = veicolo;
        this.oraI = oraI;
    }

    public Utente getUtente() { return utente; }
    public Veicolo getVeicolo() { return veicolo; }
    public LocalDateTime getOraI() { return oraI; }
    public LocalDateTime getOraF() { return oraF; }
    public void setOraF(LocalDateTime oraF) { this.oraF = oraF; }
}