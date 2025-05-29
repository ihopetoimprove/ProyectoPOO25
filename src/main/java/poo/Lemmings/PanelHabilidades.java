package poo.Lemmings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelHabilidades{

    private static BufferedImage imagenHabilidades;
    private Temporizador temporizador = new Temporizador();
    private int salvados = 0;

    public PanelHabilidades(){
        cargarImagenesPanel();
    }

    public void cargarImagenesPanel(){
        try {
            imagenHabilidades = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/Habilidades.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dibujar(Graphics2D g, Nivel nivel, int tiempoLimite, int cantidadExcavadores){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 500, 820, 120);
        g.drawImage(imagenHabilidades, 100, 500, null);
        g.setColor(Color.black);
        g.drawString("Lemmings salvados: " + nivel.getLemmingsSalvados() + " / " + nivel.getLemmingsASalvar(), 600, 550);
        g.drawString("Tiempo restante: " + String.valueOf(tiempoLimite), 600, 570);
        g.drawString(String.valueOf(cantidadExcavadores), 395, 525);
    }
}
