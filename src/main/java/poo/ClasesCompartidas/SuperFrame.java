package poo.ClasesCompartidas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class SuperFrame extends Frame implements ActionListener, KeyListener {
    protected Button bGuardar, bReset;
    protected String accionConfigurando = null;
    protected final Map<String, Button> botonesAccion = new HashMap<>();
    protected String[] acciones ={};

    public SuperFrame() {
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
    public SuperFrame(String titulo) {
        setTitle(titulo);
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


    protected void mResetTeclas() {}



    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}



