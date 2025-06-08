package poo.pong;
import poo.ClasesCompartidas.ObjetoMovible;
import java.util.Random;
import java.awt.*;

public class    Pelota extends ObjetoMovible {

    private final int radio = 10;
    int velocidadX = 5;
    int velocidadY = 5;
    private final Random random = new Random();
    boolean enMovimiento=true;


    public Pelota(int x, int y) {
        super(x,y);
    }

    @Override
    public void mover(double delta) {
        if (enMovimiento) {
            setX(x+velocidadX);
            setY(y+velocidadY);
        }
    }
    public void pararPelota(){
        this.enMovimiento = false;
    }
    public void reanudarPelota() {
        this.enMovimiento = true;
    }

    public void reiniciarPelota(){
        setX(400);
        setY(250);
        velocidadX = 5;
        velocidadY = 5;
        velocidadX = velocidadX * (random.nextBoolean() ? 1 : -1);
        velocidadY = velocidadY * (random.nextBoolean() ? 1 : -1);
    }

    public void aumentarVelocidad1(){
        velocidadX = velocidadX+2;
        velocidadY = velocidadY+2;
    }
    public void aumentarVelocidad2(){
        velocidadX = velocidadX-2;
        velocidadY = velocidadY-2;
    }

    public void invertirVelocidadY(){
        velocidadY = -1*velocidadY;
    }

    public void invertirVelocidadX(){
        velocidadX = -1*velocidadX;
    }

    public int getRadio(){
        return radio;
    }

    @Override
    public void dibujar(Graphics2D g) {
        g.fillOval( getX(), getY(), 2 * radio, 2 * radio);
    }

}


