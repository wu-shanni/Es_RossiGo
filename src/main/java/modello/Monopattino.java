package modello;

public class Monopattino extends Veicolo {

    public Monopattino(String codice, int livelloBatteria, String stato) {
        super(codice, livelloBatteria, stato, 0.25, 0.2);
    }

    @Override
    public double calcolaCosto(int minuti) {
        return getTariffa() * minuti;
    }

    @Override
    public double consumoBatteriaPerMinuto() {
        return getConsumo();
    }
}
