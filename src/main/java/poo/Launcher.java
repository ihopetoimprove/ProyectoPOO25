package poo;

import com.entropyinteractive.JGame;
import poo.Lemmings.JuegoLemmings;
import poo.pong.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Launcher extends Frame implements ActionListener {

    private final List listaJuegos;
    private final Button botonIniciar;
    private final Button botonConfig;
    private final Button botonAgregarJuego;
    private final TextArea areaTexto;
    JGame juego;
    Thread t;

    public Launcher() {
        setTitle("Launcher");
        setLayout(new BorderLayout());
        setSize(800, 400);
        setLocationRelativeTo(null);

        // Crear la lista de juegos en el WEST
        listaJuegos = new List();
        listaJuegos.add("Pong");
        listaJuegos.add("Lemmings");
        add(listaJuegos, BorderLayout.WEST);

        // Crear los botones en el SOUTH
        Panel panelBotones = new Panel(new FlowLayout(FlowLayout.CENTER));
        botonIniciar = new Button("Iniciar Juego");
        botonConfig = new Button("Configuracion");
        botonAgregarJuego = new Button("Agregar juego");
        panelBotones.add(botonIniciar);
        panelBotones.add(botonConfig);
        panelBotones.add(botonAgregarJuego);
        botonIniciar.addActionListener(this);
        botonConfig.addActionListener(this);
        botonAgregarJuego.addActionListener(this);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonIniciar) {
            String juegoSeleccionado = listaJuegos.getSelectedItem();
            if (juegoSeleccionado == "Pong") {

                juego = new Pong("Pong", 800, 600);
                t = new Thread() {
                    public void run() {
                        juego.run(1.0 / 60.0);
                    }
                };
                t.start();

            } else if (juegoSeleccionado == "Lemmings") {

                juego = new JuegoLemmings("Lemmings", 800, 600);
                t = new Thread() {
                    public void run() {
                        juego.run(1.0 / 60.0);
                    }
                };
                t.start();
            }
        }

        if (e.getSource() == botonAgregarJuego)
            JOptionPane.showMessageDialog(null, "Estamos trabajando en eso...", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        if (e.getSource() == botonConfig)
                new Config();
    }

    public static void main(String[] args) {
        Launcher ejemplo = new Launcher();
        ejemplo.setVisible(true);

    }
}

