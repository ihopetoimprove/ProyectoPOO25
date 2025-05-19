package poo.pong;
import poo.ObjetoGrafico;

import java.awt.*;

public class Arco extends ObjetoGrafico {
    public Arco(int x, int y) {
        super(x, y);
    }

    @Override
    public void dibujar(Graphics2D g) {

    }
    public void detectarColision(){
    }
    public int detectarGol(){
        return 0;
    }
}
