package poo.Lemmings;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Log;

import java.awt.*;

public class JuegoLemmings extends JGame{

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
