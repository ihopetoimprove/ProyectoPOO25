package poo.pong;

import poo.ClasesCompartidas.ObjetoMovible;

import java.awt.*;

public class Paleta extends ObjetoMovible {

    public Paleta(int x, int y) {
        super(x, y);
        largo = 100;
        ancho = 25;
        velocidadY = 10;
        velocidadX = 10;

    }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(x,y,ancho, largo);
    }

    @Override
    public void mover(double delta) {
    }

    public int getAncho(){
        return ancho;
    }
    public int getLargo(){
        return largo;
    }

}
