package poo;

import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.entropyinteractive.*;

public class Launcher extends JPanel implements ActionListener {

    private List listaJuegos;
    JGame juego;
    Thread t;
    public Launcher(){
        //setTitle("Launcher");

        setLayout(new BorderLayout());
        listaJuegos = new List();
        listaJuegos.add("Pong");
        listaJuegos.add("Lemmings");
        add(listaJuegos, BorderLayout.WEST);

    }

    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("Pong")){
            //juego = new Pong();
        }
    }

    public static void main(String arg[]) {
        JFrame frame = new JFrame("Launcher");

        frame.add(new Launcher());
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            };
        };

        frame.addWindowListener(l);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}



