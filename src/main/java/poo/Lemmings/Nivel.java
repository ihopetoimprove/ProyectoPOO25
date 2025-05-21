package poo.Lemmings;

import poo.ObjetoGrafico;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Nivel extends ObjetoGrafico {

    private int[][] terreno;
    private int entradaX, entradaY;
    private int salidaX, salidaY;
    private BufferedImage imagenTerrenoSolido;
    private BufferedImage imagenTerrenoDestructible;

    public Nivel(int x, int y){
        super(x, y);
    }

    @Override
    public void dibujar(Graphics2D g) {

    }
}
