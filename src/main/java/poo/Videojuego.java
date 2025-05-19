import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;



public class Videojuego extends Frame implements ActionListener, KeyListener{

    private List<Button> botonesHabilidad = new ArrayList<>();
    // Con sonido lo hice de otra forma pa ver namas
    Button botonMusica,musicaFondo,p,bAcelerar,bAutoDestruccion,bIniciar,bAceptar,bReset,bSkin, efectSonidos;
    Button habilidadBoton;
    private boolean opcionesVisibles = false;
    Panel panelOpcionesMusica;
    private Button habilidadSeleccionadaParaCambio = null;
    private boolean esperandoNuevaTeclaefectSonido = false;
    private boolean esperandoNuevaTeclaMusicaFondo;
    private boolean esperandoNuevaTeclaPausa;
    private boolean esperandoNuevaTeclaAutoDestruccion;
    private boolean esperandoNuevaTeclaIniciar;
    private boolean esperandoNuevaTeclaAcelerar;

    public static void main (String[] args) {// No leva NAAADAAA EL MAAINN
        new Videojuego();
    }
    public Videojuego(){
        WindowListener l= new WindowAdapter() {
            public void windowClosing(WindowEvent event){
                System.exit(0);
            };
        };

        addKeyListener(this);
        addWindowListener(l);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        Panel Sonido = new Panel(new FlowLayout(FlowLayout.CENTER));
        Sonido.add(new Label("Silenciar Juego:"));
        efectSonidos =new Button("Q");
        Sonido.add(efectSonidos);
        efectSonidos.addActionListener(this);
        add(Sonido);

        Panel pMusicaFondo = new Panel(new FlowLayout(FlowLayout.CENTER));
        pMusicaFondo.add(new Label("Silenciar Musica:"));
        musicaFondo=new Button("W");
        musicaFondo.addActionListener(this);
        pMusicaFondo.add(musicaFondo);
        add(pMusicaFondo);

        Panel Pausa = new Panel(new FlowLayout(FlowLayout.CENTER));
        Pausa.add(new Label("Pausar juego:"));
        p= new Button("Espacio");
        Pausa.add(p);
        p.addActionListener(this);
        add(Pausa);
        /*
        for (int i = 1; i <= 8; i++) {
            // Crea un Panel para cada habilidad para que esté en fila
            Panel panelHabilidad = new Panel(new FlowLayout(FlowLayout.CENTER));
            // Crea una etiqueta para el número de la habilidad
            panelHabilidad.add(new Label("Habilidad " + i + ": "));
            //panelHabilidad.add(new Button (""+i).addActionListener(this)); tira error no sé pq
            Button habilidad= new Button(""+ i);
            habilidad.addActionListener(this);
            panelHabilidad.add(habilidad);
            add(panelHabilidad);
        }*/
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

        Panel pistaMusical = new Panel(new FlowLayout(FlowLayout.CENTER));
        pistaMusical.add(new Label("Música:"));
        botonMusica = new Button("Música");
        botonMusica.addActionListener(this);
        pistaMusical.add(botonMusica);
        add(pistaMusical);
        //Agrego panel nuevo para seleccionar la pista
        panelOpcionesMusica= new Panel(new FlowLayout(FlowLayout.LEFT));
        panelOpcionesMusica.add(new Label("Seleccionar Pista:"));
        // Añadir diferentes opciones de música
        panelOpcionesMusica.add(new Button("Pista 1"));
        panelOpcionesMusica.add(new Button("Pista 2"));
        panelOpcionesMusica.add(new Button("Pista Épica"));
        panelOpcionesMusica.add(new Button("Música Relajante"));
        panelOpcionesMusica.add(new Button("Otra Pista"));
        panelOpcionesMusica.setVisible(false); // Inicialmente oculto
        add(panelOpcionesMusica);

        Panel skin = new Panel(new FlowLayout(FlowLayout.CENTER));
        skin.add(new Label("Seleccionar skin:"));
        skin.add(new Button("que peréza"));
        add(skin);

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

        if (e.getSource() == botonMusica) {
            opcionesVisibles = !opcionesVisibles;
            panelOpcionesMusica.setVisible(opcionesVisibles);
            pack(); // Reajustar el tamaño de la ventana
        }

        /* if (command.equals("Q")) *////Esta forma me hace buscar que el boton presionado tenga la etiqueta 'q', una vez cambiada ni puedo volver a cambiarla si no tiene la etiqueta 'q'
        if(command.equals(efectSonidos.getLabel())){
            System.out.println("Esperando nueva tecla ");
            esperandoNuevaTeclaefectSonido = true;
            efectSonidos.setLabel("?");
            requestFocus(); // Solicitar el foco después de hacer clic, no sé, me lo dijo gemini :o
            pack();
        }
        if (e.getSource() == musicaFondo) {
            System.out.println("Esperando nueva tecla de musica de fondo");
            esperandoNuevaTeclaMusicaFondo = true;
            musicaFondo.setLabel("?");
            requestFocus();
            pack();
        }
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
            System.exit(0);
            pack();
        }
        if (e.getSource() == bReset) {
            setbReset();
            pack();
        }
        if (e.getSource() == bSkin) {
            System.out.println("Skin");
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
        efectSonidos.setLabel("Q");
        musicaFondo.setLabel("W");
        p.setLabel("Espacio");
        bAcelerar.setLabel("e");
        bAutoDestruccion.setLabel("a");
        bIniciar.setLabel("Enter");
        bReset.setLabel("Resetear");
//        bSkin.setLabel("Skin"); No tiene actionListener y tira error asi pe

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
        if (esperandoNuevaTeclaefectSonido) {
            String nuevaTecla = KeyEvent.getKeyText(e.getKeyCode());// nuevaTecla tiene la nueva tecla
            efectSonidos.setLabel(nuevaTecla);
             // Cambiar la etiqueta del botón
            esperandoNuevaTeclaefectSonido = false; // Resetear la selección
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
        // No es necesario para este ejemplo
    }

    public void keyTyped(KeyEvent e) {
        // No es necesario para este ejemplo
    }
}
