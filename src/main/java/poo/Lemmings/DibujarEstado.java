package poo.Lemmings;

import com.entropyinteractive.Mouse;
import poo.BDJugador;

import javax.swing.*;
import java.awt.*;


public class DibujarEstado extends JFrame{

    private JuegoLemmings juegoLemmings;
    private short nivelesCompletados = 0;
    JTextField campoNombre;
    private BDJugador jugador;
    PanelHabilidades panel;
    private static int puntos = 0;

    public DibujarEstado(JuegoLemmings juegoLemmings, PanelHabilidades panelHabilidades) {
        this.juegoLemmings = juegoLemmings;
        this.panel = panelHabilidades;
    }

    public void estadoInicio(Graphics2D g) {
        Mouse mouse = juegoLemmings.getMouse();
        //Establecer fondo
        g.setColor(new Color(50, 50, 150));
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        String gameTitle = "¡LEMMINGS!";
        g.drawString(gameTitle, 255, 100);

        // campo de texto
        campoNombre = new JTextField("Ingresa tu nombre aquí");
        campoNombre.setBounds(100, 50, 200, 30); // Posición y tamaño
        campoNombre.setBackground(Color.black);
        add(campoNombre);


        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Introduce tu nombre:", 280, 250);

        // Dibujar el recuadro para el nombre
        g.setColor(Color.WHITE);
        g.drawRect(250, 280, 300, 40); // Recuadro para el texto

        // Dibujar el texto introducido
        g.setColor(Color.YELLOW);
        //String textToDisplay = currentNameInput.toString();
        //g.drawString(textToDisplay, 260, 308);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Iniciar nivel 1", 100, 400);
        g.drawString("Iniciar nivel 2", 350, 400);
        g.drawString("Iniciar nivel 3", 600, 400);
        if (mouse.isLeftButtonPressed()) { // BUTTON1 es el botón izquierdo del ratón
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();

            if (mouseY >= 340 && mouseY <= 380) { // Rango vertical para todos los botones
                if (mouseX >= 0 && mouseX <= 200) { // "Iniciar nivel 1"
                    juegoLemmings.setNivelSeleccionado(0);
                    juegoLemmings.seleccionarNivel();
                }
                if (mouseX >= 250 && mouseX <= 450){
                    juegoLemmings.setNivelSeleccionado(1);
                    juegoLemmings.seleccionarNivel();
                }
                if (mouseX >= 500 && mouseX <= 700){
                    juegoLemmings.setNivelSeleccionado(2);
                    juegoLemmings.seleccionarNivel();
                }
            }
        }
    }

    public void estadoPerdio(Graphics2D g) {
        Mouse mouse = juegoLemmings.getMouse();
        g.setColor(new Color(50, 50, 150));
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Has perdido", 240, 200);
        g.drawString("Intentar de nuevo", 50, 500);
        if (mouse.isLeftButtonPressed()) { // BUTTON1 es el botón izquierdo del ratón
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            if (mouseX >= 0 && mouseX <= 350  && mouseY >= 400 && mouseY <= 600){
                juegoLemmings.seleccionarNivel();
            }
        }
    }

    public void estadoGana(Graphics2D g) {
        Mouse mouse = juegoLemmings.getMouse();
        if (nivelesCompletados == 3) {
            juegoLemmings.finDelJuego();
        }
        sumarPuntos();
        g.setColor(new Color(50, 50, 150));
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Muy bien ganaste!", 240, 100);
        g.drawString("-Niveles completados: " + nivelesCompletados + " / 3", 20, 200);
        g.drawString("-Lemmings salvados: " + panel.getLemmingsSalvados(), 20, 250);
        g.drawString("-Tiempo restante: " + Temporizador.getTiempoRestante(), 20, 300);
        g.drawString("-Puntos totales: " + puntos, 20, 350);
        g.drawString("Jugar de nuevo", 50, 500);
        g.drawString("Siguiente nivel", 450, 500);
        if (mouse.isLeftButtonPressed()) { // BUTTON1 es el botón izquierdo del ratón
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            if (mouseX >= 350 && mouseX <= 600 && mouseY >= 400 && mouseY <= 600) {
                juegoLemmings.subirNivel();
                juegoLemmings.seleccionarNivel();
            } else if (mouseX >= 0 && mouseX <= 350 && mouseY >= 400 && mouseY <= 600) {
                juegoLemmings.seleccionarNivel();
                nivelesCompletados --;
            }
        }
    }

    public void estadoFin(Graphics2D g){
        Mouse mouse = juegoLemmings.getMouse();
        g.setColor(new Color(50, 50, 150));
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Has completado el juego!", 240, 200);
        g.drawString("-Lemmings salvados: " + panel.getLemmingsSalvados(), 20, 250);
        g.drawString("-Tiempo restante: " + Temporizador.getTiempoRestante(), 20, 300);
        g.drawString("-Puntos totales: " + puntos, 20, 350);
        g.drawString("Jugar de nuevo", 50, 500);
        if (mouse.isLeftButtonPressed()) {
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            if (mouseX >= 0 && mouseX <= 350  && mouseY >= 400 && mouseY <= 600){
                JuegoLemmings.reiniciarJuego();
            }
        }
    }

    public void reiniciarNivel(){
        juegoLemmings.seleccionarNivel();
        nivelesCompletados --;
    }
    public void sumarPuntos(){
        puntos = Temporizador.getTiempoRestante() + (panel.getLemmingsSalvados()*10);
    }
    public void completarNivel(){
        nivelesCompletados ++;
    }
    public void setPanelHabilidades(PanelHabilidades nuevoPanelHabilidades) {
        this.panel = nuevoPanelHabilidades;
    }
}
