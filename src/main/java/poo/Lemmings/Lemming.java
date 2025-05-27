package poo.Lemmings;

import poo.ObjetoMovible;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lemming extends ObjetoMovible {

    public static List<Lemming> getTodosLosLemmings() {
        return todosLosLemmings;
    }

    public enum EstadoLemming {
        CAMINANDO, CAYENDO, ESCALANDO, EXCAVANDO, BLOQUEANDO, FLOTANDO, CONSTRUYENDO, MURIENDO, SALVADO
    }

    private EstadoLemming estadoActual;
    private boolean direccionDerecha = true;
    private int habilidad;
    protected Nivel nivelActual;
    private static List<Lemming> todosLosLemmings = new ArrayList<>();
    private static final int VELOCIDAD_BASE = 5;
    private static final int VELOCIDAD_CAIDA = 20;
    private static final int ANCHO_LEMMING = 20;
    private static final int ALTO_LEMMING = 20;

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
            return;
        }
        boolean haySuelo = haySueloDebajo();

        if (estadoActual == EstadoLemming.CAMINANDO) {
            x += velocidadX;
        } else if (estadoActual == EstadoLemming.CAYENDO) {
            y += VELOCIDAD_CAIDA;
            if(haySuelo){
                setEstado(EstadoLemming.CAMINANDO);
            }
        }
    }

    @Override
    public void dibujar(Graphics2D g) {
        BufferedImage imagenLemming;
        try {
            imagenLemming = ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/mario.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (imagenLemming != null) {
            g.drawImage(imagenLemming, x, y, ANCHO_LEMMING, ALTO_LEMMING, null);
    }
}

    public void cambiarDireccion(){
        this.velocidadX = velocidadX*-1;
    }

    private boolean esTerrenoSolidoODestructible(int px, int py) {
        int tipo = nivelActual.getTipoTile(px, py);
        return tipo == 1 || tipo == 2; // 1: Sólido, 2: Destructible
    }

    private boolean haySueloDebajo() {
        // Check a point just below the bottom-center of the lemming
        int checkX = this.x + ANCHO_LEMMING / 2;
        int checkY = this.y + ALTO_LEMMING; // Base of the lemming

        // Check if there's terrain right at the lemming's base, or a pixel below it
        return esTerrenoSolidoODestructible(checkX, checkY) || esTerrenoSolidoODestructible(checkX, checkY + 1);
    }

    public static void agregarLemming(Lemming nuevoLemming) {
        todosLosLemmings.add(nuevoLemming);
    }

}