package servizio;
import modello.Veicolo;
import java.time.LocalTime;

public class Noleggio {
    private Utente utente;
    private Veicolo veicolo;
    private LocalTime orarioInizio;
    private LocalTime orarioFine;

    public Noleggio(Utente u, Veicolo v, LocalTime t1, LocalTime t2) {
        utente = u;
        veicolo = v;
        orarioInizio = t1;
        orarioFine = t2;
    }

    public Utente getUtente() {
        return utente;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public LocalTime getOrarioInizio() {
        return orarioInizio;
    }

    public LocalTime getOrarioFine() {
        return orarioFine;
    }

    public void setOrarioFine(LocalTime orarioFine) { this.orarioFine = orarioFine; }
}
