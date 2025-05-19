package poo;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
public abstract class ObjetoGrafico {
    protected BufferedImage imagen = null;

    protected int x;
    protected int y;
    protected int largo;
    protected int ancho;

    public ObjetoGrafico(int x, int y){
        this.x = x;
        this.y = y;
    }

    public abstract void detectarColision();
    //puede ser de utilidad
    public abstract void dibujar(Graphics2D g);



    // sets y gets...
    public int getX(){
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
