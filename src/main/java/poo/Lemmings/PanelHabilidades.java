package poo.Lemmings;

import com.entropyinteractive.Mouse;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class PanelHabilidades {


    public enum TipoHabilidad {NINGUNA, ESCALADOR, NUKE, BLOQUEADOR, BOMBA, PARAGUAS, PAUSA, ACELERAR_JUEGO}
    private TipoHabilidad habilidadSeleccionada = TipoHabilidad.NINGUNA;
    private static BufferedImage imagenHabilidades;
    private static int totalLemmings;
    private static int lemmingsVivos;
    private static int lemmingsASalvar;
    private static int lemmingsSalvados = 0;
    private int tiempoLimite;
    private int cantidadEscaladores;
    private int cantidadBloqueadores;
    private int cantidadBombas;
    private int cantidadParaguas;
    private Mouse mouse;
    private boolean mousePrevEstado = false;

    public PanelHabilidades(Mouse mouse){
    this.mouse = mouse;
    }

    public PanelHabilidades(String rutaNivel, Mouse mouse) {
        cargarPanelNivel("/src/main/resources/niveles/" + rutaNivel);
        this.mouse = mouse;
    }

    public void dibujar(Graphics2D g){
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 500, 820, 120);
        g.drawImage(imagenHabilidades, 10, 500, null);
        g.setColor(Color.black);
        g.drawString("Lemmings salvados: " + getLemmingsSalvados() + " / " + getLemmingsASalvar(), 620, 550);
        g.drawString("Tiempo restante: " + Temporizador.getTiempoRestante(), 620, 570);
        g.drawString("Lemmings restantes: " + getLemmingsVivos(), 620, 530);
        g.drawString(String.valueOf(cantidadParaguas), 45, 525);
        g.drawString(String.valueOf(cantidadBombas), 132, 525);
        g.drawString(String.valueOf(cantidadBloqueadores),220, 525);
        g.drawString(String.valueOf(cantidadEscaladores), 305, 525);
        g.drawString("Pausa", 370,520);
        g.drawString("Nuke", 460,520);
        g.drawString("Acelerar", 540,520);
        seleccionarHabilidad();
        dibujarHabilidadSeleccionada(g);
    }

    public static void limpiarPanel(){
        lemmingsSalvados = 0;
        lemmingsASalvar = 0;
        totalLemmings = 0;
    }

    public static void salvarLemming(){lemmingsSalvados +=1;}

    public int getTotalLemmings() {return totalLemmings;}
    public int getLemmingsSalvados() {return lemmingsSalvados;}
    public int getLemmingsASalvar() {return lemmingsASalvar;}
    public int getTiempoLimite() {return tiempoLimite;}

    public void seleccionarHabilidad(){
        if (mouse.isLeftButtonPressed()) {
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            if (mouseY >= 480){
                if (mouseX >= 10 && mouseX <= 85) {
                    habilidadSeleccionada = TipoHabilidad.PARAGUAS;
                } else if (mouseX >= 90 && mouseX <= 170){
                    habilidadSeleccionada = TipoHabilidad.BOMBA;
                } else if (mouseX > 175 && mouseX < 250){
                    habilidadSeleccionada = TipoHabilidad.BLOQUEADOR;
                } else if (mouseX >= 260 && mouseX <= 360){
                    habilidadSeleccionada = TipoHabilidad.ESCALADOR;
                } else if (mouseX >=430 && mouseX <= 510){
                    habilidadSeleccionada = TipoHabilidad.NUKE;
                } else if (mouseX >= 370 && mouseX <= 420 ){
                    habilidadSeleccionada = TipoHabilidad.PAUSA;
                } else if (mouseX >= 515 && mouseX <= 600 ){
                    habilidadSeleccionada = TipoHabilidad.ACELERAR_JUEGO;
                }
            }
        }
    }

    public void dibujarHabilidadSeleccionada(Graphics2D g){
        if (habilidadSeleccionada == TipoHabilidad.PARAGUAS){
        g.drawRect(15, 505, 70, 95);
        } else if (habilidadSeleccionada == TipoHabilidad.BOMBA){
            g.drawRect(100, 505, 70, 95);
        } else if (habilidadSeleccionada == TipoHabilidad.BLOQUEADOR){
            g.drawRect(185, 505, 70, 95);
        } else if (habilidadSeleccionada == TipoHabilidad.ESCALADOR){
            g.drawRect(270, 505, 70, 95);
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
            case ESCALADOR:
                cantidadEscaladores--;
                break;
            case BLOQUEADOR:
                cantidadBloqueadores--;
                break;
        }
    }
        public TipoHabilidad getHabilidadActivadaUnaVez() {
            TipoHabilidad habilidadActivada = null;

            boolean mouseActualEstado = mouse.isLeftButtonPressed();
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();

            if (mouseActualEstado && !mousePrevEstado) { // Detecta un clic nuevo
                if (mouseY >= 480) { // Zona de botones de habilidad
                    if (mouseX >= 10 && mouseX <= 85) {
                        habilidadActivada = TipoHabilidad.PARAGUAS;
                    } else if (mouseX >= 90 && mouseX <= 170) {
                        habilidadActivada = TipoHabilidad.BOMBA;
                    } else if (mouseX > 175 && mouseX < 250) {
                        habilidadActivada = TipoHabilidad.BLOQUEADOR;
                    } else if (mouseX >= 260 && mouseX <= 360) {
                        habilidadActivada = TipoHabilidad.ESCALADOR;
                    } else if (mouseX >= 430 && mouseX <= 510) {
                        habilidadActivada = TipoHabilidad.NUKE;
                    } else if (mouseX >= 370 && mouseX <= 420) { // Consolidado
                        habilidadActivada = TipoHabilidad.PAUSA;
                    } else if (mouseX >= 520 && mouseX <= 590) { // Un ejemplo si tienes ACELERAR_JUEGO
                        habilidadActivada = TipoHabilidad.ACELERAR_JUEGO;
                    }
                }
            }

            mousePrevEstado = mouseActualEstado; // Actualiza el estado del mouse para el próximo frame
            return habilidadActivada; // Retorna la habilidad que fue clickeada ÚNICAMENTE en este frame, o null
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
                        lemmingsVivos = totalLemmings;
                    } else if (linea.startsWith("#LEMMINGS_SALVAR:")) {
                        lemmingsASalvar = Integer.parseInt(linea.split(":")[1].trim());
                    } else if (linea.startsWith("#TIEMPO_LIMITE:")) {
                        tiempoLimite = Integer.parseInt(linea.split(":")[1].trim());
                    } else if (linea.startsWith("#ESCALADOR:")) {
                        cantidadEscaladores = Integer.parseInt(linea.split(":")[1].trim());
                    } else if (linea.startsWith("#PARAGUAS:")) {
                        cantidadParaguas = Integer.parseInt(linea.split(":")[1].trim());
                    } else if (linea.startsWith("#BOMBA:")) {
                        cantidadBombas = Integer.parseInt(linea.split(":")[1].trim());
                    } else if (linea.startsWith("#BLOQUEADOR:")) {
                        cantidadBloqueadores = Integer.parseInt(linea.split(":")[1].trim());
                    }
                    try {
                        imagenHabilidades = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/Lemmings/Habilidades.png")));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public int getCantidadHabilidad(TipoHabilidad habilidad) {
        return switch (habilidad) {
            case PARAGUAS -> cantidadParaguas;
            case BOMBA -> cantidadBombas;
            case ESCALADOR -> cantidadEscaladores;
            case BLOQUEADOR -> cantidadBloqueadores;
            default -> 0; // O manejar un error si NINGUNA u otra habilidad se consulta
        };
    }

    public void eliminarLemming() {lemmingsVivos--;}
    public int getLemmingsVivos(){return lemmingsVivos;}
    public TipoHabilidad getHabilidadSeleccionada() {return this.habilidadSeleccionada; }
}
