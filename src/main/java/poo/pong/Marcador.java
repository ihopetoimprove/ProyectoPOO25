package poo.pong;

public class Marcador {
    private short puntosP1;
    private short puntosP2;

    public Marcador(){
        puntosP1 = 0;
        puntosP2 = 0;
    }

    public void sumarGol(short jugador){
        if (jugador == 1) {
            puntosP1++;
        }else if(jugador == 2){
                puntosP2++;
        }else {System.out.println("Jugador inválido, debe ser 1 o 2.");}
    }

    public void reiniciarMarcador(){
        this.puntosP1 = 0;
        this.puntosP2 = 0;
    }
}
