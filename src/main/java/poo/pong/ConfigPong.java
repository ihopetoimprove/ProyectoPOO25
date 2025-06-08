package poo.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigPong {
    private static final String CONFIG_FILE = "config.pong";
    protected static final String DEFAULT_MUSIC = "Tema original";
    protected static Map<String, Integer> teclasPong =new HashMap<>();
    protected static final String[] acciones = {"pausa","subir(j1)", "bajar(j1)", "subir(j2)", "bajar(j2)"};
    public static boolean HabilitarSonido;
    public static boolean HabilitarMusica;
    public static String Tema_Musical;
    static Color color= Color.WHITE;

    public ConfigPong(){
        super();
        cargarConfiguracion();
    }
    public static void cargarConfiguracion() {
        Properties prop = new Properties();
        try (InputStream input = new FileInputStream(CONFIG_FILE)) {
            prop.load(input);
            for (String accion : acciones) {
                String keyStr = prop.getProperty(accion);
                if (keyStr != null) {
                    teclasPong.put(accion, Integer.parseInt(keyStr));
                } else {
                    teclasPong.put(accion, getDefaultKey(accion)); // valor predeterminado si no se encuentra
                }
            }

            if (prop.getProperty("Musica: ") != null) {
                HabilitarMusica = Boolean.parseBoolean(prop.getProperty("Musica: "));
            } else {
                HabilitarMusica = true; // Valor por defecto si la clave no se encuentra
                System.out.println("Clave 'Musica: ' no encontrada. Usando valor por defecto: " + HabilitarMusica);
            }
            String sonidoStr = prop.getProperty("Efectos de Sonido");
            if (sonidoStr != null) {
                HabilitarSonido = Boolean.parseBoolean(sonidoStr);
            }
            Tema_Musical = prop.getProperty("Musica_seleccionada");


        } catch (IOException ex) {
            System.out.println("No se encontró el archivo de configuración o hubo un error al leerlo. Usando valores predeterminados.");
            for (String accion : acciones) {
                teclasPong.put(accion, getDefaultKey(accion));
            }
        }
    }
    public static Color getColor() {
        return color;
    }


    public static void guardarConfiguracion() {
        Properties prop = new Properties();
        for (String accion : acciones) {
            if (teclasPong.containsKey(accion)) {
                prop.setProperty(accion, String.valueOf(teclasPong.get(accion)));
            } else {
                prop.setProperty(accion, String.valueOf(getDefaultKey(accion)));
            }
        }
        if (Tema_Musical != null) {
            prop.setProperty("Musica_seleccionada", Tema_Musical);
        } else {prop.setProperty("Musica_seleccionada", DEFAULT_MUSIC);}
        prop.setProperty("Efectos de Sonido", HabilitarSonido ? "true" : "false");
        prop.setProperty("Musica: ", HabilitarMusica ? "true" : "false");
        try (OutputStream output= new FileOutputStream(CONFIG_FILE)){
            prop.store(output, "Configuracion");
            JOptionPane.showMessageDialog(null, "Configuracion guardada en " + CONFIG_FILE, "Guardado", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la configuración en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    protected static int getDefaultKey(String accion) {
        switch (accion.toLowerCase()) {
            case "pausa": return KeyEvent.VK_SPACE;
            case "subir(j1)": return KeyEvent.VK_W;
            case "bajar(j1)": return KeyEvent.VK_S;
            case "subir(j2)": return KeyEvent.VK_UP;
            case "bajar(j2)": return KeyEvent.VK_DOWN;
            default: return KeyEvent.VK_UNDEFINED;
        }
    }


    public int getTSubirJ1(){
        return teclasPong.get("subir(j1)");
    }
    public int getTBajarJ1(){
        return teclasPong.get("bajar(j1)");
    }
    public int getTSubirJ2(){
        return teclasPong.get("subir(j2)");
    }
    public int getTBajarJ2(){
        return teclasPong.get("bajar(j2)");
    }
    public int getPausa(){
        return teclasPong.get("pausa");
    }

}
