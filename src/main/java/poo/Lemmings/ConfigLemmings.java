package poo.Lemmings;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigLemmings {
    private final String CONFIG_FILE = "config.Lemmings";
    private final String DEFAULT_MUSIC = "Tema original";
    private final Map<String, Integer> teclasLemmings =new HashMap<>();
    private final String[] acciones = {"pausa","autodestruccion","acelerar"};
    private final String[] musica = {DEFAULT_MUSIC,"Synthwave", "Macarena"};
    private String Tema_Musical;
    private boolean HabilitarSonido;
    private boolean HabilitarMusica;


    public ConfigLemmings(){
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
                    teclasLemmings.put(accion, Integer.parseInt(keyStr));
                } else {
                    teclasLemmings.put(accion, getDefaultKey(accion)); // valor predeterminado si no se encuentra
                }
            }
            if (prop.getProperty("Musica: ") != null) {
                HabilitarMusica = Boolean.parseBoolean(prop.getProperty("Musica: "));
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
                teclasLemmings.put(accion, getDefaultKey(accion));
            }
        }
    }
    public boolean getEstadoSonido(){return this.HabilitarSonido;}
    public boolean getEstadoMusica(){return this.HabilitarMusica;}
    public String[] getListaMusica(){return this.musica;}
    public String getDefaultMusic(){return this.DEFAULT_MUSIC;}
    public String getMusicaElegida(){return this.Tema_Musical;}
    public Map<String, Integer> getTeclasLemmings(){return teclasLemmings;};
    public String[] getAcciones(){return acciones;}

    public void guardarConfiguracion(ConfigFrameLemmings frame) {
        Properties prop = new Properties();
        for (String accion : acciones) {
            if (teclasLemmings.containsKey(accion)) {
                prop.setProperty(accion, String.valueOf(teclasLemmings.get(accion)));
            } else {
                prop.setProperty(accion, String.valueOf(getDefaultKey(accion)));
            }
        }
        if (frame.getMusicaElegida()!=null) {
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
        switch (accion.toLowerCase()) {
            case "pausa": return KeyEvent.VK_SPACE;
            case "autodestruccion": return KeyEvent.VK_D;
            case "acelerar": return KeyEvent.VK_A;
            default: return KeyEvent.VK_UNDEFINED;
        }
    }

    public int getPausa(){
        return teclasLemmings.get("pausa");
    }

}
