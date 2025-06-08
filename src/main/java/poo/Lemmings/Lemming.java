package poo.Lemmings;

import poo.ObjetoMovible;
import poo.Sonido;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Lemming extends ObjetoMovible {

    public enum EstadoLemming {CAMINANDO, CAYENDO, ESCALANDO, BLOQUEANDO, PLANEANDO, EXPLOTANDO, MURIENDO, SALVADO}
    private static final int VELOCIDAD_BASE = 1;
    private static final int VELOCIDAD_CAIDA = 4;
    private static final int ANCHO_LEMMING = 20;
    private static final int ALTO_LEMMING = 20;
    private static final int UMBRAL_CAIDA_FATAL_PIXELES = 200;
    private static BufferedImage spriteLemming = null;
    private static List<Lemming> todosLosLemmings = new ArrayList<>();
    private EstadoLemming estadoActual;
    private boolean direccionDerecha = true;
    protected Nivel nivelActual;
    private int pixelsCaidos = 0;
    private int columnaActual = 2;
    private int filaActual = 0;
    private int tiempoInicioExplosion = 0;
    private boolean puedeEscalar = false;
    private boolean puedePlanear = false;

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
    public void mover(double delta) {
        boolean haySuelo = haySueloDebajo();
        columnaActual ++;
        if (estadoActual == EstadoLemming.ESCALANDO) {
            this.y -= (int)(1 * delta);
            if (!hayParedDelante(x, y) || y <= 0 || hayTechoArriba()) {
                if (!direccionDerecha){
                    this.x = x - 10;
                }
                setEstado(EstadoLemming.CAMINANDO);
                puedeEscalar = false;
            }
        }

        if (estadoActual == EstadoLemming.PLANEANDO){
            this.y = (int)(y + 1 * delta);
            pixelsCaidos = 0;
            if(haySueloDebajo()){
                puedePlanear = false;
            }
        }

        if (estadoActual == EstadoLemming.BLOQUEANDO){
            velocidadX = 0;
        }

        if (estadoActual == EstadoLemming.EXPLOTANDO){
            velocidadX = 0;
            tiempoInicioExplosion ++;
            if (tiempoInicioExplosion >= 16) {
                realizarExplosion();
                setEstado(EstadoLemming.MURIENDO);
                tiempoInicioExplosion = 0;
            }
            return; // No hacer ninguna otra lógica de movimiento si está explotando
        }

        if (!haySuelo && estadoActual == EstadoLemming.CAMINANDO) {
            if (direccionDerecha){
                this.x = x +11;
            }else {
                this.x = x - 6;
            }
            setEstado(EstadoLemming.CAYENDO);
            pixelsCaidos = 0; // Reinicia el contador de caída
        }

        // Si estamos cayendo Y encontramos suelo
        else if (haySuelo && (estadoActual == EstadoLemming.CAYENDO || estadoActual == EstadoLemming.PLANEANDO)) {
            setEstado(EstadoLemming.CAMINANDO); // Aterriza y camina
            if (pixelsCaidos > UMBRAL_CAIDA_FATAL_PIXELES || tocaLava()) {
                setEstado(EstadoLemming.MURIENDO);
            }
            int ySuelo = (this.y + ALTO_LEMMING) / Nivel.BLOQUE_ALTO * Nivel.BLOQUE_ALTO;
            this.y = ySuelo - ALTO_LEMMING;

        }
        // Si sigue cayendo (no ha aterrizado aún)
        else if (estadoActual == EstadoLemming.CAYENDO) {
            this.y += (int)(VELOCIDAD_CAIDA * delta); // Mover verticalmente
            pixelsCaidos += (int) (VELOCIDAD_CAIDA * delta);
            if (tocaLava()){
                estadoActual = EstadoLemming.MURIENDO;
            }
            if (puedePlanear){
                estadoActual = EstadoLemming.PLANEANDO;
            }
        }

        if(estadoActual == EstadoLemming.CAMINANDO){
            this.x = (int) (x + velocidadX * delta);
            if(hayParedDelante(x,y) ){
                if(puedeEscalar){
                    estadoActual = EstadoLemming.ESCALANDO;
                }else {
                    cambiarDireccion();
                }
            }
            if(!haySueloDebajo() && puedePlanear){
                    estadoActual = EstadoLemming.PLANEANDO;
            }
            if(tocaSalida()){
                setEstado(EstadoLemming.SALVADO);
                PanelHabilidades.salvarLemming();
            }
            if(tocaLava()){
                setEstado(EstadoLemming.MURIENDO);
            }
        }
    }

    private void realizarExplosion() {
        int pixelXCentroInferior = this.x + ANCHO_LEMMING / 2;
        int pixelYDebajo = this.y + ALTO_LEMMING + 10; // Un pixel por debajo de la base del Lemming

        // Llamar a setTipoTile del nivel para cambiar el tile a "vacío" (0)
        nivelActual.setTipoTile(pixelXCentroInferior, pixelYDebajo, 0);
    }

    @Override
    public void dibujar(Graphics2D g) {
        switch (estadoActual) {
            case CAMINANDO:
                    if (columnaActual >= 8) {
                        columnaActual = 3;
                    }
                    filaActual = direccionDerecha ? 0 : 10;
                break;
            case CAYENDO:
                columnaActual = 1;
                filaActual = 19;
                break;
            case PLANEANDO:
                columnaActual = 6;
                filaActual = 97; // Fila de paraguas
                break;
            case ESCALANDO:
                if (columnaActual >= 8) {
                    columnaActual = 2;
                }
                filaActual = 40;
                break;
            case EXPLOTANDO:
                if (columnaActual >= 16) {
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
        direccionDerecha = !direccionDerecha;
    }

    private boolean esTerrenoSolidoODestructible(int px, int py) {
        int tipo = nivelActual.getTipoTile(px, py);
        return tipo == 1 || tipo == 2 || tipo == 3;
    }

    private boolean haySueloDebajo() {
        int checkX = this.x + ANCHO_LEMMING / 2; // Centro inferior del Lemming
        int checkY = this.y + ALTO_LEMMING; // Justo en la base del Lemming

        // Verifica si hay terreno en el punto exacto de su base o un píxel más abajo.
        return esTerrenoSolidoODestructible(checkX, checkY);
    }

    private boolean hayTechoArriba(){
        int checkX = this.x - ANCHO_LEMMING / 2; // Centro inferior del Lemming
        int checkY = this.y - ALTO_LEMMING;

        return esTerrenoSolidoODestructible(checkX, checkY);
    }

    private boolean hayParedDelante(int x, int y) {
        Rectangle rectanguloLemming = new Rectangle(x, y, ANCHO_LEMMING, ALTO_LEMMING);
        int checkXFront;
        if (direccionDerecha) {
            checkXFront = x + ANCHO_LEMMING; // Un píxel más allá del borde derecho del Lemming
        } else {
            checkXFront = x - 1; // Un píxel más allá del borde izquierdo del Lemming
        }
        int checkYBottom = y + ALTO_LEMMING - 1; // Cerca de los pies

        if (esTerrenoSolidoODestructible(checkXFront, checkYBottom)) {
            return true;
        }

        // 2. Detección de otros Lemmings bloqueadores
        for (Lemming otroLemming : todosLosLemmings) {
            // No compararse a sí mismo
            if (otroLemming == this) {
                continue;
            }
            // Solo si el otro Lemming está en estado BLOQUEANDO
            if (otroLemming.getEstadoActual() == EstadoLemming.BLOQUEANDO) {
                Rectangle otroLemmingRect = new Rectangle(otroLemming.x, otroLemming.y, ANCHO_LEMMING, ALTO_LEMMING);
                if (rectanguloLemming.intersects(otroLemmingRect)) {
                    boolean hayBloqueadorDelante = false;
                    if (direccionDerecha) {
                        // El bloqueador debe estar a la derecha de la posición ACTUAL del Lemming
                        if (otroLemming.x >= this.x + ANCHO_LEMMING / 2) {
                            hayBloqueadorDelante = true;
                        }
                    } else { // dirección izquierda
                        // El bloqueador debe estar a la izquierda de la posición ACTUAL del Lemming
                        if (otroLemming.x + ANCHO_LEMMING <= this.x + ANCHO_LEMMING / 2) {
                            hayBloqueadorDelante = true;
                        }
                    }
                    if (hayBloqueadorDelante) {
                        return true; // Hay una pared delante (un Lemming bloqueador)
                    }
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
                    puedePlanear = true;
                    Sonido.reproducir("agregarHabilidad.wav");
                    break;
                case BOMBA:
                    setEstado(EstadoLemming.EXPLOTANDO);
                    Sonido.reproducir("Bomba.wav");
                    break;
                case ESCALADOR:
                    setEstado(EstadoLemming.CAMINANDO);
                    Sonido.reproducir("agregarHabilidad.wav");
                    puedeEscalar = true;
                    break;
                case BLOQUEADOR:
                    setEstado(EstadoLemming.BLOQUEANDO);
                    Sonido.reproducir("agregarHabilidad.wav");
                    break;
            }
    }

    private boolean tocaLava() {
        int checkXCenter = this.x + ANCHO_LEMMING / 2;
        // El píxel justo en la base o un píxel por debajo, para detectar si "cae" en la lava
        int checkYBottom = this.y + ALTO_LEMMING;
        return nivelActual.getTipoTile(checkXCenter, checkYBottom) == 3;
    }

    public static void agregarLemming(Lemming nuevoLemming) {
        todosLosLemmings.add(nuevoLemming);
    }
}