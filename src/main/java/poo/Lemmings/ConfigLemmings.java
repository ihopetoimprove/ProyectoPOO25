package poo.Lemmings;

import poo.ConfigP;

import javax.swing.*;
import java.awt.*;

public class ConfigLemmings extends ConfigP {
    protected final String[] acciones = {"pausa"};
    public ConfigLemmings() {
        super();
        super.agregarBotones(acciones);
//    checkSound();
//    checkMusic();
    //ComboBox musica
//    BoxMusica();
    //ComboBox skin
//    BoxSkin();
//    cargarConfiguracion(); // Intentar cargar la configuración, si se cambia de lugar algunas weas dejan de andar,
    // por ejemplo, no se guardan los cambios de lo de abajo

//    pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screenSize.width - getWidth()) / 2;
    int y = (screenSize.height - getHeight()) / 2;
    setLocation(x, y);
    }

}

