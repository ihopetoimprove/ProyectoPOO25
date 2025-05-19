package poo.pong;

import poo.ObjetoMovible;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Paleta extends ObjetoMovible {

    public Paleta() {
        //esto no lo entiendo, habría que preguntarlo
        super(400, 300);
        try {
            imagen = ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/paleta.png")); // Reemplaza con la ruta correcta
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen de la paleta: " + e);
            imagen = null;
        }
    }


    @Override
    public void dibujar(Graphics2D g) {
        g.drawImage(imagen,(int)posicion.getX(),(int)posicion.getY(),null);
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
