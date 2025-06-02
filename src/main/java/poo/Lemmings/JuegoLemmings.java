package poo.Lemmings;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Log;
import com.entropyinteractive.Mouse;

import java.awt.*;

public class JuegoLemmings extends JGame {

    public enum EstadoJuego {INICIO, JUGANDO, PERDIO, GANA, FIN}

    private static EstadoJuego estadoJuego = EstadoJuego.INICIO;
    private Nivel nivelActual;
    private PanelHabilidades panel;
    private final String[] nombresNiveles = {"nivel1.txt", "nivel2.txt", "nivel3.txt"};
    private int nivelSeleccionado = -1;
    private int lemmingsGenerados = 0;
    private long ultimoTiempoAccion;
    Mouse mouse = this.getMouse();
    boolean sePresionoElMouse = false;

    Temporizador tiempo;
    private final int FPS_JUEGO =60;
    int f= FPS_JUEGO;

    //no se que onda con esto
    private StringBuilder currentNameInput = new StringBuilder(); // ¡Aquí está declarado!
    private String playerName = "Jugador";

    public JuegoLemmings(String title, int width, int height) {
        super(title, width, height);
    }

    @Override
    public void gameStartup() {

    }

    @Override
    public void gameUpdate(double v) {

        if (estadoJuego == EstadoJuego.JUGANDO) {
            f--;
            if(f<=0) {
                tiempo.restarTiempo();
                f=FPS_JUEGO;
            }
            crearLemmings();
            for (Lemming lemming : Lemming.getTodosLosLemmings()) {
                lemming.mover();
            }
            controlarHabilidades();
            controlarVictoria();
            controlarDerrota();
            sePresionoElMouse = mouse.isLeftButtonPressed();

        }

    }

    @Override
    public void gameDraw(Graphics2D g) {
        if (estadoJuego == EstadoJuego.INICIO) {
            estadoInicio(g);
        }
        if (estadoJuego == EstadoJuego.JUGANDO) {
            nivelActual.dibujar(g);
            for (Lemming lemming : Lemming.getTodosLosLemmings()) {
                lemming.dibujar(g);
            }
            panel.dibujar(g);
        }
        if (estadoJuego == EstadoJuego.PERDIO){
            estadoPerdio(g);
        }
        if (estadoJuego == EstadoJuego.GANA){
            estadoGana(g);
        }
        if (estadoJuego == EstadoJuego.FIN){
        }
    }

    @Override
    public void gameShutdown() {
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }

    public void estadoInicio(Graphics2D g) {
        Mouse mouse = this.getMouse();
        //Establecer fondo
        g.setColor(new Color(50, 50, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        String gameTitle = "¡LEMMINGS!";
        g.drawString(gameTitle, 255, 100);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Introduce tu nombre:", 280, 250);

        // Dibujar el recuadro para el nombre
        g.setColor(Color.WHITE);
        g.drawRect(250, 280, 300, 40); // Recuadro para el texto

        // Dibujar el texto introducido
        g.setColor(Color.YELLOW);
        String textToDisplay = currentNameInput.toString();
        g.drawString(textToDisplay, 260, 308);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Iniciar nivel 1", 100, 400);
        g.drawString("Iniciar nivel 2", 350, 400);
        g.drawString("Iniciar nivel 3", 600, 400);
        if (mouse.isLeftButtonPressed()) { // BUTTON1 es el botón izquierdo del ratón
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();

            if (mouseY >= 340 && mouseY <= 380) { // Rango vertical para todos los botones
                if (mouseX >= 0 && mouseX <= 200) { // "Iniciar nivel 1"
                    nivelSeleccionado = 0;
                    seleccionarNivel();
                }
                if (mouseX >= 250 && mouseX <= 450){
                    nivelSeleccionado = 1;
                    seleccionarNivel();
                }
                if (mouseX >= 500 && mouseX <= 700){
                    nivelSeleccionado = 2;
                    seleccionarNivel();
                }
            }
        }
    }

    public void estadoPerdio(Graphics2D g) {
        Mouse mouse = this.getMouse();
        g.setColor(new Color(50, 50, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Muy bien PERDISTE!", 240, 200);
        g.drawString("Intentar de nuevo", 50, 500);
        if (mouse.isLeftButtonPressed()) { // BUTTON1 es el botón izquierdo del ratón
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            if (mouseX >= 0 && mouseX <= 250  && mouseY >= 400 && mouseY <= 600){
                seleccionarNivel();
            }
        }
    }

    public void estadoGana(Graphics2D g) {
        Mouse mouse = this.getMouse();
        g.setColor(new Color(50, 50, 150));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Muy bien ganaste!", 240, 200);
        g.drawString("Jugar de nuevo", 50, 500);
        g.drawString("Siguiente nivel", 450, 500);
        if (mouse.isLeftButtonPressed()) { // BUTTON1 es el botón izquierdo del ratón
            int mouseX = mouse.getX();
            int mouseY = mouse.getY();
            if (mouseX >= 350 && mouseX <= 600 && mouseY >= 400 && mouseY <= 600) {
                nivelSeleccionado +=1 ;
                seleccionarNivel();
            } else if (mouseX >= 0 && mouseX <= 250  && mouseY >= 400 && mouseY <= 600){
                seleccionarNivel();
            }
        }
    }

    public void seleccionarNivel() {
        Lemming.limpiarLemmings();
        PanelHabilidades.limpiarPanel();
        lemmingsGenerados = 0;
        if (nivelSeleccionado != -1) {
            panel = new PanelHabilidades(nombresNiveles[nivelSeleccionado], getMouse());
            nivelActual = new Nivel(nombresNiveles[nivelSeleccionado]);
            estadoJuego = EstadoJuego.JUGANDO;
            tiempo=new Temporizador(panel.getTiempoLimite());
            Log.info(getClass().getSimpleName(), "Iniciando Nivel: " + nombresNiveles[nivelSeleccionado]);
        }
    }

    private void crearLemmings() {
        if (lemmingsGenerados < panel.getTotalLemmings()) {
            long tiempoActual = System.currentTimeMillis(); // Obtiene el tiempo actual en milisegundos
            long INTERVALO_LEMMINGS = 1000;
            if (tiempoActual - ultimoTiempoAccion >= INTERVALO_LEMMINGS) {
                ultimoTiempoAccion = tiempoActual;
                Lemming nuevoLemming = new Lemming(nivelActual.getEntradaX(), nivelActual.getEntradaY(), nivelActual);
                Lemming.agregarLemming(nuevoLemming); // Añadirlo a la lista estáticav
                lemmingsGenerados++;
            }
        }
    }

    public void controlarHabilidades() {
        if (panel.getHabilidadSeleccionada() != PanelHabilidades.TipoHabilidad.NINGUNA) {
            boolean mouseApretado = mouse.isLeftButtonPressed();
            if (mouseApretado && !sePresionoElMouse) {
                int mouseX = mouse.getX();
                int mouseY = mouse.getY();
                Lemming lemmingClickeado = encontrarLemmingEn(mouseX, mouseY);
                if (lemmingClickeado != null) {
                    if (panel.getCantidadHabilidad(panel.getHabilidadSeleccionada()) > 0) {
                        if (lemmingClickeado.getEstadoActual() == Lemming.EstadoLemming.CAMINANDO) {
                            lemmingClickeado.aplicarHabilidadLemming(panel.getHabilidadSeleccionada());
                            panel.decrementarHabilidad(panel.getHabilidadSeleccionada());
                        }
                    }
                }
            }
        }
    }

    public void controlarVictoria() {
        if (panel.getLemmingsSalvados() == panel.getLemmingsASalvar()){
            estadoJuego = EstadoJuego.GANA;
        }
    }

    public void controlarDerrota() {
        if (tiempo.getTiempoRestante() <= 0/*|| panel.getLemmingsSalvados()< panel.getLemmingsASalvar()*/){
            estadoJuego = EstadoJuego.PERDIO;
        }
    }

    public Lemming encontrarLemmingEn(int x, int y) {
        for (Lemming lemming : Lemming.getTodosLosLemmings()) {
            if (
                    x >= lemming.getX() && x <= (lemming.getX() + 20) &&
                            y >= lemming.getY() && y <= (lemming.getY() + 20)) {
                return lemming;
            }
        }
        return null;
    }
}