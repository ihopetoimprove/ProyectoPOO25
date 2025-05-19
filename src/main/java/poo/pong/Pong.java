package poo.pong;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Pong extends JGame {

    Paleta paletaIzq = new Paleta();

    public Pong(String title, int width, int height) {
        super(title, width, height);
    }
        Button Jugadores=new Button("Jugadores");
        BufferedImage imagen = null;

    @Override
    public void gameStartup() {

        try{
            imagen= ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/fondo.png"));
//            ovni.setImagen(ImageIO.read(getClass().getResource("imagenes/ufo.png")));
//            ovni.setPosicion(getWidth() / 2,getHeight() / 2 );
        }
        catch(Exception e){
            System.out.println("La excepcion " + e + " ha ocurrido.");
        }
    }

    @Override
    public void gameUpdate(double v) {
        Keyboard keyboard = this.getKeyboard();
    }

    @Override
    public void gameDraw(Graphics2D g) {

        g.drawImage(imagen,0,0,null);
        paletaIzq.dibujar(g);
        /*      Ejemplos de cosas para poner en la pantalla
        g.setColor(Color.black);
        g.setFont(new Font("Pixel Emulator", Font.PLAIN, 16));
        g.drawString("Tecla ESC = Fin del Juego ",502,62);
        g.setColor(Color.white);

        g.drawString("Tecla ESC = Fin del Juego ",500,60);
        g.setColor(Color.red);

        g.setFont(new Font("SNES", Font.PLAIN, 60));
        g.drawString("SNES Emulator  ",200,220);*/
    }

    @Override
    public void gameShutdown() {
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }


    public boolean detectarColision() {
        return false;
    }

    public void dibujar() {

    }
}
