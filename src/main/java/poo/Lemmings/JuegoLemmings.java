package poo.Lemmings;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Log;
import com.entropyinteractive.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class JuegoLemmings extends JGame{

    public enum EstadoJuego{INICIO, JUGANDO, FIN}

    private EstadoJuego estadoJuego = EstadoJuego.INICIO;
    private Nivel nivelActual;
    //private List<Lemming> lemmings;
    private String[] nombresNiveles = {"nivel1.txt", "nivel2.txt", "nivel3,txt"};

    public JuegoLemmings(String title, int width, int height) {
        super(title, width, height);
    }

    @Override
    public void gameStartup(){

    }

    @Override
    public void gameUpdate(double v){
        Keyboard keyboard = this.getKeyboard();
        Mouse mouse = this.getMouse();

        if(estadoJuego == EstadoJuego.JUGANDO){
            //logica de jugando
        }


    }

    @Override
    public void gameDraw(Graphics2D g) {
        if(estadoJuego == EstadoJuego.INICIO){
            estadoInicio(g);
        }
        if(estadoJuego == EstadoJuego.JUGANDO){

        }
    }

    @Override
    public void gameShutdown() {
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }

    public void estadoInicio(Graphics2D g){
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Iniciar nivel 1", 100, 400);
        g.drawString("Iniciar nivel 2", 350, 400);
        g.drawString("Iniciar nivel 3", 600, 400);
    }

}