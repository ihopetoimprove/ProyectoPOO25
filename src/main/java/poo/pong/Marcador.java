package poo.pong;

import java.awt.*;

public class Marcador {
    private short puntosP1;
    private short puntosP2;

    public Marcador(){
        puntosP1 = 0;
        puntosP2 = 0;
    }

    public void sumarGolJugador1(){
        puntosP1++;
    }

    public void sumarGolJugador2(){
        puntosP2++;
    }

    public void dibujar(Graphics2D g){
        g.setFont(new Font("Arial", Font.PLAIN, 60));
        g.drawString(String.valueOf(puntosP1), 330, 100);
        g.drawString(String.valueOf(puntosP2), 430, 100);
    }

    public void reiniciarMarcador(){
        this.puntosP1 = 0;
        this.puntosP2 = 0;
    }
}
