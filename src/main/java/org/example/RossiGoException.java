package org.example;

public class RossiGoException extends Exception {
    public RossiGoException(String message) {
        super(message);
    }

    public RossiGoException(String message, Throwable cause) {
        super(message, cause);
    }
}

class VeicoloNonDisponibileException extends RossiGoException {
    public VeicoloNonDisponibileException(String message) {
        super(message);
    }
    public VeicoloNonDisponibileException(String message, Throwable cause) {
        super(message, cause);
    }
}

class BatteriaScaricaException extends RossiGoException {
    private final double livelloAttuale;

    public BatteriaScaricaException(String message, double livelloAttuale) {
        super(message);
        this.livelloAttuale = livelloAttuale;
    }

    public BatteriaScaricaException(String message, double livelloAttuale, Throwable cause) {
        super(message, cause);
        this.livelloAttuale = livelloAttuale;
    }

    public double getLivelloAttuale() {
        return livelloAttuale;
    }
}

class CreditoInsufficienteException extends RossiGoException {
    private final double creditoAttuale;

    public CreditoInsufficienteException(String message, double creditoAttuale) {
        super(message);
        this.creditoAttuale = creditoAttuale;
    }

    public CreditoInsufficienteException(String message, double creditoAttuale, Throwable cause) {
        super(message, cause);
        this.creditoAttuale = creditoAttuale;
    }

    public double getCreditoAttuale() {
        return creditoAttuale;
    }
}

class UtenteNonTrovatoException extends RossiGoException {
    public UtenteNonTrovatoException(String message) {
        super(message);
    }
    public UtenteNonTrovatoException(String message, Throwable cause) {
        super(message, cause);
    }
}

class CodiceFiscaleNonValidoException extends RossiGoException {
    public CodiceFiscaleNonValidoException(String message) {
        super(message);
    }
    public CodiceFiscaleNonValidoException(String message, Throwable cause) {
        super(message, cause);
    }
}
