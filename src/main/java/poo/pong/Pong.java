package poo.pong;


import com.entropyinteractive.JGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Pong extends JGame {


    public Pong(String title, int width, int height) {
        super(title, width, height);
    }
        Button Jugadores=new Button("Jugadores");
        BufferedImage imagen = null;

    @Override
    public void gameStartup() {

        try{
            imagen= ImageIO.read(getClass().getResource("imagenes/fondo.jpg"));
//            ovni.setImagen(ImageIO.read(getClass().getResource("imagenes/ufo.png")));
//            ovni.setPosicion(getWidth() / 2,getHeight() / 2 );
        }
        catch(Exception e){

        }
    }

    @Override
    public void gameUpdate(double v) {

    }

    @Override
    public void gameDraw(Graphics2D g) {
        g.drawImage(imagen,0,0,null);
    }

    @Override
    public void gameShutdown() {

    }


    public boolean detectarColision() {
        return false;
    }

    public void dibujar() {

    }
}
