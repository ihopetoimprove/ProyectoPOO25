package poo.Lemmings;

import poo.ObjetoMovible;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Lemming extends ObjetoMovible {

    public enum EstadoLemming {CAMINANDO, CAYENDO, EXCAVANDO, BLOQUEANDO, PLANEANDO, EXPLOTANDO, MURIENDO, SALVADO}
    private static final int VELOCIDAD_BASE = 1;
    private static final int VELOCIDAD_CAIDA = 4;
    private static final int ANCHO_LEMMING = 20;
    private static final int ALTO_LEMMING = 20;
    private static final int UMBRAL_CAIDA_FATAL_PIXELES = 6 * Nivel.BLOQUE_ALTO;
    private static BufferedImage spriteLemming = null;
    private static List<Lemming> todosLosLemmings = new ArrayList<>();
    private EstadoLemming estadoActual;
    private boolean direccionDerecha = true;
    protected Nivel nivelActual;
    private int pixelsCaidos = 0;
    private int columnaActual = 2;
    private int filaActual = 0;
    private int tiempoInicioExplosion = 0;

    public Lemming(int x, int y, Nivel nivelActual) {
        super(x, y);
        this.nivelActual = nivelActual;
        this.estadoActual = EstadoLemming.CAYENDO;
        this.velocidadX = VELOCIDAD_BASE;
        this.velocidadY = 0;
        if (spriteLemming == null){
            try {
                spriteLemming = ImageIO.read(Objects.requireNonNull(Lemming.class.getClassLoader().getResourceAsStream("imagenes/Lemmings/animacionesLemming.png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void limpiarLemmings(){todosLosLemmings.clear();}
    public void setEstado(EstadoLemming nuevoEstado) {this.estadoActual = nuevoEstado;}
    public EstadoLemming getEstado() {return estadoActual;}
    public EstadoLemming getEstadoActual() { return estadoActual;}
    public static List<Lemming> getTodosLosLemmings() { return todosLosLemmings;}

    @Override
    public void mover() {
        boolean haySuelo = haySueloDebajo();
        columnaActual ++;
        if (estadoActual == EstadoLemming.EXCAVANDO){
            velocidadX = 0;
        }

        if (estadoActual == EstadoLemming.PLANEANDO){
            this.y = y + 1;
        }

        if (estadoActual == EstadoLemming.BLOQUEANDO){
            velocidadX = 0;
        }

        if (estadoActual == EstadoLemming.EXPLOTANDO){
            velocidadX = 0;
            // Lógica de explosión: después de un cierto tiempo, destruir bloques y eliminar Lemming
            // Explotar después de 0.5 segundos de animación
            tiempoInicioExplosion ++;
            if (tiempoInicioExplosion >= 16) {
                realizarExplosion();
                setEstado(EstadoLemming.MURIENDO); // O un estado para ser eliminado completamente
                tiempoInicioExplosion = 0;
                // Puedes añadir una lógica aquí para removerlo de todosLosLemmings en el próximo ciclo de jueg
            }
            return; // No hacer ninguna otra lógica de movimiento si está explotando
        }

        if (!haySuelo && estadoActual == EstadoLemming.CAMINANDO) {
            setEstado(EstadoLemming.CAYENDO);
            pixelsCaidos = 0; // Reinicia el contador de caída
        }

        // Si estamos cayendo Y encontramos suelo
        else if (haySuelo && (estadoActual == EstadoLemming.CAYENDO || estadoActual == EstadoLemming.PLANEANDO)) {
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
        }
    }

    private void realizarExplosion() {
        System.out.println("Lemming en (" + x + "," + y + ") EXPLOTANDO! Rompiendo el bloque debajo.");

        // Calcular la posición en píxeles del centro inferior del Lemming
        // Esto es el punto donde el Lemming "toca" el suelo o el bloque debajo
        int pixelXCentroInferior = this.x + ANCHO_LEMMING / 2;
        int pixelYDebajo = this.y + ALTO_LEMMING + 10; // Un pixel por debajo de la base del Lemming

        // Llamar a setTipoTile del nivel para cambiar el tile a "vacío" (0)
        // Se rompe el tile en la posición inferor-central del Lemming
        nivelActual.setTipoTile(pixelXCentroInferior, pixelYDebajo, 0);
    }

    @Override
    public void dibujar(Graphics2D g) {
        switch (estadoActual) {
            case CAMINANDO:
                if (columnaActual >= 8) {
                    columnaActual = 3;
                }
                filaActual = 0;
                break;
            case CAYENDO:
                columnaActual = 3;
                filaActual = 22; // Fila de caída
                break;
            case PLANEANDO:
                columnaActual = 6;
                filaActual = 97; // Fila de paraguas
                break;
            case EXCAVANDO:
                if (columnaActual >= 12) {
                    columnaActual = 2;
                }
                filaActual = 249;
                break;
            case EXPLOTANDO:
                if (columnaActual >= 16) {
                    // - Eliminar el Lemming del juego
                    // - Ponerlo en un estado "muerto" o "inactivo"
                    columnaActual = 15; // Se queda en el último frame de la explosión
                }
                filaActual = 169; // Fila de explosión
                break;
            case BLOQUEANDO:
                if (columnaActual >= 16){
                    columnaActual = 3;
                }
                filaActual = 149;
                break;
        }
        int sx = columnaActual * 16 + 2;
        int dx = this.x;
        int dy = this.y;
        g.drawImage(spriteLemming,
                dx, dy, dx + 24, dy + 24, // Coordenadas de destino en pantalla (esquina superior izq y esquina inferior der)
                sx, filaActual, sx + 12, filaActual + 12, // Coordenadas de origen en el sprite sheet (esquina superior izq y esquina inferior der)
                null);
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
        int checkYCentroVertical = this.y + ALTO_LEMMING / 2; // A la altura del centro del Lemming
        int checkYSuperior = this.y + 5; // Cerca de la cabeza (para túneles bajos)

        if (direccionDerecha) {
            checkX = this.x + ANCHO_LEMMING + 1; // Un píxel más allá de la derecha del Lemming
        } else {
            checkX = this.x - 1; // Un píxel más allá de la izquierda del Lemming
        }

        // 1. Detección de terreno sólido
        if (esTerrenoSolidoODestructible(checkX, checkYCentroVertical) ||
                esTerrenoSolidoODestructible(checkX, checkYSuperior)) {
            return true;
        }

        // 2. Detección de otros Lemmings bloqueadores
        Rectangle lemmingRect = new Rectangle(x, y, ANCHO_LEMMING, ALTO_LEMMING);
        for (Lemming otroLemming : todosLosLemmings) {
            // No compararse a sí mismo
            if (otroLemming == this) {
                continue;
            }

            // Solo si el otro Lemming está en estado BLOQUEANDO
            if (otroLemming.getEstadoActual() == EstadoLemming.BLOQUEANDO) {
                Rectangle otroLemmingRect = new Rectangle(otroLemming.x, otroLemming.y, ANCHO_LEMMING, ALTO_LEMMING);
                Rectangle nextLemmingRect;
                if (direccionDerecha) {
                    nextLemmingRect = new Rectangle(x + velocidadX, y, ANCHO_LEMMING, ALTO_LEMMING);
                } else {
                    nextLemmingRect = new Rectangle(x + velocidadX, y, ANCHO_LEMMING, ALTO_LEMMING);
                }

                // Si el bloqueador está muy cerca en la dirección del movimiento
                // y se intersectaría con la próxima posición, lo consideramos una pared.
                if (nextLemmingRect.intersects(otroLemmingRect)) {
                    // Opcional: solo considerar si el bloqueador está justo en frente (misma altura, dirección correcta)
                    // int distanciaX = Math.abs(this.x - otroLemming.x);
                    // if (distanciaX <= ANCHO_LEMMING && // Están cerca horizontalmente
                    //     Math.abs(this.y - otroLemming.y) < ALTO_LEMMING / 2) { // Están a una altura similar
                    return true;
                    // }
                }
            }
        }
        return false; // No hay pared de terreno ni Lemming bloqueador
    }

    private boolean tocaSalida() {
        Rectangle lemmingRect = new Rectangle(x, y, ANCHO_LEMMING, ALTO_LEMMING);
        Rectangle salidaRect = new Rectangle(nivelActual.getSalidaX(), nivelActual.getSalidaY(),32, 32);
        return lemmingRect.intersects(salidaRect);
    }

    public void aplicarHabilidadLemming(PanelHabilidades.TipoHabilidad habilidad) {
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

    public static void agregarLemming(Lemming nuevoLemming) {
        todosLosLemmings.add(nuevoLemming);
    }
}