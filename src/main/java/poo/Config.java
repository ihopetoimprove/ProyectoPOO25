package poo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import static java.awt.event.KeyEvent.getKeyText;

public class Config extends Frame implements ActionListener, KeyListener {
    private static final String DEFAULT_MUSIC = "Tema original";
    JCheckBox checkSound;
    JCheckBox checkMusic;
    Button bGuardar, bReset;
    Panel panelOpcionesMusica;
    Panel panelOpcionesSonido;
    private String accionConfigurando = null;
    private static final String CONFIG_FILE = "config.properties";
    private final Map<String, Integer> teclas = new HashMap<>();
    private final Map<String, Button> botonesAccion = new HashMap<>();
    private final String[] acciones = {"pausa", "acelerar", "autodestruccion", "subir(j1)", "bajar(j1)", "subir(j2)", "bajar(j2)"};
    JComboBox<String> musicComboBox;
    JComboBox<String> skinComboBox;

    public Config() {
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                dispose();
            }
        };
        addKeyListener(this);
        addWindowListener(l);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        checkSound();
        checkMusic();
        //ComboBox musica
        BoxMusica();
        //ComboBox skin
        BoxSkin();
        cargarConfiguracion(); // Intentar cargar la configuración, si se cambia de lugar algunas weas dejan de andar,
        // por ejemplo, no se guardan los cambios de lo de abajo

        for (String accion : acciones) {
            Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER));
            panel.add(new JLabel(accion + ":    "));
            Button boton = new Button(getKeyText(teclas.getOrDefault(accion, getDefaultKey(accion))));
            botonesAccion.put(accion, boton);
            panel.add(boton);
            boton.addActionListener(e -> iniciarConfiguracion(accion, boton)); // addActionListener(e -> iniciarCambioTecla(accion, boton)) es otra forma
            add(panel);
        }
        Panel acepReset = new Panel(new FlowLayout(FlowLayout.CENTER));
        bGuardar = new Button("Aceptar");
        acepReset.add(bGuardar);
        bGuardar.addActionListener(e -> guardarConfiguracion());
        bReset = new Button("Resetear");
        acepReset.add(bReset);
        bReset.addActionListener(e -> mResetTeclas());
        add(acepReset);

        pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
        setVisible(true);
    }
    private void BoxMusica(){
        Panel SelMusica = new Panel(new FlowLayout(FlowLayout.CENTER));
        JPanel panelMusica = new JPanel();
        panelMusica.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel labelMusica = new JLabel("Pista musical: ");
        String[] musicTracks = {DEFAULT_MUSIC, "Pista 1", "Pista 2"};
        musicComboBox =new JComboBox<>(musicTracks);
        panelMusica.add(labelMusica);
        panelMusica.add(musicComboBox);
        SelMusica.add(panelMusica);
        add(SelMusica);
    }
    private void BoxSkin(){
        Panel SelSkin = new Panel(new FlowLayout(FlowLayout.CENTER));
        JPanel skinPanel = new JPanel();
        skinPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel skinLabel = new JLabel("Skin del PJ: ");
        String[] skins = {"Skin 1", "Skin 2", "Skin 3"};
        skinComboBox = new JComboBox<>(skins);
        skinPanel.add(skinLabel);
        skinPanel.add(skinComboBox);
        SelSkin.add(skinPanel);
        add(SelSkin);
    }
    private void checkSound(){
        panelOpcionesSonido = new Panel(new FlowLayout(FlowLayout.CENTER));
        checkSound = new JCheckBox("Efectos de Sonido :", true);
        panelOpcionesSonido.add(checkSound);
        add(panelOpcionesSonido);
    }
    private void checkMusic(){
        panelOpcionesMusica = new Panel(new FlowLayout(FlowLayout.CENTER));
        checkMusic = new JCheckBox("Musica: ", true);
        panelOpcionesMusica.add(checkMusic);
        add(panelOpcionesMusica);
    }
    private void iniciarConfiguracion(String accion, Button botonAsociado) {
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
    public void setTecla(String accion, int codigoTecla) {
        teclas.put(accion, codigoTecla);
    }

    private int getDefaultKey(String accion) {
        switch (accion.toLowerCase()) {
            case "pausa": return KeyEvent.VK_SPACE;
            case "autodestruccion": return KeyEvent.VK_D;
            case "acelerar": return KeyEvent.VK_A;
            case "subir(j1)": return KeyEvent.VK_W;
            case "bajar(j1)": return KeyEvent.VK_S;
            case "subir(j2)": return KeyEvent.VK_UP;
            case "bajar(j2)": return KeyEvent.VK_DOWN;
            default: return KeyEvent.VK_UNDEFINED;
        }
    }

    public void mResetTeclas() {
        for (String accion : acciones) {
            teclas.put(accion.toLowerCase(), getDefaultKey(accion));
            botonesAccion.get(accion).setLabel(getKeyText(getDefaultKey(accion)));
        }
        musicComboBox.setSelectedItem(DEFAULT_MUSIC);
        skinComboBox.setSelectedItem("Skin 1");
        checkMusic.setSelected(true);
        checkSound.setSelected(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {    }
    public void keyReleased(KeyEvent e) {
        // Innecesario por ahora
    }

    public void keyTyped(KeyEvent e) {
        // Innecesario por ahora
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    private void cargarConfiguracion() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            prop.load(input);
            for (String accion : acciones) {
                String keyStr = prop.getProperty(accion);
                if (keyStr != null) {
                    teclas.put(accion, Integer.parseInt(keyStr));
                } else {
                    teclas.put(accion, getDefaultKey(accion)); // valor predeterminado si no se encuentra
                }
            }
            String MusicaGuardada = prop.getProperty("Musica_seleccionada");
            if (MusicaGuardada != null && musicComboBox != null) {
                musicComboBox.setSelectedItem(MusicaGuardada);
            }
            String SkinGuardada = prop.getProperty("Skin_seleccionada");
            if (SkinGuardada != null && skinComboBox != null) {
                skinComboBox.setSelectedItem(SkinGuardada);
            }
            checkMusic.setSelected(Boolean.parseBoolean(prop.getProperty(checkMusic.getText())));
            checkSound.setSelected(Boolean.parseBoolean(prop.getProperty(checkSound.getText())));
        } catch (IOException ex) {
            System.out.println("No se encontró el archivo de configuración o hubo un error al leerlo. Usando valores predeterminados.");
            for (String accion : acciones) {
                teclas.put(accion, getDefaultKey(accion));
            }
            if (musicComboBox != null) {
                musicComboBox.setSelectedItem("DEFAULT_MUSIC"); // O el valor predeterminado real
            }
            if (skinComboBox != null) {
                skinComboBox.setSelectedItem("Skin 1"); // O el valor predeterminado real
            }
            checkMusic.setSelected(true);
            checkSound.setSelected(true);
        }
    }
    private void guardarConfiguracion() {
        Properties prop = new Properties();
        for (String accion : acciones) {
            if (teclas.containsKey(accion)) {
                prop.setProperty(accion, String.valueOf(teclas.get(accion)));
            } else {
                prop.setProperty(accion, String.valueOf(getDefaultKey(accion)));
            }
        }
        String MusicaAGuardar = (String) musicComboBox.getSelectedItem();
        if (MusicaAGuardar != null) {
            prop.setProperty("Musica_seleccionada", MusicaAGuardar);
        }
        String SkinAGuardar = (String) skinComboBox.getSelectedItem();
        if (SkinAGuardar != null) {
            prop.setProperty("Skin_seleccionada", SkinAGuardar);
        }
        prop.setProperty(checkMusic.getText(), String.valueOf(checkMusic.isSelected()));
        prop.setProperty(checkSound.getText(), String.valueOf(checkSound.isSelected()));
        try (OutputStream output= new FileOutputStream(CONFIG_FILE)){
            prop.store(output, "Configuracion");
                JOptionPane.showMessageDialog(this, "Configuracion guardada en " + CONFIG_FILE, "Guardado", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar la configuración en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }


    public int getTSubirJ1(){
        return teclas.get("subir(j1)");
    }
    public int getTBajarJ1(){
        return teclas.get("bajar(j1)");
    }
    public int getTSubirJ2(){
        return teclas.get("subir(j2)");
    }
    public int getTBajarJ2(){
        return teclas.get("bajar(j2)");
    }

}
