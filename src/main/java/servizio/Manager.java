package servizio;

import Database.Database;
import eccezioni.*;
import modello.Veicolo;
import servizio.Utente;
import servizio.Noleggio;

import java.sql.SQLException;
import java.time.LocalTime;

public class Manager {
    private Database database;

    public Manager() {
        try {
            database = Database.getInstance();
        } catch (SQLException e) {
            System.out.println("Errore nella connessione al database: " + e.getMessage());
            System.exit(-1);
        }
    }

    public void registraVeicolo(Veicolo v) {
        try {
            database.insert(v);
        } catch (SQLException e) {
            System.out.println("Errore di connessione al database (insert veicolo): " + e.getMessage());
        }
    }

    public void registraUtente(Utente u) {
        try {
            database.insert(u);
        } catch (SQLException e) {
            System.out.println("Errore di connessione al database (insert utente): " + e.getMessage());
        }
    }

    public void sbloccaVeicolo(String codice, String id) throws VeicoloNonDisponibileException,
            BatteriaScaricaException, UtenteNonTrovatoException, CreditoInsufficienteException {
        try {
            Utente u = database.select(Utente.class, id);
            Veicolo v = database.select(Veicolo.class, codice);

            if (u == null) throw new UtenteNonTrovatoException("Utente non trovato nel database.");
            if (v == null) throw new VeicoloNonDisponibileException("Il veicolo non esiste.");

            if (!v.getStato().equals("LIBERO"))
                throw new VeicoloNonDisponibileException("Il veicolo non è disponibile");
            if (v.getLivelloBatteria() <= 10) {
                throw new BatteriaScaricaException("La batteria del veicolo è scarica (" + v.getLivelloBatteria() + "%)");
            }
            if (u.getCredito() <= 0)
                throw new CreditoInsufficienteException("Credito insufficiente: " + u.getCredito());

            v.setStato("IN_USO");

            database.update(v);
            database.insert(new Noleggio(u, v, LocalTime.now(), null));
        } catch (SQLException e) {
            System.out.println("Errore durante il salvataggio dello sblocco nel DB: " + e.getMessage());
        }
    }

    public void terminaNoleggio(String codice, int minutesUsati) {
        try {
            Veicolo v = database.select(Veicolo.class, codice);
            Noleggio n = database.select(codice);

            if (v == null || n == null) {
                System.out.println("Errore: Veicolo o Noleggio attivo non trovato per il codice: " + codice);
                return;
            }

            Utente u = n.getUtente();
            if (u == null) {
                System.out.println("Errore: Nessun utente associato a questo noleggio.");
                return;
            }

            double acc = (v.calcolaCosto(minutesUsati)) + 1;
            u.setCredito(u.getCredito() - acc);

            v.setStato("LIBERO");
            v.setLivelloBatteria((int) (v.getLivelloBatteria() - (v.consumoBatteriaPerMinuto() * minutesUsati)));

            n.setOrarioFine(n.getOrarioInizio().plusMinutes(minutesUsati));

            database.update(v);
            database.update(u);
            database.update(n);
        } catch (SQLException e) {
            System.out.println("Errore SQL durante il termine del noleggio: " + e.getMessage());
        }
    }

    public void mettiInRicarica(String codice) {
        try {
            Veicolo v = database.select(Veicolo.class, codice);
            if (v == null) {
                System.out.println("Errore: Veicolo non trovato per la ricarica.");
                return;
            }

            v.setStato("IN_CARICA");
            database.update(v);
            Thread.sleep(3000);

            v = database.select(Veicolo.class, codice);
            if (v != null) {
                v.setStato("LIBERO");
                v.setLivelloBatteria(100);
                database.update(v);
            }
        } catch (SQLException e) {
            System.out.println("Errore SQL durante la ricarica: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
