package modello;

public class Bici extends Veicolo{
    private int numeroMarce;

    public int getNumeroMarce() {
        return numeroMarce;
    }

    public Bici(String codice, int livelloBatteria, String stato, int numeroMarce) {
        super(codice, livelloBatteria, stato, 0.15, 0.1);
        this.numeroMarce = numeroMarce;
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
