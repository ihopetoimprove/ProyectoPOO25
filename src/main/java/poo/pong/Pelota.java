package poo.pong;

import poo.ObjetoMovible;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Pelota extends ObjetoMovible {
    private int radio = 10;
    int velocidadX = 10;
    int velocidadY = 10;

    public Pelota(int x, int y) {
        super(x,y);
    }

    @Override
    public void mover() {
        setX(x+velocidadX);
        setY(y+velocidadY);
    }

    @Override
    public void dibujar(Graphics2D g) {
        g.fillOval((int) getX(), (int) getY(), 2 * radio, 2 * radio);
    }

    public void setPosicion(double x, double y){
        posicion.setLocation(x,y);
    }

    @Override
    public void detectarColision() {
    }
}


