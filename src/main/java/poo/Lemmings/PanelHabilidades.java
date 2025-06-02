package poo.Lemmings;

import com.entropyinteractive.Mouse;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class PanelHabilidades {

    public enum TipoHabilidad {NINGUNA, EXCAVADOR, BLOQUEADOR, BOMBA, PARAGUAS}
    private TipoHabilidad habilidadSeleccionada = TipoHabilidad.NINGUNA;
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
    private Mouse mouse;

    public PanelHabilidades(String rutaNivel, Mouse mouse) {
        cargarPanelNivel("/src/main/resources/niveles/" + rutaNivel);
        this.mouse = mouse;
    }

    public void dibujar(Graphics2D g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 500, 820, 120);
        g.drawImage(imagenHabilidades, 100, 500, null);
        g.setColor(Color.black);
        g.drawString("Lemmings salvados: " + getLemmingsSalvados() + " / " + getLemmingsASalvar(), 600, 550);
        g.drawString("Tiempo restante: " + Integer.toString(Temporizador.getTiempoRestante()), 600, 570);
        g.drawString(String.valueOf(cantidadParaguas), 135, 525);
        g.drawString(String.valueOf(cantidadBombas), 222, 525);
        g.drawString(String.valueOf(cantidadBloqueadores),310, 525);
        g.drawString(String.valueOf(cantidadExcavadores), 395, 525);
        seleccionarHabilidad(g);
        dibujarHabilidadSeleccionada(g);
    }

    public static void limpiarPanel(){
        lemmingsSalvados = 0;
        lemmingsASalvar = 0;
        totalLemmings = 0;
    }

    public static void salvarLemming(){lemmingsSalvados +=1;} ;

    public int getTotalLemmings() {return totalLemmings;}
    public int getLemmingsSalvados() {return lemmingsSalvados;}
    public int getLemmingsASalvar() {return lemmingsASalvar;}
    public int getTiempoLimite() {return tiempoLimite;}

    public void seleccionarHabilidad(Graphics2D g){
        if (mouse.isLeftButtonPressed()) {
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            if (mouseY >= 500){
                if (mouseX >= 100 && mouseX <= 175) {
                    habilidadSeleccionada = TipoHabilidad.PARAGUAS;
                } else if (mouseX >= 180 && mouseX <= 260){
                    habilidadSeleccionada = TipoHabilidad.BOMBA;
                } else if (mouseX > 265 && mouseX < 340){
                    habilidadSeleccionada = TipoHabilidad.BLOQUEADOR;
                } else if (mouseX >= 361 && mouseX <= 440){
                    habilidadSeleccionada = TipoHabilidad.EXCAVADOR;
                }
            }
        }
    }

    public void dibujarHabilidadSeleccionada(Graphics2D g){
        if (habilidadSeleccionada == TipoHabilidad.PARAGUAS){
        g.drawRect(105, 505, 70, 95);
        } else if (habilidadSeleccionada == TipoHabilidad.BOMBA){
            g.drawRect(190, 505, 70, 95);
        } else if (habilidadSeleccionada == TipoHabilidad.BLOQUEADOR){
            g.drawRect(275, 505, 70, 95);
        } else if (habilidadSeleccionada == TipoHabilidad.EXCAVADOR){
            g.drawRect(360, 505, 70, 95);
        }
    }

    public void decrementarHabilidad(TipoHabilidad habilidad){
        switch (habilidad){
            case PARAGUAS:
                cantidadParaguas--;
                break;
            case BOMBA:
                cantidadBombas--;
                break;
            case EXCAVADOR:
                cantidadExcavadores--;
                break;
            case BLOQUEADOR:
                cantidadBloqueadores--;
                break;
        }
    }

    public void cargarPanelNivel(String rutaNivel) {
        String currentDir = System.getProperty("user.dir");
        rutaNivel = currentDir + rutaNivel;

        try {
            String linea;
            BufferedReader reader = null;
            reader = new BufferedReader(new FileReader(rutaNivel));

            while ((linea = reader.readLine()) != null) {
                linea = linea.trim(); // Eliminaar espacios al inicio y fin de la línea
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


    public int getCantidadHabilidad(TipoHabilidad habilidad) {
        switch (habilidad) {
            case PARAGUAS:
                return cantidadParaguas;
            case BOMBA:
                return cantidadBombas;
            case EXCAVADOR:
                return cantidadExcavadores;
            case BLOQUEADOR:
                return cantidadBloqueadores;
            default:
                return 0; // O manejar un error si NINGUNA u otra habilidad se consulta
        }
    }

    public TipoHabilidad getHabilidadSeleccionada() {return this.habilidadSeleccionada; }
}
