package poo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

import static java.awt.event.KeyEvent.getKeyText;

public class ConfigP extends Frame implements ActionListener, KeyListener {
    protected final Map<String, Integer> teclas=new HashMap<>();
    protected final Map<String, Button> botonesAccion = new HashMap<>();
    protected String accionConfigurando = null;
    Button bGuardar, bReset;
    protected String[] acciones ={};

    public ConfigP(){
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                dispose();
            }
        };
        addKeyListener(this);
        addWindowListener(l);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setVisible(true);
    }
    protected void agregarBotones(String[] acciones){
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
    }
    protected void mResetTeclas() {
        for (String accion : acciones) {
            teclas.put(accion.toLowerCase(), getDefaultKey(accion));
            botonesAccion.get(accion).setLabel(getKeyText(getDefaultKey(accion)));
        }
    }
    protected int getDefaultKey(String accion) {
        switch (accion.toLowerCase()) {
            default: return KeyEvent.VK_UNDEFINED;
        }
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
    public void setTecla(String accion, int codigoTecla) {
        teclas.put(accion, codigoTecla);
    }
    @Override
    public void actionPerformed(ActionEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
    protected void cargarConfiguracion() {}
    protected void guardarConfiguracion() {}
}
