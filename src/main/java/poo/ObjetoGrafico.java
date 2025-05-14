package poo;

public abstract class ObjetoGrafico {
    private int x;
    private int y;

    public ObjetoGrafico(int x, int y){
        this.x = x;
        this.y = y;
    }

    public abstract boolean detectarColision();
    //puede ser de utilidad
    public abstract void dibujar();


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
