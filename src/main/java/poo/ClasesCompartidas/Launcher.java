package poo.ClasesCompartidas;

import com.entropyinteractive.JGame;
import poo.Lemmings.*;
import poo.pong.*;
import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
//La main
public class Launcher extends JFrame implements ActionListener {

    private final Button botonIniciar;
    private final Button botonConfig;
    private final Button botonAgregarJuego;
    private final Button botonPuntacion;
    private JButton botonSelect;
    private final JLabel imagen;
    JGame juego;
    Thread t;

    public Launcher() {
        setTitle("Launcher");
        setLayout(new BorderLayout());
        setSize(640, 480);
        setLocationRelativeTo(null);

        imagen = new JLabel();
        imagen.setHorizontalAlignment(SwingConstants.CENTER); // Centrar la imagen en el JLabel
        imagen.setVerticalAlignment(SwingConstants.CENTER);
        imagen.setBackground(Color.DARK_GRAY);
        imagen.setOpaque(true);
        cargaImgPrinc();
        add(imagen, BorderLayout.CENTER);


        // Crear la lista de juegos en el WEST
        JPanel listJuegos= new JPanel();
        listJuegos.setLayout(new BoxLayout(listJuegos, BoxLayout.Y_AXIS));
        listJuegos.setBackground(Color.DARK_GRAY);
        listJuegos.setBackground(new Color(45,45,45));
        listJuegos.setBorder(BorderFactory.createEmptyBorder(0, 1, 1, 1));
        listJuegos.add(Box.createRigidArea(new Dimension(5, 0))); // Espacio vertical de 20px
        //Boton Pong
        String Juego1 = "Pong";
        String rutaImgPong = "/imagenes/Launcher/pong.jpeg"; // Ruta
        listJuegos.add(crearBotonJuego(Juego1, rutaImgPong, this)); // this, es para que los botones se conecten con Launcher
        //Boton Lemmings
        String Juego2 = "Lemmings";
        String rutaImgLemmings = "/imagenes/Launcher/LemmingsImg.jpg"; // Ruta
        listJuegos.add(crearBotonJuego(Juego2, rutaImgLemmings, this));
        add(listJuegos, BorderLayout.WEST);

        // Crear los botones en el SOUTH
        Panel panelBotones = new Panel(new FlowLayout(FlowLayout.CENTER));
        panelBotones.setBackground(Color.DARK_GRAY);
        botonIniciar = new Button("Iniciar Juego");
        botonConfig = new Button("Configuracion");
        botonAgregarJuego = new Button("Agregar juego");
        botonPuntacion = new Button("Puntuacion");
        panelBotones.add(botonIniciar);
        panelBotones.add(botonConfig);
        panelBotones.add(botonAgregarJuego);
        panelBotones.add(botonPuntacion);
        botonIniciar.addActionListener(this);
        botonConfig.addActionListener(this);
        botonAgregarJuego.addActionListener(this);
        botonPuntacion.addActionListener(this);
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

    public void cargaImgPrinc(){
        String ruta="/imagenes/Launcher/GTA.jpeg";
        if(botonSelect!=null){
            ruta = switch (botonSelect.getText()) {
                case "Pong" -> "/imagenes/Launcher/pong.jpeg";
                case "Lemmings" -> "/imagenes/Launcher/LemmingsImg.jpg";
                default -> "/imagenes/Launcher/GTA.jpeg";
            };
        }
        try {
            URL imageUrl = Launcher.class.getResource(ruta);// obtiene la carpeta de recursos
            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);

                imagen.setIcon(originalIcon);
            } else {
                System.err.println("Imagen no encontrado img");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar imagen");
        }
        imagen.revalidate();
        imagen.repaint();
    }
    private JButton crearBotonJuego(String nombre, String rutaIcono, JFrame parentFrame) {
        // Cargar el icono
        ImageIcon icono = null;
        try {
            URL imageUrl = Launcher.class.getResource(rutaIcono);// obtiene la carpeta de recursos
            if (imageUrl != null) {
                icono = new ImageIcon(imageUrl);
                Image image = icono.getImage();
                Image scaledImage = image.getScaledInstance(40, 40, Image.SCALE_SMOOTH); // Puedes ajustar 40,40
                icono = new ImageIcon(scaledImage);
            } else {
                System.err.println("Icono no encontrado para '" + nombre + "': " + rutaIcono);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar icono para '" + nombre + "': " + e.getMessage());
        }

        // Crear el botón
        JButton botonJuego = new JButton(nombre);
        if (icono != null) {
            botonJuego.setIcon(icono);
        } else {
            botonJuego.setText(nombre + " (Sin Icono)"); // Muestra que no hay icono
        }

        // Configurar el estilo del botón
        botonJuego.setBorder(BorderFactory.createCompoundBorder( // Borde redondeado sutil
                BorderFactory.createLineBorder(new Color(90, 90, 90), 1),
                BorderFactory.createEmptyBorder(0, 5, 0, 5) // Relleno interno
        ));
        botonJuego.setPreferredSize(new Dimension(110, 50)); // Tamaño preferido
        botonJuego.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50)); // Asegura que el ancho se expanda con BoxLayout
        // Alineación del botón en el BoxLayout (centrado horizontalmente)
        botonJuego.setHorizontalAlignment(SwingConstants.LEFT);

        botonJuego.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                botonSelect=botonJuego;
                cargaImgPrinc();
            }
        });

        return botonJuego;
    }




    @Override
    public void actionPerformed(ActionEvent e) {
        String juegoSeleccionado;
        if(botonSelect!=null) {
        juegoSeleccionado = botonSelect.getText();
            if (e.getSource() == botonIniciar) {
                if (Objects.equals(juegoSeleccionado, "Pong")) {

                    juego = new Pong("Pong", 800, 600);
                    t = new Thread(() -> juego.run(1.0 / 60.0));
                    t.start();

                } else if (Objects.equals(juegoSeleccionado, "Lemmings")) {

                    juego = new JuegoLemmings("Lemmings", 800, 600);
                    t = new Thread(() -> juego.run((double) 1 /30));
                    t.start();
                }
            }

            if (e.getSource() == botonAgregarJuego)
                JOptionPane.showMessageDialog(null, "Estamos trabajando en eso...", "Informacion", JOptionPane.INFORMATION_MESSAGE);
            if (e.getSource() == botonConfig)
                if (Objects.equals(juegoSeleccionado, "Pong")) {
                    new ConfigFramePong();
                } else if (Objects.equals(juegoSeleccionado, "Lemmings")) {
                    new ConfigFrameLemmings();
                }
            if (e.getSource() == botonPuntacion)
                if (Objects.equals(juegoSeleccionado, "Pong")) {
                    new FramePuntuacion("Pong",new BDJugador("Pong"));
                } else if (Objects.equals(juegoSeleccionado, "Lemmings")) {
                    new FramePuntuacion("Lemmings",new BDJugador("Lemmings"));
                }
        }else if (e.getSource() == botonIniciar&&botonSelect==null||e.getSource() == botonPuntacion&&botonSelect==null)
            JOptionPane.showMessageDialog(null, "No seleccionaste un juego!!", "Informacion", JOptionPane.INFORMATION_MESSAGE);

        if(botonSelect==null && e.getSource()==botonConfig){
            JOptionPane.showMessageDialog(null, "No seleccionaste un juego!!", "Informacion", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public static void main(String[] args) {
        Launcher ejemplo = new Launcher();
        ejemplo.setVisible(true);

    }
}


