package poo.Lemmings;

public class Temporizador {
    private int tiempoRestante;
    private boolean pausa = true;

    public Temporizador(int tiempoLimite){
        this.tiempoRestante = tiempoLimite;
    }

    public void iniciar(){
        this.pausa = false;
    }

    public void pausar(){
        this.pausa = true;
    }

}
