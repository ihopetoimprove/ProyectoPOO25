package poo.Lemmings;

import poo.ObjetoMovible;

import java.awt.*;

public class Lemming extends ObjetoMovible {

    public enum EstadoLemming {
        CAMINANDO, CAYENDO, ESCALANDO, EXCAVANDO, BLOQUEANDO, FLOTANDO, CONSTRUYENDO, MURIENDO, SALVADO
    }

    private EstadoLemming estadoActual;
    private boolean direccionDerecha = true;
    private int habilidad;
    protected Nivel nivel;
    private static final int VELOCIDAD_BASE = 10;
    private static final int VELOCIDAD_CAIDA = 50;


    public Lemming(int x, int y) {
        super(x, y);
        this.nivel = nivel;
        this.estadoActual = EstadoLemming.CAMINANDO;
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

    }

    @Override
    public void dibujar(Graphics2D g) {

    }
}
