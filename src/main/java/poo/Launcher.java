package poo;

import com.entropyinteractive.JGame;
import poo.Lemmings.JuegoLemmings;
import poo.pong.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Launcher extends Frame implements ActionListener {

    private final List listaJuegos;
    private final Button botonIniciar;
    private final Button botonConfig;
    private final Button botonAgregarJuego;
    private JLabel imagenLauncher;
    JGame juego;
    Thread t;

    public Launcher() {
        setTitle("Launcher");
        setLayout(new BorderLayout());
        setSize(640, 480);
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
        //add(areaTexto, BorderLayout.CENTER);

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
        String juegoSeleccionado = listaJuegos.getSelectedItem();
        //intento de cargar imagen en el lanzador
        /*if(juegoSeleccionado == "Pong"){
            try {
                BufferedImage imagenFondo = ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/fondo.png"));
                ImageIcon icon = new ImageIcon(imagenFondo);
                imagenLauncher.setIcon(icon);
                add(imagenLauncher, BorderLayout.CENTER);
                revalidate();
                repaint();
                System.out.println("llegué2");
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }*/
        if (e.getSource() == botonIniciar) {
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

