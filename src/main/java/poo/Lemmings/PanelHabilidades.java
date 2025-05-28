package poo.Lemmings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.AttributedCharacterIterator;
import java.util.Objects;
import javax.imageio.ImageIO;
import javax.swing.*;

public class PanelHabilidades{

    private static BufferedImage imagenBloqueador;
    private static BufferedImage imagenExcavador;
    private static BufferedImage imagenCayendo;

    private Temporizador temporizador = new Temporizador();
    private int salvados = 0;

    public PanelHabilidades(){
        cargarImagenesPanel();
    }

    public void cargarImagenesPanel(){
        try {
            imagenExcavador = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/excavador.png")));
            imagenCayendo = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/cayendo.png")));
            imagenBloqueador = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/bloqueador.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void dibujar(Graphics2D g, Nivel nivel){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 500, 820, 120);
        g.setColor(Color.black);
        g.drawString("Lemmings salvados: " + nivel.getLemmingsSalvados() + " / " + nivel.getLemmingsASalvar(), 600, 550);
        g.drawString(String.valueOf("Tiempo restante: " + temporizador.getTiempoRestante()), 600, 570);
        g.drawImage(imagenExcavador, 200, 530, null);
        g.drawImage(imagenCayendo, 100, 530, null);
        g.drawImage(imagenBloqueador, 300, 530, null);
    }
}
