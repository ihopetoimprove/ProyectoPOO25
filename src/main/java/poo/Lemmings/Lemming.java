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

    public enum EstadoLemming {CAMINANDO, CAYENDO, EXCAVANDO, BLOQUEANDO, AMORTIGUANDO, EXPLOTANDO, MURIENDO, SALVADO}
    private static final int VELOCIDAD_BASE = 3;
    private static final int VELOCIDAD_CAIDA = 10;
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

    public Lemming(int x, int y, Nivel nivelActual) {
        super(x, y);
        this.nivelActual = nivelActual;
        this.estadoActual = EstadoLemming.CAYENDO;
        this.velocidadX = VELOCIDAD_BASE;
        this.velocidadY = 0;
    }

    public void setEstado(EstadoLemming nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public EstadoLemming getEstado() {
        return estadoActual;
    }

    @Override
    public void mover() {
        if (estadoActual == EstadoLemming.MURIENDO || estadoActual == EstadoLemming.SALVADO || estadoActual == EstadoLemming.BLOQUEANDO) {
            //acá deberíamos eliminarlo de la lista
            setX(900);
            setY(900);
        }

        if (estadoActual == EstadoLemming.EXCAVANDO){
            velocidadX = 0;
        }
        boolean haySuelo = haySueloDebajo();

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
       }
    }

    @Override
    public void dibujar(Graphics2D g) {
        BufferedImage imagenLemming;
        try {
            imagenLemming = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/mario.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (imagenLemming != null) {
            g.drawImage(imagenLemming, x, y, ANCHO_LEMMING, ALTO_LEMMING, null);
    }
}
    public static List<Lemming> getTodosLosLemmings() {
        return todosLosLemmings;
    }

    public void cambiarDireccion(){
        this.velocidadX = -1*velocidadX;
    }

    private boolean esTerrenoSolidoODestructible(int px, int py) {
        int tipo = nivelActual.getTipoTile(px, py);
        // Simplificado:
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
                    setEstado(EstadoLemming.AMORTIGUANDO);
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