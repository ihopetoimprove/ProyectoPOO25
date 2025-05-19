package poo.pong;

import com.entropyinteractive.Keyboard;
import poo.ObjetoMovible;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
    public void mover() {
    }

    public int getAncho(){
        return ancho;
    }

    public int getLargo(){
        return largo;
    }

}
