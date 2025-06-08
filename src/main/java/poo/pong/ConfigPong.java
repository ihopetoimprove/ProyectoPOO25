package poo.pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigPong {
    private final String CONFIG_FILE = "config.pong";
    private static final String DEFAULT_MUSIC = "Tema original";
    private final Map<String, Integer> teclasPong =new HashMap<>();
    private final String[] acciones = {"pausa","subir(j1)", "bajar(j1)", "subir(j2)", "bajar(j2)"};
    private boolean HabilitarSonido;
    private boolean HabilitarMusica;
    private String Tema_Musical;
    static Color color= Color.WHITE;

    public ConfigPong(){
        super();
        cargarConfiguracion();
    }
    public void cargarConfiguracion() {
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
                System.out.println("Música cargada: " + HabilitarMusica);
            } else {
                HabilitarMusica = true; // Valor por defecto si la clave no se encuentra
                System.out.println("Clave 'Musica: ' no encontrada. Usando valor por defecto: " + HabilitarMusica);
            }
            if (prop.getProperty("Efectos de Sonido") != null) {
                HabilitarSonido = Boolean.parseBoolean(prop.getProperty("Efectos de Sonido"));
            }else {
                HabilitarSonido = true; // Valor por defecto si la clave no se encuentra
                System.out.println("Clave 'Sonido: ' no encontrada. Usando valor por defecto: " + HabilitarSonido);
            }
            if(prop.getProperty("Musica_seleccionada")!= null){
                Tema_Musical = prop.getProperty("Musica_seleccionada");
            }else {Tema_Musical = DEFAULT_MUSIC;}


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
    public boolean getEstadoSonido(){return this.HabilitarSonido;}
    public boolean getEstadoMusica(){return this.HabilitarMusica;}
    public String getDefaultMusic(){return this.DEFAULT_MUSIC;}
    public String getMusicaElegida(){return this.Tema_Musical;}
    public Map<String, Integer> getTeclasPong(){return teclasPong;}
    public String[] getAcciones(){return acciones;}

    public void guardarConfiguracion(ConfigFramePong frame) {
        Properties prop = new Properties();
        for (String accion : acciones) {
            if (teclasPong.containsKey(accion)) {
                prop.setProperty(accion, String.valueOf(teclasPong.get(accion)));
            } else {
                prop.setProperty(accion, String.valueOf(getDefaultKey(accion)));
            }
        }
        if (frame.getMusicaElegida() != null) {
            prop.setProperty("Musica_seleccionada", frame.getMusicaElegida());
        } else {prop.setProperty("Musica_seleccionada", DEFAULT_MUSIC);}
        prop.setProperty("Efectos de Sonido", frame.getEstadoSonido() ? "true" : "false");
        prop.setProperty("Musica: ", frame.getEstadoMusica() ? "true" : "false");
        try (OutputStream output= new FileOutputStream(CONFIG_FILE)){
            prop.store(output, "Configuracion");
            JOptionPane.showMessageDialog(null, "Configuracion guardada en " + CONFIG_FILE, "Guardado", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar la configuración en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    protected int getDefaultKey(String accion) {
        return switch (accion.toLowerCase()) {
            case "pausa" -> KeyEvent.VK_SPACE;
            case "subir(j1)" -> KeyEvent.VK_W;
            case "bajar(j1)" -> KeyEvent.VK_S;
            case "subir(j2)" -> KeyEvent.VK_UP;
            case "bajar(j2)" -> KeyEvent.VK_DOWN;
            default -> KeyEvent.VK_UNDEFINED;
        };
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
