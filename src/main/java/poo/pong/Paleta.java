package poo.pong;

import poo.ObjetoMovible;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Paleta extends ObjetoMovible {

    public Paleta(int x, int y) {
        super(x, y);
        largo = 100;
        ancho = 25;

    }

    @Override
    public void dibujar(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.fillRect(x,y,ancho, largo);
        //g.drawImage(imagen,(int)posicion.getX(),(int)posicion.getY(),null);
    }

    public void setPosicion(double x, double y){
        posicion.setLocation(x,y);
    }

    @Override
    public void mover() {

    }

    @Override
    public boolean detectarColision() {
        return false;
    }
}
