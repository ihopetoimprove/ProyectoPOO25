package poo.pong;
import poo.ObjetoGrafico;

public class Arco extends ObjetoGrafico {
    public Arco(int x, int y) {
        super(x, y);
    }

    @Override
    public void dibujar() {

    }
    public boolean detectarColision(){
        return false;
    }
    public int detectarGol(){
        return 0;
    }
}
