package Database;

import com.sun.jdi.InvalidTypeException;
import eccezioni.CodiceFiscaleNonValidoException;
import modello.Monopattino;
import modello.Veicolo;
import modello.Bici;
import servizio.Noleggio;
import servizio.Utente;

import java.sql.*;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalTime;

public class Database {
    private static Database instance;
    private static Connection connection;

    public Database() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:Rossigo.db");
    }

    public static Database getInstance() throws SQLException {
        return instance = instance != null ? instance : new Database();
    }

    public <T> void insert(T obj) throws SQLException {
        try {
            if (!connection.isValid(5)) {
                throw new RuntimeException("connessione persa");
            }
        } catch (SQLException e) {
            return;
        }

        String query = "INSERT INTO %s (%s) VALUES (%s)";
        String table, columns, values;

        try {
            switch (obj) {
                case Utente u -> {
                    if (esiste(Utente.class, u.getID())) {
                        throw new SQLException("Utente già nel database; cambiare id");
                    }
                    table = "Utenti";
                    columns = "ID, Nome, Email, Cod_fiscale, credito";
                    values = "?, ?, ?, ?, ?";
                }
                case Veicolo v -> {
                    if (esiste(Veicolo.class, v.getCodice())) {
                        throw new SQLException("Veicolo già nel database; cambiare id");
                    }
                    table = "veicoli";
                    columns = "CODICE, Liv_batt, Stato, Tipo, N_marce";
                    values = "?, ?, ?, ?, ?";
                }
                case Noleggio n -> {
                    table = "Noleggia";
                    columns = "ID_utente, COD_veicolo, Orario_in, Orario_fi";
                    values = "?, ?, ?, ?";
                }
                default -> {
                    throw new InvalidTypeException();
                }
            }
        } catch (InvalidTypeException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(query.formatted(table, columns, values))) {
            switch (obj) {
                case Utente u -> {
                    stmt.setString(1, u.getID());
                    stmt.setString(2, u.getNome());
                    stmt.setString(3, u.getEmail());
                    stmt.setString(4, u.getCodiceFiscale());
                    stmt.setDouble(5, u.getCredito());
                }
                case Veicolo v -> {
                    stmt.setString(1, v.getCodice());
                    stmt.setDouble(2, v.getLivelloBatteria());
                    stmt.setString(3, v.getStato());
                    String tipo;
                    Integer marce;
                    if (v instanceof Bici) {
                        stmt.setString(4, "BICI");
                        stmt.setInt(5, ((Bici) v).getNumeroMarce());
                    } else {
                        stmt.setString(4, "MONOPATTINO");
                        stmt.setNull(5, java.sql.Types.INTEGER);
                    }
                }
                case Noleggio n -> {
                    stmt.setString(1, n.getUtente().getID());
                    stmt.setString(2, n.getVeicolo().getCodice());
                    stmt.setTime(3, Time.valueOf(n.getOrarioInizio()));
                    stmt.setTime(4, Time.valueOf(n.getOrarioFine()));
                }
                default -> {
                }
            }
            stmt.execute();
        }
    }

    public <T> T select(Class<T> type, String key) throws SQLException {
        try {
            if (!connection.isValid(5)) {
                throw new RuntimeException("connessione persa");
            }
        } catch (SQLException e) {
            return null;
        }

        if (type == null) return null;

        String query = "SELECT * FROM %s WHERE %s = ?";
        String table, param;

        if (type == Utente.class) {
            table = "Utenti";
            param = "ID";
        } else if (type == Veicolo.class) {
            table = "veicoli";
            param = "CODICE";
        } else {
            throw new IllegalArgumentException("Tipo non supportato: " + type.getTypeName());
        }

        try (PreparedStatement stmt = connection.prepareStatement(query.formatted(table, param))) {
            stmt.setString(1, key);

            try (ResultSet rs = stmt.executeQuery()) {
                if (type == Utente.class) {
                    Utente u = new Utente(
                            rs.getString("ID"),
                            rs.getString("Nome"),
                            rs.getString("Email"),
                            rs.getString("Cod_fiscale"),
                            rs.getDouble("Credito")
                    );

                    return type.cast(u);
                } else {
                    String tipo = rs.getString("Tipo");
                    Veicolo v = null;

                    switch (tipo.toUpperCase()) {
                        case "BICI" -> v = new Bici(
                                rs.getString("CODICE"),
                                rs.getInt("Liv_batt"),
                                rs.getString("Stato"),
                                rs.getInt("N_marce")
                        );
                        case "MONOPATTINO" -> v = new Monopattino(
                                rs.getString("CODICE"),
                                rs.getInt("Liv_batt"),
                                rs.getString("Stato")
                        );
                        default -> throw new IllegalArgumentException("Tipo veicolo sconosciuto: " + tipo);
                    }

                    return type.cast(v);
                }
            }
        } catch (CodiceFiscaleNonValidoException e) {
            throw new RuntimeException(e);
        }
    }

    public Noleggio select(String cod) throws SQLException {
        String query = "SELECT Orario_in, Orario_fi, ID_utente FROM noleggia WHERE COD_veicolo = ? AND Orario_fi IS NULL";

        Time orarioInizio;
        String id;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cod);

            try (ResultSet rs = stmt.getResultSet()) {
                orarioInizio = rs.getTime("Orario_in");
                id = rs.getString("ID_utente");
            }
        }

        return new Noleggio(
                select(Utente.class, id),
                select(Veicolo.class, cod),
                orarioInizio.toLocalTime(),
                null
        );
    }

    public Noleggio select(String id, String cod) throws SQLException {
        String query = "SELECT Orario_in, Orario_fi FROM noleggia WHERE ID_Utente = ? AND COD_veicolo = ?";

        Time orarioInizio, orarioFine;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            stmt.setString(2, cod);

            try (ResultSet rs = stmt.getResultSet()) {
                orarioInizio = rs.getTime("Orario_in");
                orarioFine = rs.getTime("Orario_fi");
            }
        }
        
        return new Noleggio(
                select(Utente.class, id),
                select(Veicolo.class, cod),
                orarioInizio.toLocalTime(),
                orarioFine.toLocalTime()
        );
    }

    public <T> void update(T obj) throws SQLException {
        try {
            if (!connection.isValid(5)) {
                throw new RuntimeException("connessione persa");
            }
        } catch (SQLException e) {
            return;
        }

        String query = "UPDATE %s SET %s WHERE %s = ?";
        String table, setClause, idColumn;

        try {
            switch (obj) {
                case Utente u -> {
                    table = "Utenti";
                    setClause = "Nome = ?, Email = ?, Cod_fiscale = ?, Credito = ?";
                    idColumn = "ID";
                }
                case Veicolo v -> {
                    table = "veicoli";
                    setClause = "Liv_batt = ?, Stato = ?, Tipo = ?, N_marce = ?";
                    idColumn = "CODICE";
                }
                default -> {
                    throw new InvalidTypeException();
                }
            }
        } catch (InvalidTypeException e) {
            throw new RuntimeException(e);
        }

        try (PreparedStatement stmt = connection.prepareStatement(query.formatted(table, setClause, idColumn))) {
            switch (obj) {
                case Utente u -> {
                    stmt.setString(1, u.getNome());
                    stmt.setString(2, u.getEmail());
                    stmt.setString(3, u.getCodiceFiscale());
                    stmt.setDouble(4, u.getCredito());
                    stmt.setString(5, u.getID());
                }
                case Veicolo v -> {
                    stmt.setDouble(1, v.getLivelloBatteria());
                    stmt.setString(2, v.getStato());
                    String tipo;
                    Integer marce;
                    if (v instanceof Bici) {
                        tipo = "BICI";
                        marce = ((Bici) v).getNumeroMarce();
                    } else {
                        tipo = "MONOPATTINO";
                        marce = null;
                    }
                    stmt.setString(3, tipo);
                    if (marce != null) {
                        stmt.setInt(4, marce);
                    } else {
                        stmt.setNull(4, java.sql.Types.INTEGER);
                    }
                    stmt.setString(5, v.getCodice());
                }
                default -> {
                }
            }
            stmt.executeUpdate();
        }
    }

    public <T> boolean esiste(Class<T> type, String key) throws SQLException {
        try {
            if (!connection.isValid(5)) {
                throw new RuntimeException("connessione persa");
            }
        } catch (SQLException e) {
            return false;
        }

        String query = "SELECT 1 FROM %s WHERE %s = ?";

        String table, idColumn;

        if (type == Utente.class) {
            table = "Utenti";
            idColumn = "ID";
        } else if (type == Veicolo.class) {
            table = "veicoli";
            idColumn = "CODICE";
        } else {
            return false;
        }

        try (PreparedStatement stmt = connection.prepareStatement(query.formatted(table, idColumn))) {
            stmt.setString(1, key);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

}