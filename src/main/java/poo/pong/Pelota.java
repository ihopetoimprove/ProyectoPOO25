package poo.pong;

import poo.ObjetoMovible;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Pelota extends ObjetoMovible {
    private int radio = 10;

    public Pelota() {
        super(400, 300);
        try {
            imagen = ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/pelota.png")); // Reemplaza con la ruta correcta
        } catch (IOException e) {
            System.err.println("Error al cargar la imagen de la paleta: " + e);
            imagen = null;
        }
    }

    @Override
    public void mover() {

    }

    @Override
    public boolean detectarColision() {
        return false;
    }

    @Override
    public void dibujar(Graphics2D g) {
        g.drawImage(imagen,(int)posicion.getX(),(int)posicion.getY(),null);
        //g.fillOval((int) getX(), (int) getY(), 2 * radio, 2 * radio);
    }

    public void setPosicion(double x, double y){
        posicion.setLocation(x,y);
    }
}
