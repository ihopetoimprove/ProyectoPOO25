package poo;

import com.entropyinteractive.JGame;
import poo.pong.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Launcher extends Frame implements ActionListener {

    private List listaJuegos;
    private Button botonIniciar;
    private Button botonConfig;
    private TextArea areaTexto;
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
        botonConfig = new Button("Configuración");
        panelBotones.add(botonIniciar);
        panelBotones.add(botonConfig);
        botonIniciar.addActionListener(this);
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
        if(e.getSource() == botonIniciar){
            String juegoSeleccionado = listaJuegos.getSelectedItem();
            if(juegoSeleccionado == "Pong"){
                areaTexto.append("Le mandaste pong!");
            }else if(juegoSeleccionado == "Lemmings") {
                areaTexto.append("Le mandaste Lemmings!");
            }
        }
    }


    public static void main(String[] args) {
        //EventQueue.invokeLater(() -> {
        Launcher ejemplo = new Launcher();
        ejemplo.setVisible(true);
        //Pong ejemplo = new Pong("Pong", 600, 500);
        //ejemplo.setVisible(true);

    }
}

