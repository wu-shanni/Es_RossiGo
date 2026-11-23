package app;

import com.github.lalyos.jfiglet.FigletFont;
import eccezioni.CodiceFiscaleNonValidoException;
import eccezioni.RossiGoException;
import modello.Bici;
import modello.Monopattino;
import servizio.Manager;
import servizio.Utente;
import java.io.IOException;

import static java.lang.IO.println;

public class Main {
    public static void main(String[] args) throws IOException {
        println(FigletFont.convertOneLine("ROSSIGO"));

        Manager flotta = new Manager();

        Bici bici1 = new Bici("B01", 80, "LIBERO", 7);
        Bici bici2 = new Bici("B02", 10, "LIBERO", 6);

        Monopattino mono1 = new Monopattino("M01", 90, "LIBERO");
        Monopattino mono2 = new Monopattino("M02", 60, "LIBERO");

        flotta.registraVeicolo(bici1);
        flotta.registraVeicolo(bici2);
        flotta.registraVeicolo(mono1);
        flotta.registraVeicolo(mono2);

        // Isoliamo ogni creazione in un try-catch per non bloccare gli altri utenti
        Utente u1 = null;
        try {
            u1 = new Utente("U01", "Mario", "mario@gmail.com", "RSSLCU08H23L840O", 20.0);
            flotta.registraUtente(u1);
        } catch (CodiceFiscaleNonValidoException e) {
            println("Errore registrazione U01: " + e.getMessage());
        }

        Utente u2 = null;
        try {
            u2 = new Utente("U02", "Luca", "luca@gmail.com", "RSSLCU08H23L840O", 5.0);
            flotta.registraUtente(u2);
        } catch (CodiceFiscaleNonValidoException e) {
            println("Errore registrazione U02: " + e.getMessage());
        }

        Utente u3 = null;
        try {
            u3 = new Utente("U03", "Riccardo", "riccardo@gmail.com", "CODICEFISCALEERRATO", 10.0);
            flotta.registraUtente(u3);
        } catch (CodiceFiscaleNonValidoException e) {
            // Questo fallirà, ma il programma NON si blocca e va avanti
            println("Errore registrazione U03 (Segnalato): " + e.getMessage());
        }

        //3 - SBLOCCA VEICOLO
        println("\n--UTENTE 1 NOLEGGIA BICI 1--");
        try {
            flotta.sbloccaVeicolo("B01", "U01");
            println("Veicolo sbloccato");
        } catch (RossiGoException e) {
            println("Noleggio non riuscito: " + e.getMessage());
        }

        //4 - ECCEZIONE BICI IN USO
        println("\n--UTENTE 2 NOLEGGIA BICI 1--");
        try {
            flotta.sbloccaVeicolo("B01", "U02");
            println("Veicolo sbloccato");
        } catch (RossiGoException e) {
            println("Noleggio non riuscito: " + e.getMessage());
        }

        //5 - ECCEZIONE BATTERIA SCARICA
        println("\n--UTENTE 1 NOLEGGIA BICI 2--");
        try {
            flotta.sbloccaVeicolo("B02", "U01");
            println("Veicolo sbloccato");
        } catch (RossiGoException e) {
            println("Noleggio non riuscito: " + e.getMessage());
        }

        //6 - ECCEZIONE UTENTE INESISTENTE
        println("\n--UTENTE INESISTENTE NOLEGGIA MONOPATTINO 1--");
        try {
            flotta.sbloccaVeicolo("M01", "U10");
            println("Veicolo sbloccato");
        } catch (RossiGoException e) {
            println("Noleggio non riuscito: " + e.getMessage());
        }

        //7 - TERMINA NOLEGGIO
        // Usiamo un controllo preventivo: se u1 non è stato creato a causa di un errore precedente, evitiamo un NullPointerException
        if (u1 != null) {
            flotta.terminaNoleggio("B01", 20);
            println("\nNoleggio terminato");
            println("Credito dell'utente associato: " + u1.getCredito() + " euro");
            println("Batteria veicolo: " + bici1.getLivelloBatteria() + "%");
        } else {
            println("\nImpossibile terminare il noleggio: l'utente U01 non è valido.");
        }

        println("\n=== ESECUZIONE DEL MAIN COMPLETATA CON SUCCESSO ===");
    }
}
