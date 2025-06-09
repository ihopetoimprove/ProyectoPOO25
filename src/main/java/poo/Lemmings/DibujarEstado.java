package poo.Lemmings;

import com.entropyinteractive.Keyboard;
import com.entropyinteractive.Mouse;
import poo.ClasesCompartidas.BDJugador;
import poo.ClasesCompartidas.FramePuntuacion;
import poo.ClasesCompartidas.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Objects;

import static poo.Lemmings.JuegoLemmings.Texto;


public class DibujarEstado{

    private JuegoLemmings juegoLemmings;
    private static short nivelesCompletados = 0;
    PanelHabilidades panel;
    private static int puntos;
    private boolean escribiendo = false;
    private final int LargoTextoMax = 25;
    private final Jugador jugador = new Jugador();
    private boolean sumoPuntos = false;

    public DibujarEstado(JuegoLemmings juegoLemmings, PanelHabilidades panelHabilidades) {
        this.juegoLemmings = juegoLemmings;
        this.panel = panelHabilidades;
    }

    public void estadoInicio(Graphics2D g) {
        Mouse mouse = juegoLemmings.getMouse();
        puntos = 0;

        //Establecer fondo
        g.setColor(new Color(50, 50, 150));
        g.fillRect(0, 0, 1024, 768);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        String gameTitle = "¡LEMMINGS!";
        g.drawString(gameTitle, 255, 100);

        // Dibujar el recuadro para el nombre
        g.setColor(Color.WHITE);
        g.drawRect(250, 280, 300, 40); // Recuadro para el texto

        // Dibujar el texto introducido
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.setColor(Color.YELLOW);
        g.drawString(Texto, 260, 308);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Iniciar nivel 1", 100, 400);
        g.drawString("Iniciar nivel 2", 350, 400);
        g.drawString("Iniciar nivel 3", 600, 400);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Introduce tu nombre:", 280, 250);
        if (mouse.isLeftButtonPressed()) { // BUTTON1 es el botón izquierdo del ratón
            mousePresionado(mouse);
        }
        if(escribiendo)
            escucharTeclado();// Tiene que estar aca para llamarlo constantemente

    }
    public void mousePresionado(Mouse mouse){
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
        if (mouseX >= 242 && mouseX <=541 && mouseY >= 251 && mouseY <=289){ // Para el "campo de texto"
            escribiendo=true;
        }
    }
    public void escucharTeclado() {
        Keyboard teclado = juegoLemmings.getKeyboard(); //Aca teclado empieza a almacenar los eventos del teclado fisico pe, en un "lista"
        if (escribiendo && teclado != null) {
            for (KeyEvent event : teclado.getEvents()) {
                if (event.getID() == KeyEvent.KEY_TYPED) {// para ver si es tipeo y una tecla no especial
                    char typedChar = event.getKeyChar();
                    // Caracteres imprimibles
                    if (typedChar >= ' ' && typedChar <= '~') {
                        if (Texto.length() < LargoTextoMax) {
                            Texto += typedChar;
                        }
                    }
                } else if (event.getID() == KeyEvent.KEY_PRESSED) {
                    // teclas especiales como BACKSPACE y ENTER
                    if (event.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                        if (!Texto.isEmpty()) {
                            Texto = Texto.substring(0, Texto.length() - 1);
                        }
                    } else if (event.getKeyCode() == KeyEvent.VK_ENTER) {
                        System.out.println("Texto ingresado: " + Texto);
                        escribiendo = false;
                    }
                }
            }
        }
//        teclado.getEvents().clear(); no es necesario para limpiar la lista porque ya se borra en cada bucle, por instanciar en este metodo
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
        if (!sumoPuntos) {
            sumarPuntos();
        }
        g.setColor(new Color(85, 85, 226));
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
        g.setFont(new Font("Arial", Font.BOLD, 15));
        g.drawString("Los puntos se obtienen sumando los segundos restantes y cada lemming salvado cuenta 10 puntos", 20,370);
        if (mouse.isLeftButtonPressed()) { // BUTTON1 es el botón izquierdo del ratón
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            if (mouseX >= 443 && mouseX <= 719 && mouseY >= 438 && mouseY <= 470) {
                if (nivelesCompletados != 3) {
                    juegoLemmings.subirNivel();
                    juegoLemmings.seleccionarNivel();
                } else {
                    juegoLemmings.finDelJuego();
                }

            } else if (mouseX >= 0 && mouseX <= 350 && mouseY >= 400 && mouseY <= 600) {
                juegoLemmings.seleccionarNivel();
                nivelesCompletados --;
            }
        }
    }

    public void estadoFin(Graphics2D g){
        nivelesCompletados = 0;
        Mouse mouse = juegoLemmings.getMouse();
        g.setColor(new Color(50, 50, 150));
        g.fillRect(0, 0, 800, 600);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Has completado el juego!", 240, 200);
        g.drawString("-Puntos totales: " + puntos, 20, 350);
        g.drawString("-Ver Tabla de Puntuaciones", 20, 420);
        g.drawString("Jugar de nuevo", 50, 500);

        //cargar a la BD
        if(!Objects.equals(Texto, "")) {
            jugador.setNombre(Texto);
            jugador.setRecord(puntos);
            BDJugador GuardaJugador = new BDJugador("Lemmings");
            GuardaJugador.agregarJugador(jugador);
            Texto="";
        }

        if (mouse.isLeftButtonPressed()) {
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            if (mouseX >= 0 && mouseX <= 350  && mouseY >= 400 && mouseY <= 600){
                JuegoLemmings.reiniciarJuego();
            }
            if (mouseX >= 10 && mouseX <= 530  && mouseY >= 356 && mouseY <= 392)
                new FramePuntuacion("Lemmings",new BDJugador("Lemmings"));
        }
    }

    public void reiniciarNivel(){
        juegoLemmings.seleccionarNivel();
        nivelesCompletados --;
    }
    public void sumarPuntos(){
        puntos = Temporizador.getTiempoRestante() + (panel.getLemmingsSalvados()*10) + puntos;
        sumoPuntos = true;
    }
    public void completarNivel(){nivelesCompletados ++;}
}
