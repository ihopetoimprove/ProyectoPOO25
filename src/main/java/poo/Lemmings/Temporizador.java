package poo.Lemmings;

public class Temporizador {
    private static int tiempoRestante;
    private boolean pausa = false;
    private final int FPS_JUEGO =60;
    int f= FPS_JUEGO;

    public Temporizador(){}
    public Temporizador(int n){//recibe el tiempo de cada nivel
        tiempoRestante=n;
    }


    public static int getTiempoRestante(){return tiempoRestante;}
    public void restarTiempo(){
        tiempoRestante--;
    }

    public void controlarTemporizador(double velocidadJuego){
        for (int i = 0; i < velocidadJuego; i++) {
        f--;
        }

        if(f<=0) {
            tiempoRestante--;
            f=FPS_JUEGO;
        }
    }

    public void iniciar(){
        this.pausa = false;
    }

    public void pausar(){
        this.pausa = true;
    }

}
