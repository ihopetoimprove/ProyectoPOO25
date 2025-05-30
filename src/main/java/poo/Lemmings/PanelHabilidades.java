package poo.Lemmings;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class PanelHabilidades{

    private static BufferedImage imagenHabilidades;
    private Temporizador temporizador = new Temporizador();
    private static int totalLemmings;
    private static int lemmingsASalvar;
    private static int lemmingsSalvados = 0;
    private int tiempoLimite;
    private int cantidadExcavadores;
    private int cantidadBloqueadores;
    private int cantidadBombas;
    private int cantidadParaguas;

    public PanelHabilidades(String rutaNivel) {
        cargarPanelNivel("/src/main/resources/niveles/" + rutaNivel);
    }

    public void dibujar(Graphics2D g, Nivel rutaNivel){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 500, 820, 120);
        g.drawImage(imagenHabilidades, 100, 500, null);
        g.setColor(Color.black);
        g.drawString("Lemmings salvados: " + getLemmingsSalvados() + " / " + getLemmingsASalvar(), 600, 550);
        g.drawString("Tiempo restante: " + String.valueOf(tiempoLimite), 600, 570);
        g.drawString(String.valueOf(cantidadParaguas), 135, 525);
        g.drawString(String.valueOf(cantidadBombas), 222, 525);
        g.drawString(String.valueOf(cantidadBloqueadores),310, 525);
        g.drawString(String.valueOf(cantidadExcavadores), 395, 525);

    }

    public static void limpiarPanel(){
        lemmingsSalvados = 0;
        lemmingsASalvar = 0;
        totalLemmings = 0;
    }

    public static void salvarLemming(){
        lemmingsSalvados +=1 ;
    }

    public static int getTotalLemmings() {return totalLemmings;}
    public static int getLemmingsSalvados() {return lemmingsSalvados;}
    public static int getLemmingsASalvar() {return lemmingsASalvar;}
    public int getTiempoLimite() {return tiempoLimite;}

    public void cargarPanelNivel(String rutaNivel) {
        String currentDir = System.getProperty("user.dir");
        rutaNivel = currentDir + rutaNivel;

        try {
            String linea;
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(rutaNivel));

            while ((linea = reader.readLine()) != null) {
                linea = linea.trim(); // Eliminar espacios al inicio y fin de la línea
                if (linea.isEmpty()) {
                    continue; // Saltar líneas vacías
                }

                if (linea.startsWith("#")) { // Líneas de configuración (metadatos del nivel)
                    if (linea.startsWith("#LEMMINGS_TOTAL:")) {
                        totalLemmings = Integer.parseInt(linea.split(":")[1].trim());
                    } else if (linea.startsWith("#LEMMINGS_SALVAR:")) {
                        lemmingsASalvar = Integer.parseInt(linea.split(":")[1].trim());
                    } else if (linea.startsWith("#TIEMPO_LIMITE:")) {
                        tiempoLimite = Integer.parseInt(linea.split(":")[1].trim());
                    } else if (linea.startsWith("#EXCAVADOR:")) {
                        cantidadExcavadores = Integer.parseInt(linea.split(":")[1].trim());
                    } else if (linea.startsWith("#PARAGUAS:")) {
                        cantidadParaguas = Integer.parseInt(linea.split(":")[1].trim());
                    } else if (linea.startsWith("#BOMBA:")) {
                        cantidadBombas = Integer.parseInt(linea.split(":")[1].trim());
                    } else if (linea.startsWith("#BLOQUEADOR:")) {
                        cantidadBloqueadores = Integer.parseInt(linea.split(":")[1].trim());
                    }
                    try {
                        imagenHabilidades = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/Habilidades.png")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
