package poo.pong;

import poo.ObjetoMovible;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class Paleta extends ObjetoMovible {

    //Quizás esto deba tenerlo ObjetoMovible, hay que ver
    private Point2D.Double posicion  = new Point2D.Double();

    public Paleta() {
        super(200, 100);

    }

    @Override
    public void dibujar(Graphics2D g) {
        g.drawImage(imagen,(int)posicion.getX(),(int)posicion.getY(),null);
    }

    @Override
    public void mover() {

    }

    @Override
    public boolean detectarColision() {
        return false;
    }
}
