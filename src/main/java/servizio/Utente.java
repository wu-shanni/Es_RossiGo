package servizio;

import eccezioni.CodiceFiscaleNonValidoException;
import it.kamaladafrica.codicefiscale.CodiceFiscale;

public class Utente {
    private String ID;
    private String nome;
    private String email;
    private String codiceFiscale;
    private double credito;

    public Utente(String ID, String nome, String email, String codiceFiscale, double credito) throws CodiceFiscaleNonValidoException {
        this.ID = ID;
        this.nome = nome;
        this.email = email;
        if(!CodiceFiscale.isFormatValid(codiceFiscale)) {
            throw new CodiceFiscaleNonValidoException("Codice fiscale non valido");
        } else {
            this.codiceFiscale = codiceFiscale;
        }
        this.credito = credito;
    }

    public String getID() {
        return ID;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }
}
