package poo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BDJugador {
    private static final String URL = "jdbc:sqlite:db/juego.db";

    public BDJugador() {
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

    public List<Jugador> obtenerTodosLosJugadores() {
        List<Jugador> jugadores = new ArrayList<>();
        String sql = "SELECT nombreUsuario, puntuacion FROM Jugadores";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                jugadores.add(new Jugador(
                        rs.getString("nombreUsuario"),
                        rs.getInt("puntuacion")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return jugadores;
    }
}
