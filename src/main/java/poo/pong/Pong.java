package poo.pong;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Pong extends JGame {

    Paleta paletaIzq = new Paleta(10,140);
    Paleta paletaDer = new Paleta(780,140);
    Pelota pelota = new Pelota(400, 200);

    public Pong(String title, int width, int height) {
        super(title, width, height);
    }
        Button Jugadores = new Button("Jugadores");
        BufferedImage fondo = null;

    @Override
    public void gameStartup() {

        try{
            fondo = ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/fondo.png"));
//            ovni.setImagen(ImageIO.read(getClass().getResource("imagenes/ufo.png")));
            //pelota.setPosicion( getWidth() / 2, getHeight() / 2);
        }
        catch(Exception e){
            System.out.println("La excepcion " + e + " ha ocurrido.");
        }
    }

    @Override
    public void gameUpdate(double v) {
        Keyboard keyboard = this.getKeyboard();
        procesarTeclado();
        pelota.mover();
    }

    @Override
    public void gameDraw(Graphics2D g) {

        g.drawImage(fondo,0,0,null);
        paletaIzq.dibujar(g);
        paletaDer.dibujar(g);
        pelota.dibujar(g);
        //      Ejemplos de cosas para poner en la pantalla
        //g.setColor(Color.black);
        //g.setFont(new Font("Pixel Emulator", Font.PLAIN, 16));
    }

    @Override
    public void gameShutdown() {
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }

    public void procesarTeclado(){
        Keyboard keyboard = this.getKeyboard();

        // Mover la paleta derecha hacia arriba si la tecla está presionada y no está en el borde superior
        if (keyboard.isKeyPressed(KeyEvent.VK_UP) && paletaDer.getY() > 0) {
            paletaDer.setY((int) (paletaDer.getY() - paletaDer.getVelocidadY() ));
        }

        // Mover la paleta derecha hacia abajo si la tecla está presionada y no está en el borde inferior
        if (keyboard.isKeyPressed(KeyEvent.VK_DOWN) && paletaDer.getY() < getHeight() - 100) {
            paletaDer.setY((int) (paletaDer.getY() + paletaDer.getVelocidadY() ));
        }

        // Mover la paleta izquierda hacia arriba si la tecla está presionada y no está en el borde superior
        if (keyboard.isKeyPressed(KeyEvent.VK_W) && paletaIzq.getY() > 0) {
            paletaIzq.setY((int) (paletaIzq.getY() - paletaIzq.getVelocidadY() ));
        }

        // Mover la paleta izquierda hacia abajo si la tecla está presionada y no está en el borde inferior
        if (keyboard.isKeyPressed(KeyEvent.VK_S) && paletaIzq.getY() < getHeight() - 100) {
            paletaIzq.setY((int) (paletaIzq.getY() + paletaIzq.getVelocidadY() ));
        }
    }

    public void detectarColision() {

    }

}
