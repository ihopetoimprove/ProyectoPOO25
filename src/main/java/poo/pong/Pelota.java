package poo.pong;

import poo.ObjetoMovible;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Pelota extends ObjetoMovible {
    private int radio = 10;
    int velocidadX = 5;
    int velocidadY = 5;

    public Pelota(int x, int y) {
        super(x,y);
    }

    @Override
    public void mover() {
        setX(x+velocidadX);
        setY(y+velocidadY);
    }

    public void invertirVelocidadY(){
        velocidadY = -1*velocidadY;
    }

    public void invertirVelocidadX(){
        velocidadX = -1*velocidadX;
    }

    public int getRadio(){
        return radio;
    }

    @Override
    public void dibujar(Graphics2D g) {
        g.fillOval((int) getX(), (int) getY(), 2 * radio, 2 * radio);
    }

}


