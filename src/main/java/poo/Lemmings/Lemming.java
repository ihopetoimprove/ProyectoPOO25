package poo.Lemmings;

import poo.ObjetoMovible;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lemming extends ObjetoMovible {

    public enum EstadoLemming {CAMINANDO, CAYENDO, EXCAVANDO, BLOQUEANDO, PLANEANDO, EXPLOTANDO, MURIENDO, SALVADO}
    private static final int VELOCIDAD_BASE = 1;
    private static final int VELOCIDAD_CAIDA = 4;
    private static final int ANCHO_LEMMING = 20;
    private static final int ALTO_LEMMING = 20;
    private static final int UMBRAL_CAIDA_FATAL_PIXELES = 6 * Nivel.BLOQUE_ALTO;

    private static List<Lemming> todosLosLemmings = new ArrayList<>();
    private EstadoLemming estadoActual;
    private boolean direccionDerecha = true;
    private int habilidad;
    protected Nivel nivelActual;
    private int pixelsCaidos = 0;
    private static BufferedImage spriteLemming;
    private int columnaActual = 1;
    private int filaActual = 0;

    public Lemming(int x, int y, Nivel nivelActual) {
        super(x, y);
        this.nivelActual = nivelActual;
        this.estadoActual = EstadoLemming.CAYENDO;
        this.velocidadX = VELOCIDAD_BASE;
        this.velocidadY = 0;
        try {
            spriteLemming = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/animacionesLemming.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setEstado(EstadoLemming nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public EstadoLemming getEstado() {
        return estadoActual;
    }

    @Override
    public void mover() {
        boolean haySuelo = haySueloDebajo();
        if (estadoActual == EstadoLemming.MURIENDO || estadoActual == EstadoLemming.SALVADO) {
            //acá deberíamos eliminarlo de la lista
            setX(900);
            setY(900);
        }

        if (estadoActual == EstadoLemming.EXCAVANDO){
            velocidadX = 0;
            columnaActual = (columnaActual + 1) % 16;
            //nivelActual.destruirBloque(x, y);

        }

        if (estadoActual == EstadoLemming.PLANEANDO){
            velocidadY = 1;
            columnaActual = (columnaActual + 1);
        }

        if (estadoActual == EstadoLemming.BLOQUEANDO){
            velocidadX = 0;
            columnaActual = (columnaActual + 1);
        }

        if (!haySuelo && estadoActual != EstadoLemming.CAYENDO ) {
            setEstado(EstadoLemming.CAYENDO); // ¡Empieza a caer!
            pixelsCaidos = 0; // Reinicia el contador de caída
        }

        // Si estamos cayendo Y encontramos suelo
        else if (haySuelo && estadoActual == EstadoLemming.CAYENDO) {
            setEstado(EstadoLemming.CAMINANDO); // Aterriza y camina
            // Ajustar Y para que el Lemming esté exactamente sobre el suelo
            int ySuelo = (this.y + ALTO_LEMMING) / Nivel.BLOQUE_ALTO * Nivel.BLOQUE_ALTO;
            // Ajusta la posición Y del Lemming para que su base esté JUSTO sobre la superficie del suelo
            this.y = ySuelo - ALTO_LEMMING;

            setEstado(EstadoLemming.CAMINANDO);

            // Lógica de daño por caída
            if (pixelsCaidos > UMBRAL_CAIDA_FATAL_PIXELES) {
                setEstado(EstadoLemming.MURIENDO);
            }
            pixelsCaidos = 0; // Reinicia el contador de caída al aterrizar
        }
        // Si sigue cayendo (no ha aterrizado aún)
        else if (estadoActual == EstadoLemming.CAYENDO) {
            this.y += VELOCIDAD_CAIDA; // Mover verticalmente
            pixelsCaidos += velocidadY; // Acumular distancia de caída
            columnaActual = (columnaActual + 1);
        }

        if(estadoActual == EstadoLemming.CAMINANDO){
            this.x = x + velocidadX;
            if(hayParedDelante()){
                cambiarDireccion();
            }
            if(tocaSalida()){
                setEstado(EstadoLemming.SALVADO);
                PanelHabilidades.salvarLemming();
            }
            columnaActual = (columnaActual + 1);
        }

    }

    @Override
    public void dibujar(Graphics2D g) {
        switch (estadoActual) {
            case CAMINANDO:
                if (columnaActual >= 8) {
                    columnaActual = 0;
                }
                filaActual = 0;
                break;
            case CAYENDO:
                if (columnaActual >= 4) {
                    columnaActual = 0;
                }
                filaActual = 4; // Fila de caída
                break;
            case PLANEANDO:
                if (columnaActual >= 2) {
                    columnaActual = 0;
                }
                filaActual = 5; // Fila de paraguas
                break;
            case EXCAVANDO:
                if (columnaActual >= 8) {
                    columnaActual = 0;
                }
                filaActual = 247;
                break;
            case EXPLOTANDO:
                if (columnaActual >= 16) {
                    // - Eliminar el Lemming del juego
                    // - Ponerlo en un estado "muerto" o "inactivo"
                    columnaActual = 15; // Se queda en el último frame de la explosión
                }
                filaActual = 8; // Fila de explosión
                break;
            case BLOQUEANDO:
                if (columnaActual >= 8){
                    columnaActual = 0;
                }
                filaActual = 130;
        }
        int sx = columnaActual * 16;
        //int sy = filaActual * 16;
        int dx = this.x;
        int dy = this.y;
        g.drawImage(spriteLemming,
                dx, dy, dx + 16, dy + 16, // Coordenadas de destino en pantalla (esquina superior izq y esquina inferior der)
                sx, filaActual, sx + 16, filaActual + 16, // Coordenadas de origen en el sprite sheet (esquina superior izq y esquina inferior der)
                null);
    }


    public static List<Lemming> getTodosLosLemmings() {
        return todosLosLemmings;
    }

    public void cambiarDireccion(){
        this.velocidadX = -1*velocidadX;
    }

    private boolean esTerrenoSolidoODestructible(int px, int py) {
        int tipo = nivelActual.getTipoTile(px, py);
        return tipo == 1 || tipo == 2;
    }

    private boolean haySueloDebajo() {
        int checkX = this.x + ANCHO_LEMMING / 2; // Centro inferior del Lemming
        int checkY = this.y + ALTO_LEMMING;     // Justo en la base del Lemming

        // Verifica si hay terreno en el punto exacto de su base o un píxel más abajo.
        // Esto ayuda a detectar el suelo incluso si el lemming está "justo al borde".
        return esTerrenoSolidoODestructible(checkX, checkY) ||
                esTerrenoSolidoODestructible(checkX, checkY + 1);
    }

    private boolean hayParedDelante() {
        int checkX;
        if (direccionDerecha) {
            checkX = this.x + ANCHO_LEMMING + 1; // Un píxel más allá de la derecha del Lemming
        } else {
            checkX = this.x - 1; // Un píxel más allá de la izquierda del Lemming
        }
        int checkYCentroVertical = this.y + ALTO_LEMMING / 2; // A la altura del centro del Lemming
        int checkYSuperior = this.y + 5; // Cerca de la cabeza

        // Verifica si hay terreno a la altura del cuerpo o de la cabeza en la dirección de movimiento
        return esTerrenoSolidoODestructible(checkX, checkYCentroVertical) ||
                esTerrenoSolidoODestructible(checkX, checkYSuperior);
    }

    private boolean tocaSalida() {
        Rectangle lemmingRect = new Rectangle(x, y, ANCHO_LEMMING, ALTO_LEMMING);
        Rectangle salidaRect = new Rectangle(nivelActual.getSalidaX(), nivelActual.getSalidaY(),32, 32);
        return lemmingRect.intersects(salidaRect);
    }

    public static void limpiarLemmings(){
        todosLosLemmings.clear();
    }

    public void aplicarHabilidadLemming(PanelHabilidades.TipoHabilidad habilidad) {
        if (estadoActual == EstadoLemming.CAMINANDO) {
            switch (habilidad) {
                case PARAGUAS:
                    setEstado(EstadoLemming.PLANEANDO);
                    break;
                case BOMBA:
                    setEstado(EstadoLemming.EXPLOTANDO);
                    break;
                case EXCAVADOR:
                    setEstado(EstadoLemming.EXCAVANDO);
                    break;
                case BLOQUEADOR:
                    setEstado(EstadoLemming.BLOQUEANDO);
                    break;
            }
        }
    }

    public static void agregarLemming(Lemming nuevoLemming) {
        todosLosLemmings.add(nuevoLemming);
    }

    public EstadoLemming getEstadoActual() {
        return estadoActual;
    }
}