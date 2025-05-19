package poo;

import java.awt.geom.Point2D;

public abstract class ObjetoMovible extends ObjetoGrafico {

    protected int velocidadX;
    protected int velocidadY;
    protected boolean isColision;
    protected Point2D.Double posicion  = new Point2D.Double();

    public ObjetoMovible(int x, int y) {
        super(x,y);
    }

    public abstract void mover();
    public abstract boolean detectarColision();

    public void actualizar() {
        //mover(); // Llama al método abstracto mover para actualizar la posición
    }

    // Métodos getter y setter para la velocidad
    public int getVelocidadX() {
        return velocidadX;
    }

    public void setVelocidadX(int velocidadX) {
        this.velocidadX = velocidadX;
    }

    public int getVelocidadY() {
        return velocidadY;
    }

    public void setVelocidadY(int velocidadY) {
        this.velocidadY = velocidadY;
    }
}
