package poo.Lemmings;

import com.entropyinteractive.JGame;
import com.entropyinteractive.Log;
import com.entropyinteractive.Mouse;

import java.awt.*;
import java.util.Iterator;

public class JuegoLemmings extends JGame {

    public enum EstadoJuego {INICIO, JUGANDO, PAUSA, PERDIO, GANA, FIN}

    private static EstadoJuego estadoJuego = EstadoJuego.INICIO;
    private DibujarEstado dibujadorEstados;
    private Nivel nivelActual;
    private PanelHabilidades panel;
    private final String[] nombresNiveles = {"nivel1.txt", "nivel2.txt", "nivel3.txt"};
    private int nivelSeleccionado = -1;
    private int lemmingsGenerados = 0;
    private long ultimoTiempoAccion;
    Mouse mouse = this.getMouse();
    static boolean sePresionoElMouse = false;

    Temporizador temporizador;

    //no se que onda con esto
    private StringBuilder currentNameInput = new StringBuilder(); // ¡Aquí está declarado!
    private String playerName = "Jugador";

    public JuegoLemmings(String title, int width, int height) {
        super(title, width, height);

    }

    @Override
    public void gameStartup() {
        this.dibujadorEstados = new DibujarEstado(this);
    }

    @Override
    public void gameUpdate(double v) {
        if (estadoJuego == EstadoJuego.JUGANDO) {
            temporizador.controlarTemporizador();
            crearLemmings();
            actualizarLemmings();
            controlarHabilidades();
            controlarVictoria();
            controlarDerrota();
        }
        if (estadoJuego == EstadoJuego.PAUSA){
            controlarHabilidades();
        }
        sePresionoElMouse = mouse.isLeftButtonPressed();
    }

    @Override
    public void gameDraw(Graphics2D g) {
        if (estadoJuego == EstadoJuego.INICIO) {
            dibujadorEstados.estadoInicio(g);
        }
        if (estadoJuego == EstadoJuego.JUGANDO || estadoJuego == EstadoJuego.PAUSA) {
            nivelActual.dibujar(g);
            for (Lemming lemming : Lemming.getTodosLosLemmings()) {
                lemming.dibujar(g);
            }
            panel.dibujar(g);
        }
        if (estadoJuego == EstadoJuego.PERDIO) {
            dibujadorEstados.estadoPerdio(g);
        }
        if (estadoJuego == EstadoJuego.GANA) {
            dibujadorEstados.estadoGana(g);
        }
        if (estadoJuego == EstadoJuego.FIN) {
            dibujadorEstados.estadoFin(g);
        }
    }

    @Override
    public void gameShutdown() {
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }

    public void seleccionarNivel() {
        Lemming.limpiarLemmings();
        PanelHabilidades.limpiarPanel();
        lemmingsGenerados = 0;
        if (nivelSeleccionado != -1) {
            panel = new PanelHabilidades(nombresNiveles[nivelSeleccionado], getMouse());
            nivelActual = new Nivel(nombresNiveles[nivelSeleccionado]);
            estadoJuego = EstadoJuego.JUGANDO;
            temporizador = new Temporizador(panel.getTiempoLimite());
            Log.info(getClass().getSimpleName(), "Iniciando Nivel: " + nombresNiveles[nivelSeleccionado]);
        }
    }

    private void actualizarLemmings() {
        Iterator<Lemming> iterador = Lemming.getTodosLosLemmings().iterator();
        while (iterador.hasNext()) {
            Lemming lemmingActual = iterador.next();
            lemmingActual.mover();
            if (lemmingActual.getEstadoActual() == Lemming.EstadoLemming.MURIENDO ||
                    lemmingActual.getEstadoActual() == Lemming.EstadoLemming.SALVADO) {
                iterador.remove();
                panel.eliminarLemming();
            }
        }
    }

    private void crearLemmings() {
        if (lemmingsGenerados < panel.getTotalLemmings()) {
            long tiempoActual = System.currentTimeMillis(); // Obtiene el tiempo actual en milisegundos
            long INTERVALO_LEMMINGS = 1000;
            if (tiempoActual - ultimoTiempoAccion >= INTERVALO_LEMMINGS) {
                ultimoTiempoAccion = tiempoActual;
                Lemming nuevoLemming = new Lemming(nivelActual.getEntradaX(), nivelActual.getEntradaY(), nivelActual);
                Lemming.agregarLemming(nuevoLemming); // Añadirlo a la lista estática
                lemmingsGenerados++;
            }
        }
    }

    public void controlarHabilidades() {
        int mouseX = mouse.getX();
        int mouseY = mouse.getY();
        boolean mouseApretado = mouse.isLeftButtonPressed();
        if (mouseApretado && !sePresionoElMouse) {
            Lemming lemmingClickeado = encontrarLemmingEn(mouseX, mouseY);
            if (lemmingClickeado != null) {
                if (panel.getCantidadHabilidad(panel.getHabilidadSeleccionada()) > 0) {
                    if (lemmingClickeado.getEstadoActual() == Lemming.EstadoLemming.CAMINANDO || lemmingClickeado.getEstadoActual() == Lemming.EstadoLemming.CAYENDO) {
                        lemmingClickeado.aplicarHabilidadLemming(panel.getHabilidadSeleccionada());
                        panel.decrementarHabilidad(panel.getHabilidadSeleccionada());
                    }
                }
            }
        }

    }

    public void controlarVictoria() {
        if (panel.getLemmingsSalvados() == panel.getLemmingsASalvar()) {
            dibujadorEstados.completarNivel();
            estadoJuego = EstadoJuego.GANA;
        }
    }

    public void controlarDerrota() {
        if (temporizador.getTiempoRestante() <= 0 || panel.getLemmingsASalvar() - panel.getLemmingsSalvados() > panel.getTotalLemmings()) {
            estadoJuego = EstadoJuego.PERDIO;
        }
    }

    public Lemming encontrarLemmingEn(int x, int y) {
        for (Lemming lemming : Lemming.getTodosLosLemmings()) {
            if (
                    x >= (lemming.getX() - 60) && x <= (lemming.getX() + 20) &&
                            y >= (lemming.getY() - 60) && y <= (lemming.getY() + 20)) {
                return lemming;
            }
        }
        return null;
    }

    public Mouse getMouse() {
        return super.getMouse(); // Asegúrate de llamar al getMouse() de JGame
    }

    public static EstadoJuego getEstadoJuego(){return estadoJuego;}
    public static boolean getSePresionoElMouse(){return sePresionoElMouse;}
    public void setNivelSeleccionado(int nivel){nivelSeleccionado = nivel;}
    public static void setPausa(){estadoJuego = EstadoJuego.PAUSA;}
    public static void reaunudarJuego(){estadoJuego = EstadoJuego.JUGANDO;}
    public static void reiniciarJuego(){estadoJuego = EstadoJuego.INICIO;}

    public void subirNivel(){
        nivelSeleccionado = (nivelSeleccionado + 1) % 3;
    }

    public void finDelJuego(){
        estadoJuego = EstadoJuego.FIN;
    }
}