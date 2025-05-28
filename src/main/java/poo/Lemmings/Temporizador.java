package poo.Lemmings;

public class Temporizador {
    private int tiempoRestante = 500;
    private boolean pausa = false;

    public Temporizador(){}

    public int getTiempoRestante(){return tiempoRestante;}

    public void iniciar(){
        this.pausa = false;
    }

    public void pausar(){
        this.pausa = true;
    }

}
