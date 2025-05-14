package poo;

import java.awt.*;
import java.awt.event.*;

public class Launcher extends Frame {

    private List listaJuegos;
    private Button botonIniciar;
    private Button botonConfig;
    private TextArea areaTexto;

    public Launcher() {
        setTitle("Launcher");
        setLayout(new BorderLayout());
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Crear la lista de juegos en el WEST
        listaJuegos = new List();
        listaJuegos.add("Pong");
        listaJuegos.add("Lemmings");
        add(listaJuegos, BorderLayout.WEST);

        // Crear los botones en el SOUTH
        Panel panelBotones = new Panel(new FlowLayout(FlowLayout.CENTER));
        botonIniciar = new Button("Iniciar Juego");
        botonConfig = new Button("Configuración");
        panelBotones.add(botonIniciar);
        panelBotones.add(botonConfig);
        add(panelBotones, BorderLayout.SOUTH);

        // Crear el área de texto en el CENTER
        areaTexto = new TextArea("Era muy dificil poner una imagen");
        add(areaTexto, BorderLayout.CENTER);

        // Manejar el cierre de la ventana
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                dispose();
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        //EventQueue.invokeLater(() -> {
            Launcher ejemplo = new Launcher();
            ejemplo.setVisible(true);
    }
}


