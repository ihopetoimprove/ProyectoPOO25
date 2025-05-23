package poo.pong;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;
import poo.Config;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Pong extends JGame {

    Config cPong= new Config();
    BufferedImage fondo = null;
    Paleta paletaIzq = new Paleta(10,140);
    Paleta paletaDer = new Paleta(780,140);
    Pelota pelota = new Pelota(400, 250);
    Marcador marcador = new Marcador();
    private int ganador;
    private int estadoJuego = 0;
    public final static int START = 0;
    public final static int JUGANDO = 1;
    public final static int FIN = 2;

    public Pong(String title, int width, int height) {
        super(title, width, height);
        cPong.setVisible(false);
    }

    @Override
    public void gameStartup() {

        try{
            fondo = ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/fondo.png"));
        }
        catch(Exception e){
            System.out.println("La excepcion " + e + " ha ocurrido.");
        }
    }

    @Override
    public void gameUpdate(double v) {
        if (estadoJuego == JUGANDO) {
            procesarTeclado();
            detectarColision();
            pelota.mover();
            anotarGol();
        }
    }

    @Override
    public void gameDraw(Graphics2D g) {
        g.drawImage(fondo,0,0,null);
        if(estadoJuego == START){
            estadoStart(g);
        }
        if(estadoJuego == FIN){
            estadoFin(g);
        }
        paletaIzq.dibujar(g);
        paletaDer.dibujar(g);
        pelota.dibujar(g);
        marcador.dibujar(g);
    }

    @Override
    public void gameShutdown() {
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }

    public void procesarTeclado(){
        Keyboard keyboard = this.getKeyboard();

        // Mover la paleta derecha hacia arriba si la tecla está presionada y no está en el borde superior
        if (keyboard.isKeyPressed(cPong.getTSubirJ2()) && paletaDer.getY() > 0)
            paletaDer.setY((paletaDer.getY() - paletaDer.getVelocidadY() ));

        // Mover la paleta derecha hacia abajo si la tecla está presionada y no está en el borde inferior
        if (keyboard.isKeyPressed(cPong.getTBajarJ2()) && paletaDer.getY() < getHeight() - 100)
            paletaDer.setY((paletaDer.getY() + paletaDer.getVelocidadY() ));

        // Mover la paleta izquierda hacia arriba si la tecla está presionada y no está en el borde superior
        if (keyboard.isKeyPressed(cPong.getTSubirJ1()) && paletaIzq.getY() > 0)
            paletaIzq.setY( (paletaIzq.getY() - paletaIzq.getVelocidadY() ));

        // Mover la paleta izquierda hacia abajo si la tecla está presionada y no está en el borde inferior
        if (keyboard.isKeyPressed(cPong.getTBajarJ1()) && paletaIzq.getY() < getHeight() - 100)
            paletaIzq.setY( (paletaIzq.getY() + paletaIzq.getVelocidadY() ));

    }

    public void detectarColision() {
        //si la paleta choca contra el techo
        if (pelota.getY() <= getHeight())
            pelota.invertirVelocidadY();

        //piso
        if (pelota.getY() >= 0)
            pelota.invertirVelocidadY();

        //paletas
        if (pelota.getX() <= paletaIzq.getX() + paletaIzq.getAncho() &&
                pelota.getX() + pelota.getRadio() * 2 >= paletaIzq.getX() &&
                pelota.getY() <= paletaIzq.getY() + paletaIzq.getLargo() &&
                pelota.getY() + pelota.getRadio() * 2 >= paletaIzq.getY()) {
            pelota.invertirVelocidadX();
            pelota.aumentarVelocidad();
        }
        if (pelota.getX() <= paletaDer.getX() + paletaDer.getAncho() &&
                pelota.getX() + pelota.getRadio() * 2 >= paletaDer.getX() &&
                pelota.getY() <= paletaDer.getY() + paletaDer.getLargo() &&
                pelota.getY() + pelota.getRadio() * 2 >= paletaDer.getY()) {
            pelota.invertirVelocidadX();
            pelota.aumentarVelocidad();
        }

    }

    public void anotarGol(){
        if (pelota.getX() < 0) {
            marcador.sumarGolJugador2();
            pelota.reiniciarPelota();
        } else if (pelota.getX() > 800) {
            marcador.sumarGolJugador1();
            pelota.reiniciarPelota();
        }
        if(marcador.getPuntosP1() == 3){
            ganador = 1;
            estadoJuego = FIN;
        }
        if(marcador.getPuntosP2() == 3) {
            ganador = 2;
            estadoJuego = FIN;
        }
    }

    public void estadoStart(Graphics2D g){
        Keyboard keyboard = this.getKeyboard();
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Apreta barra espaciadora para comenzar", 150, 300);
        g.drawString("¡Buena suerte!", 300, 400);

        if (keyboard.isKeyPressed(KeyEvent.VK_SPACE))
            estadoJuego = 1;

    }

    public void estadoFin(Graphics2D g){
        Keyboard keyboard = this.getKeyboard();
        marcador.reiniciarMarcador();
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("El jugador " + ganador + " ha ganado!", 250, 300);
        g.drawString("Presiona barra espaciadora para volver a jugar", 100, 400);

        if (keyboard.isKeyPressed(KeyEvent.VK_SPACE))
            estadoJuego = 1;
    }
}
