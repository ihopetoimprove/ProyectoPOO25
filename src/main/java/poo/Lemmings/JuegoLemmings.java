package poo.Lemmings;

import com.entropyinteractive.Log;
import com.entropyinteractive.Mouse;
import poo.Juego;

import java.awt.*;
import java.util.Iterator;

public class JuegoLemmings extends Juego {

    public enum EstadoJuego {INICIO, JUGANDO, PAUSA, PERDIO, GANA, ACELERADO, FIN}

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
    private boolean juegoPausado = false;
    private double velocidadJuego = 1.0;


    public JuegoLemmings(String title, int width, int height) {
        super(title, width, height);
    }

    @Override
    public void gameStartup() {
        dibujadorEstados = new DibujarEstado(this, panel);
        panel = new PanelHabilidades(mouse);
    }

    @Override
    public void gameUpdate(double v) {
        PanelHabilidades.TipoHabilidad habilidadActivadaEsteFrame = panel.getHabilidadActivadaUnaVez();

        // 2. Manejar habilidades globales de forma independiente
        aplicarPausa(habilidadActivadaEsteFrame);
        aplicarNuke(habilidadActivadaEsteFrame);
        manejarAcelerarJuego(habilidadActivadaEsteFrame);

        if (estadoJuego == EstadoJuego.JUGANDO || estadoJuego == EstadoJuego.ACELERADO) {
            crearLemmings();
            temporizador.controlarTemporizador(velocidadJuego);
            actualizarLemmings(velocidadJuego);
            controlarHabilidades();
            controlarVictoria();
            controlarDerrota();
        }
        sePresionoElMouse = mouse.isLeftButtonPressed();
    }

    @Override
    public void gameDraw(Graphics2D g) {
        if (estadoJuego == EstadoJuego.INICIO) {
            dibujadorEstados.estadoInicio(g);
        } else if (estadoJuego == EstadoJuego.JUGANDO || estadoJuego == EstadoJuego.PAUSA || estadoJuego == EstadoJuego.ACELERADO) {
            nivelActual.dibujar(g);
            for (Lemming lemming : Lemming.getTodosLosLemmings()) {
                lemming.dibujar(g);
            }
            panel.dibujar(g);
        } else if (estadoJuego == EstadoJuego.PERDIO) {
            dibujadorEstados.estadoPerdio(g);
        } else if (estadoJuego == EstadoJuego.GANA) {
            dibujadorEstados.estadoGana(g);
        } else if (estadoJuego == EstadoJuego.FIN) {
            dibujadorEstados.estadoFin(g);
        }
    }

    @Override
    public void gameShutdown() {
        Log.info(getClass().getSimpleName(), "Shutting down game");
    }

    private void aplicarPausa(PanelHabilidades.TipoHabilidad habilidadActivada) {
        if (habilidadActivada == PanelHabilidades.TipoHabilidad.PAUSA) {
            if (estadoJuego == EstadoJuego.PAUSA) {
                estadoJuego = EstadoJuego.JUGANDO;
            } else if (estadoJuego == EstadoJuego.JUGANDO || estadoJuego == EstadoJuego.ACELERADO) {
                estadoJuego = EstadoJuego.PAUSA;
            }
        }
    }

    private void aplicarNuke(PanelHabilidades.TipoHabilidad habilidadActivada) {
        if (habilidadActivada == PanelHabilidades.TipoHabilidad.NUKE) {
            dibujadorEstados.reiniciarNivel();
        }
    }

    private void manejarAcelerarJuego(PanelHabilidades.TipoHabilidad habilidadActivada) {
        if (habilidadActivada == PanelHabilidades.TipoHabilidad.ACELERAR_JUEGO) {
            if (estadoJuego == EstadoJuego.ACELERADO) {
                estadoJuego = EstadoJuego.JUGANDO;
                velocidadJuego = velocidadJuego / 3;
            } else if (estadoJuego == EstadoJuego.JUGANDO) {
                estadoJuego = EstadoJuego.ACELERADO;
                velocidadJuego = velocidadJuego * 3;
            }
        }
    }

    public void seleccionarNivel() {
        Lemming.limpiarLemmings();
        PanelHabilidades.limpiarPanel();
        lemmingsGenerados = 0;
        if (nivelSeleccionado != -1) {
            panel = new PanelHabilidades(nombresNiveles[nivelSeleccionado], getMouse());
            dibujadorEstados = new DibujarEstado(this, panel);
            nivelActual = new Nivel(nombresNiveles[nivelSeleccionado]);
            estadoJuego = EstadoJuego.JUGANDO;
            temporizador = new Temporizador(panel.getTiempoLimite());
            Log.info(getClass().getSimpleName(), "Iniciando Nivel: " + nombresNiveles[nivelSeleccionado]);
        }
    }

    private void actualizarLemmings(double velocidadJuego) {
        Iterator<Lemming> iterador = Lemming.getTodosLosLemmings().iterator();
        while (iterador.hasNext()) {
            Lemming lemmingActual = iterador.next();
            lemmingActual.mover(velocidadJuego);
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
        boolean mouseApretado = mouse.isLeftButtonPressed();
        if (mouseApretado && !sePresionoElMouse) {
            if (panel.getHabilidadSeleccionada() != PanelHabilidades.TipoHabilidad.PAUSA) {
                int mouseX = mouse.getX();
                int mouseY = mouse.getY();
                Lemming lemmingClickeado = encontrarLemmingEn(mouseX, mouseY);
                if (lemmingClickeado != null) {
                    if (panel.getCantidadHabilidad(panel.getHabilidadSeleccionada()) > 0) {
                        if (lemmingClickeado.getEstadoActual() != Lemming.EstadoLemming.BLOQUEANDO && lemmingClickeado.getEstadoActual() != Lemming.EstadoLemming.ESCALANDO) {
                            lemmingClickeado.aplicarHabilidadLemming(panel.getHabilidadSeleccionada());
                            panel.decrementarHabilidad(panel.getHabilidadSeleccionada());
                        }
                    }
                }
            }
        }
    }

    public void controlarVictoria() {
        if (panel.getLemmingsVivos() == 0) {
            dibujadorEstados.completarNivel();
            estadoJuego = EstadoJuego.GANA;
        }
    }

    public void controlarDerrota() {
        if (Temporizador.getTiempoRestante() <= 0 || panel.getLemmingsASalvar() - panel.getLemmingsSalvados() > panel.getLemmingsVivos()) {
            estadoJuego = EstadoJuego.PERDIO;
        }
    }

    public Lemming encontrarLemmingEn(int x, int y) {
        for (Lemming lemming : Lemming.getTodosLosLemmings()) {
            if (
                    x >= (lemming.getX() - 20) && x <= (lemming.getX() + 20) &&
                            y >= (lemming.getY() - 20) && y <= (lemming.getY() + 20)) {
                return lemming;
            }
        }
        return null;
    }

    public Mouse getMouse() {return super.getMouse();}
    public static EstadoJuego getEstadoJuego(){return estadoJuego;}
    public static boolean getSePresionoElMouse(){return sePresionoElMouse;}
    public void setNivelSeleccionado(int nivel){nivelSeleccionado = nivel;}
    public static void setPausa(){estadoJuego = EstadoJuego.PAUSA;}
    public static void reaunudarJuego(){estadoJuego = EstadoJuego.JUGANDO;}
    public static void reiniciarJuego(){estadoJuego = EstadoJuego.INICIO;}
    public void subirNivel(){nivelSeleccionado = (nivelSeleccionado + 1) % 3;}
    public void finDelJuego(){estadoJuego = EstadoJuego.FIN;}
}