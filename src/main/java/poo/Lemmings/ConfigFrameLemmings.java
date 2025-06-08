package poo.Lemmings;
import poo.ClasesCompartidas.SuperFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Map;
import static java.awt.event.KeyEvent.getKeyText;
import static poo.Lemmings.ConfigLemmings.*;

public class ConfigFrameLemmings extends SuperFrame {
    private JCheckBox checkSound;
    private JCheckBox checkMusic;
    Panel panelOpcionesMusica;
    Panel panelOpcionesSonido;
    JComboBox<String> musicComboBox;
    String tema_musical;
    ConfigLemmings config=new ConfigLemmings();

    public ConfigFrameLemmings() {
        super("Configuración de Lemming");
        checkSound();
        checkMusic();
        //ComboBox musica
        BoxMusica();
        agregarBotones(config.getTeclasLemmings());
        botonGuardaryResetear();
        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }
    public void botonGuardaryResetear(){
        Panel acepReset = new Panel(new FlowLayout(FlowLayout.CENTER));
        bGuardar = new Button("Aceptar");
        acepReset.add(bGuardar);
        bGuardar.addActionListener(e -> {
                    config.guardarConfiguracion(this);
                    dispose();
                }
        );
        bReset = new Button("Resetear");
        acepReset.add(bReset);
        bReset.addActionListener(e -> mResetTeclas());// no pongo dispose pq me gusta tener que guardar despues de resetear como para confirmar
        add(acepReset);
    }

    public void setTecla(String accion, int codigoTecla) {
        config.getTeclasLemmings().put(accion, codigoTecla);
    }
    protected void iniciarConfiguracion(String accion, Button botonAsociado) {
        this.accionConfigurando = accion;
        botonAsociado.setLabel("?");
        botonAsociado.setEnabled(false); // Deshabilitar el botón mientras esperamos la tecla
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (accionConfigurando != null) {
                    int nuevoCodigoTecla = e.getKeyCode();
                    setTecla(accionConfigurando, nuevoCodigoTecla);
                    botonAsociado.setLabel(KeyEvent.getKeyText(nuevoCodigoTecla));
                    botonAsociado.setEnabled(true);
                    accionConfigurando = null; // Resetear el estado
                    removeKeyListener(this); // Quitar el KeyListener temporal
                }
            }
        });
    }
    protected void agregarBotones(Map<String, Integer> teclasLemmings) {
        for (String accion : config.getAcciones()) {
            Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER));
            panel.add(new JLabel(accion + ":    "));
            Button boton = new Button(getKeyText(teclasLemmings.getOrDefault(accion, getDefaultKey(accion))));
            botonesAccion.put(accion, boton);
            panel.add(boton);
            boton.addActionListener(e -> iniciarConfiguracion(accion, boton)); // addActionListener(e -> iniciarCambioTecla(accion, boton)) es otra forma
            add(panel);
        }
    }

    private void BoxMusica(){
        Panel SelMusica = new Panel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelMusica = new JPanel();
        panelMusica.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelMusica = new JLabel("Pista musical: ");
        String[] musicTracks = {config.getDefaultMusic(),"smstitle.wav", "Macarena"};
        musicComboBox =new JComboBox<>(musicTracks);
        musicComboBox.setSelectedItem(config.getMusicaElegida());
        panelMusica.add(labelMusica);
        panelMusica.add(musicComboBox);
        SelMusica.add(panelMusica);
        add(SelMusica);
    }

    private void checkSound(){
        panelOpcionesSonido = new Panel(new FlowLayout(FlowLayout.CENTER));
        checkSound = new JCheckBox("Efectos de Sonido", true);
        panelOpcionesSonido.add(checkSound);
        add(panelOpcionesSonido);
        if(checkSound!=null)
            checkSound.setSelected(config.getEstadoSonido());
    }
    private void checkMusic(){
        panelOpcionesMusica = new Panel(new FlowLayout(FlowLayout.CENTER));
        checkMusic = new JCheckBox("Musica", true);
        panelOpcionesMusica.add(checkMusic);
        add(panelOpcionesMusica);
        if(checkMusic!=null)
            checkMusic.setSelected(config.getEstadoMusica());
    }


    protected void mResetTeclas() {
        for (String accion : config.getAcciones()) {
            config.getTeclasLemmings().put(accion.toLowerCase(), getDefaultKey(accion));
            botonesAccion.get(accion).setLabel(getKeyText(getDefaultKey(accion)));
        }
        musicComboBox.setSelectedItem(config.getDefaultMusic());
        checkMusic.setSelected(true);
        checkSound.setSelected(true);
    }

    public boolean getEstadoSonido(){return this.checkSound.isSelected();}
    public boolean getEstadoMusica(){return this.checkMusic.isSelected();}
    public String getMusicaElegida(){
        if(musicComboBox.getSelectedItem().toString()!=null)
            tema_musical=musicComboBox.getSelectedItem().toString();
        return this.tema_musical;}



}

