package poo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;



public class Config extends Frame implements ActionListener, KeyListener{
    private static final String DEFAULT_MUSIC = "Tema original";
    JCheckBox checkSound;
    private final List<Button> botonesHabilidad = new ArrayList<>();
    // Con sonido lo hice de otra forma pa ver namas
    Button musicaFondo,p,bAcelerar,bAutoDestruccion,bIniciar,bAceptar,bReset;
    Button habilidadBoton;
    Panel panelOpcionesMusica;
    Panel panelOpcionesSonido;

    private Button habilidadSeleccionadaParaCambio = null;
    private boolean esperandoNuevaTeclaMusicaFondo;
    private boolean esperandoNuevaTeclaPausa;
    private boolean esperandoNuevaTeclaAutoDestruccion;
    private boolean esperandoNuevaTeclaIniciar;
    private boolean esperandoNuevaTeclaAcelerar;

    public static void main (String[] args) {new Config();}
    public Config(){
        WindowListener l= new WindowAdapter() {
            public void windowClosing(WindowEvent event){
                dispose();
            }
        };

        addKeyListener(this);
        addWindowListener(l);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));


        panelOpcionesMusica= new Panel(new FlowLayout(FlowLayout.CENTER));
        checkSound = new JCheckBox("Musica", true);
        panelOpcionesMusica.add(checkSound);
        add(panelOpcionesMusica);

        panelOpcionesSonido= new Panel(new FlowLayout(FlowLayout.CENTER));
        checkSound = new JCheckBox("Efectos de Sonido", true);
        panelOpcionesSonido.add(checkSound);
        add(panelOpcionesSonido);

        Panel Pausa = new Panel(new FlowLayout(FlowLayout.CENTER));
        Pausa.add(new Label("Pausar juego:"));
        p= new Button("Espacio");
        Pausa.add(p);
        p.addActionListener(this);
        add(Pausa);
        for (int i = 1; i <= 8; i++) {
            Panel panelHabilidad = new Panel(new FlowLayout(FlowLayout.CENTER));
            panelHabilidad.add(new Label("Habilidad " + i + ": "));
            habilidadBoton = new Button("" + i);
            habilidadBoton.setActionCommand("habilidad_" + i);//Agrega una cadena de texto asociada que no se muestra como la etiqueta del botón.
            habilidadBoton.addActionListener(this);
            panelHabilidad.add(habilidadBoton);
            add(panelHabilidad);
            botonesHabilidad.add(habilidadBoton);
        }
        Panel acelerar = new Panel(new FlowLayout(FlowLayout.CENTER));
        acelerar.add(new Label("Acelerar juego:"));
        bAcelerar = new Button("e");
        bAcelerar.addActionListener(this);
        acelerar.add(bAcelerar);
        add(acelerar);

        Panel autoDestruccion = new Panel(new FlowLayout(FlowLayout.CENTER));
        autoDestruccion.add(new Label("Autodestruccion:"));
        bAutoDestruccion = new Button("a");
        autoDestruccion.add(bAutoDestruccion);
        bAutoDestruccion.addActionListener(this);
        add(autoDestruccion);

        Panel iniciar = new Panel(new FlowLayout(FlowLayout.CENTER));
        iniciar.add(new Label("Iniciar Juego:"));
        bIniciar=new Button("Enter");
        iniciar.add(bIniciar);
        bIniciar.addActionListener(this);
        add(iniciar);

        //ComboBox musica
        Panel SelMusica = new Panel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelMusica = new JPanel();
        panelMusica.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelMusica = new JLabel("Pista musical: ");
        String[] musicTracks = {DEFAULT_MUSIC, "Pista 1", "Pista 2"};
        JComboBox<String> musicComboBox = new JComboBox<>(musicTracks);
        panelMusica.add(labelMusica);
        panelMusica.add(musicComboBox);
        SelMusica.add(panelMusica);
        add(SelMusica);

        //ComboBox skin
        Panel SelSkin = new Panel(new FlowLayout(FlowLayout.CENTER));
        JPanel skinPanel = new JPanel();
        skinPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel skinLabel = new JLabel("Skin del PJ: ");
        String[] skins = {"Skin 1", "Skin 2", "Skin 3"};
        JComboBox<String> skinComboBox = new JComboBox<>(skins);
        skinPanel.add(skinLabel);
        skinPanel.add(skinComboBox);
        SelSkin.add(skinPanel);
        add(SelSkin);

        Panel acepReset = new Panel(new FlowLayout(FlowLayout.CENTER));
        bAceptar=new Button("Aceptar");
        acepReset.add(bAceptar);
        bAceptar.addActionListener(this);
        bReset=new Button("Resetear");
        acepReset.add(bReset);
        bReset.addActionListener(this);
        add(acepReset);

        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e){
        String command = e.getActionCommand();// String usado para las habilidades y para probar con efecto de sonidos

        if (e.getSource() == p) {
            System.out.println("Esperando nueva tecla de Pausa");
            esperandoNuevaTeclaPausa = true;
            p.setLabel("?");
            requestFocus();
            pack();        }
        if (e.getSource() == bAcelerar) {
            System.out.println("Esperando nueva tecla para acelerar");
            esperandoNuevaTeclaAcelerar = true;
            bAcelerar.setLabel("?");
            requestFocus();
            pack();        }
        if (e.getSource() == bAutoDestruccion) {
            System.out.println("Esperando nueva tecla de Autodestrucción MASIVAAA!!!!");
            esperandoNuevaTeclaAutoDestruccion = true;
            bAutoDestruccion.setLabel("?");
            requestFocus();
            pack();        }
        if (e.getSource() == bIniciar) {
            System.out.println("Esperando nueva tecla para iniciar");
            esperandoNuevaTeclaIniciar = true;
            bIniciar.setLabel("?");
            requestFocus();
            pack();        }
        if (e.getSource() == bAceptar) {
            dispose();
            pack();
        }
        if (e.getSource() == bReset) {
            setbReset();
            pack();
        }

        if (command.startsWith("habilidad_")) {
            habilidadSeleccionadaParaCambio = (Button) e.getSource();
            System.out.println("Esperando nueva tecla para la Habilidad " + command.substring("habilidad_".length()));
            if (habilidadSeleccionadaParaCambio != null) {
                habilidadSeleccionadaParaCambio.setLabel("?");
                requestFocus(); // Solicitar el foco después de hacer clic

            }

        }


    }



    public void setbReset() {
        p.setLabel("Espacio");
        bAcelerar.setLabel("e");
        bAutoDestruccion.setLabel("a");
        bIniciar.setLabel("Enter");
        bReset.setLabel("Resetear");
        for (int i = 0; i < botonesHabilidad.size(); i++) {
            Button boton = botonesHabilidad.get(i);
            boton.setLabel("" + (i + 1)); // Establecer la etiqueta al número de la habilidad
        }
    }


    public void keyPressed(KeyEvent e) {
        if (habilidadSeleccionadaParaCambio != null) {
            String nuevaTecla = KeyEvent.getKeyText(e.getKeyCode());// nuevaTecla tiene la nueva tecla
            habilidadSeleccionadaParaCambio.setLabel(nuevaTecla); // Cambiar la etiqueta del botón
            habilidadSeleccionadaParaCambio = null; // Resetear la selección
        }
        if (esperandoNuevaTeclaMusicaFondo) {
            String nuevaTecla = KeyEvent.getKeyText(e.getKeyCode());// nuevaTecla tiene la nueva tecla
            musicaFondo.setLabel(nuevaTecla);
            esperandoNuevaTeclaMusicaFondo = false; // Resetear la selección
        }
        if (esperandoNuevaTeclaAcelerar) {
            String nuevaTecla = KeyEvent.getKeyText(e.getKeyCode());// nuevaTecla tiene la nueva tecla
            bAcelerar.setLabel(nuevaTecla);
            esperandoNuevaTeclaAcelerar = false; // Resetear la selección
        }
        if (esperandoNuevaTeclaAutoDestruccion) {
            String nuevaTecla = KeyEvent.getKeyText(e.getKeyCode());// nuevaTecla tiene la nueva tecla
            bAutoDestruccion.setLabel(nuevaTecla);
            esperandoNuevaTeclaAutoDestruccion = false; // Resetear la selección
        }
        if (esperandoNuevaTeclaPausa) {
            String nuevaTecla = KeyEvent.getKeyText(e.getKeyCode());// nuevaTecla tiene la nueva tecla
            p.setLabel(nuevaTecla);
            esperandoNuevaTeclaPausa = false; // Resetear la selección
        }
        if (esperandoNuevaTeclaIniciar) {
            String nuevaTecla = KeyEvent.getKeyText(e.getKeyCode());// nuevaTecla tiene la nueva tecla
            bIniciar.setLabel(nuevaTecla);
            esperandoNuevaTeclaIniciar = false; // Resetear la selección
        }
    }

    public void keyReleased(KeyEvent e) {
        // Innecesario por ahora
    }

    public void keyTyped(KeyEvent e) {
        // Innecesario por ahora
    }
}
