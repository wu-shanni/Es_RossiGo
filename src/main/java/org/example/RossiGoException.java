package org.example;

abstract class RossiGoException extends RuntimeException {
    public RossiGoException(String message) {
        super(message);
    }
}

class VeicoloNonDisponibileException extends RossiGoException {
    public VeicoloNonDisponibileException(String message) {
        super(message);
    }
}

class BatteriaScaricaException extends RossiGoException {
    public BatteriaScaricaException(String message) {
        super(message);
    }
}

class CreditoInsufficienteException extends RossiGoException {
    public CreditoInsufficienteException(String message) {
        super(message);
    }
}

class UtenteNonTrovatoException extends RossiGoException {
    public UtenteNonTrovatoException(String message) {
        super(message);
    }
}

class CodiceFiscaleNonValidoException extends RossiGoException {
    public CodiceFiscaleNonValidoException(String message) {
        super(message);
    }
}

