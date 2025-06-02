package poo.Lemmings;

import javax.swing.Timer;

public class Temporizador {
    private static int tiempoRestante;
    private boolean pausa = false;

    public Temporizador(){}
    public Temporizador(int n){//recibe el tiempo de cada nivel
        tiempoRestante=n;
    }


    public static int getTiempoRestante(){return tiempoRestante;}
    public void restarTiempo(){
        tiempoRestante--;
    }

    public void iniciar(){
        this.pausa = false;
    }

    public void pausar(){
        this.pausa = true;
    }

}
