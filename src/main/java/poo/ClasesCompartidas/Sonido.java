package poo.ClasesCompartidas;

import poo.Lemmings.ConfigLemmings;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Sonido {
    public static void reproducir(String archivo) {
        try {
            URL sonidoURL = Sonido.class.getClassLoader().getResource("Sonido/" + archivo);

            if (sonidoURL == null) {
                System.out.println("No se encontró el archivo de sonido: " + archivo);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(sonidoURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error al reproducir sonido: " + archivo);
            e.printStackTrace();
        }
    }
}

