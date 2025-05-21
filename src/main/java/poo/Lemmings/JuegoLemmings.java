package poo.Lemmings;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Log;

import java.awt.*;
import java.awt.image.BufferedImage;

public class JuegoLemmings extends JGame{

    public enum EstadoJuego{INICIO, JUGANDO, FIN}

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

    }

    @Override
    public void gameDraw(Graphics2D g) {

    }

    @Override
    public void gameShutdown() {
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }
}
