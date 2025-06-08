package poo.ClasesCompartidas;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class BDJugador {
    private static String URL;
    private static final String rutaPong = "jdbc:sqlite:db/BDPong.db";
    private static final String rutaLemmings = "jdbc:sqlite:db/BDLemmings.db";


    public BDJugador() {
        new java.io.File("db").mkdirs();
        crearTablaJugadores();
    }
    public BDJugador(String juego) {

        if(juego=="Lemmings")
            URL=rutaLemmings;
        else if(juego=="Pong")
            URL=rutaPong;

        new java.io.File("db").mkdirs();
        crearTablaJugadores();
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void crearTablaJugadores() {
        String sql = "CREATE TABLE IF NOT EXISTS Jugadores (" +
                "nombreUsuario TEXT NOT NULL UNIQUE," +
                "puntuacion INTEGER DEFAULT 0" +
                ");";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla Jugadores creada o ya existente.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void agregarJugador(Jugador jugador) {
        String sql = "INSERT INTO Jugadores(nombreUsuario, puntuacion) VALUES(?,?)" +
                " ON CONFLICT(nombreUsuario) DO UPDATE SET puntuacion = EXCLUDED.puntuacion";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jugador.getNombre());
            pstmt.setInt(2, jugador.getRecord());
            pstmt.executeUpdate();
            System.out.println("Jugador " + jugador.getNombre() + " agregado.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Jugador obtenerJugadorPorNombre(String nombreUsuario) {
        String sql = "SELECT nombreUsuario, puntuacion FROM Jugadores WHERE nombreUsuario = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreUsuario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Jugador(
                        rs.getString("nombreUsuario"),
                        rs.getInt("puntuacion")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void actualizarJugador(Jugador jugador) {
        String sql = "UPDATE Jugadores SET nombreUsuario = ?, puntuacion = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, jugador.getNombre());
            pstmt.setInt(2, jugador.getRecord());
            pstmt.executeUpdate();
            System.out.println("Jugador " + jugador.getNombre() + " actualizado.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void eliminarJugador(String nombre) {
        String sql = "DELETE FROM Jugadores WHERE nombreUsuario = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
            System.out.println("Jugador " + nombre + " eliminado.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<List<Object>> obtenerTodosLosJugadores() {
        List<List<Object>> data = new ArrayList<>();
        String sql = "SELECT nombreUsuario, puntuacion FROM Jugadores ORDER BY puntuacion DESC";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                List<Object> row = new ArrayList<>(); // para la fila
                row.add(rs.getString("nombreUsuario"));
                row.add(rs.getInt("puntuacion"));
                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener jugadores de la BD: " + e.getMessage());
        }
        return data;
    }


    // podría usar vector para evitar problemas de concurrencia, pero el juego se ejecuta en un hilo asi q no hay je, DATAZO
    public List<String> obtenerColumnasJugadores(){
        List<String> columnas = new Vector<>();
        columnas.add("Nombre de Usuario");
        columnas.add("Puntuación");
        return columnas;
    }
}
