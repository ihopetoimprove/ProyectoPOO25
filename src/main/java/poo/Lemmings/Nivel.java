package poo.Lemmings;

import poo.ClasesCompartidas.ObjetoGrafico;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Nivel extends ObjetoGrafico {
    public static final int BLOQUE_ANCHO = 32;
    public static final int BLOQUE_ALTO = 32;

    List<List<Integer>> mapa;
    private int entradaX, entradaY;
    private int salidaX, salidaY;
    private static BufferedImage imagenTerrenoSolido;
    private static BufferedImage imagenTerrenoDestructible;
    private static BufferedImage imagenEntrada;
    private static BufferedImage imagenSalida;
    private static BufferedImage imagenLava;


    public Nivel(String archivoNivel) {
        super(0, 0);
        JOptionPane.showMessageDialog(null, "Entro a nivel", "nivel", JOptionPane.INFORMATION_MESSAGE);

        cargarImagenesTerreno();
        JOptionPane.showMessageDialog(null, "cargoterreno", "nivel", JOptionPane.INFORMATION_MESSAGE);

        try (InputStream ruta=getClass().getResourceAsStream("/niveles/" + archivoNivel)){
        if (ruta != null) {
            cargarNivelDesdeArchivo(ruta);
        } else {
            System.err.println("Recurso de nivel no encontrado: /niveles/" + archivoNivel);
            // Manejar el caso de que el nivel no se encuentre
        }
    } catch (IOException e) {
        System.err.println("Error al obtener el InputStream para el nivel: " + e.getMessage());
    }

    }

    private void cargarImagenesTerreno() {
        try {
            imagenTerrenoSolido = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/Lemmings/terreno_solido.png")));
            imagenTerrenoDestructible = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/Lemmings/terreno_destructible.png")));
            imagenEntrada = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/Lemmings/entrada.png")));
            imagenSalida = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/Lemmings/salida.png")));
            imagenLava = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("imagenes/Lemmings/lava.jpg")));
        } catch (IOException e) {
            System.err.println("Error al cargar imágenes de terreno o entrada/salida: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Recurso de imagen no encontrado. Asegúrate de que las rutas son correctas y las imágenes existen: " + e.getMessage());
        }
    }

    private void cargarNivelDesdeArchivo(InputStream inputStream) {
        mapa = new ArrayList<>(); // Inicializa el mapa para este nivel
        try {
            String linea;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            boolean leyendoMapa = false;

            while ((linea = reader.readLine()) != null) {
                linea = linea.trim(); // Eliminar espacios al inicio y fin de la línea
                if (linea.isEmpty()) {
                    continue; // Saltar líneas vacías
                }

                if (linea.startsWith("#")) { // Líneas de configuración (metadatos del nivel)
                    if (linea.startsWith("#ENTRADA:")) {
                        String[] coords = linea.split(":")[1].trim().split(",");
                        entradaX = Integer.parseInt(coords[0].trim());
                        entradaY = Integer.parseInt(coords[1].trim());
                    } else if (linea.startsWith("#SALIDA:")) {
                        String[] coords = linea.split(":")[1].trim().split(",");
                        salidaX = Integer.parseInt(coords[0].trim());
                        salidaY = Integer.parseInt(coords[1].trim());
                    } else if (linea.startsWith("#MAPA")) { // Indica el comienzo de los datos del mapa
                        leyendoMapa = true;
                    }
                } else if (leyendoMapa) { // Líneas del mapa en sí
                    List<Integer> fila = new ArrayList<>();
                    for (char c : linea.toCharArray()) {
                        // Convierte el carácter a su valor numérico (ej. '1' a 1)
                        fila.add(Character.getNumericValue(c));
                    }
                    mapa.add(fila);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo de nivel " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error de formato numérico en el archivo de nivel "+e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("El recurso del archivo de nivel no fue encontrado.");
        }
    }

    @Override
    public void dibujar(Graphics2D g) {
            for (int yTile = 0; yTile < mapa.size(); yTile++) {
                List<Integer> fila = mapa.get(yTile);
                // Asegurarse de que la fila no es nula y tiene elementos
                if (fila == null || fila.isEmpty()) {
                    continue;
                }
                for (int xTile = 0; xTile < fila.size(); xTile++) {
                    int tipoTile = fila.get(xTile);
                    BufferedImage imagenADibujar = null;

                    switch (tipoTile) {
                        case 0:
                            break;
                        case 1:
                            imagenADibujar = imagenTerrenoDestructible;
                            break;
                        case 2:
                            imagenADibujar = imagenTerrenoSolido;
                            break;
                        case 3:
                            imagenADibujar = imagenLava;
                    }

                    if (imagenADibujar != null) {
                        g.drawImage(imagenADibujar, xTile * BLOQUE_ANCHO + this.x, yTile * BLOQUE_ALTO + this.y, BLOQUE_ANCHO, BLOQUE_ALTO, null);
                    } else if (tipoTile != 0) {
                        // Fallback de color si la imagen no se cargó
                        if (tipoTile == 1) g.setColor(Color.DARK_GRAY);
                        else if (tipoTile == 2) g.setColor(Color.LIGHT_GRAY);
                        else g.setColor(Color.PINK); // Para tipos de tile no reconocidos o sin imagen
                        g.fillRect(xTile * BLOQUE_ANCHO + this.x, yTile * BLOQUE_ALTO + this.y, BLOQUE_ANCHO, BLOQUE_ALTO);
                        g.setColor(Color.BLACK);
                        g.drawRect(xTile * BLOQUE_ANCHO + this.x, yTile * BLOQUE_ALTO + this.y, BLOQUE_ANCHO, BLOQUE_ALTO);
                    }
                }
            }
        if (imagenEntrada != null)
            g.drawImage(imagenEntrada, entradaX, entradaY, null);
        if (imagenSalida != null)
            g.drawImage(imagenSalida, salidaX, salidaY, null);
    }


    public void setTipoTile(int pixelX, int pixelY, int nuevoTipo) {
        int tileX = pixelX / BLOQUE_ANCHO;
        int tileY = pixelY / BLOQUE_ALTO;

        if (tileY >= 0 && tileY < mapa.size()) {
            List<Integer> fila = mapa.get(tileY);
            if (fila != null && tileX >= 0 && tileX < fila.size()) {
                // Solo modificar si es terreno destructible (tipo 2)
                int tipoActual = fila.get(tileX);
                if (tipoActual == 1) { // Puedes romper destructible y lava
                    fila.set(tileX, nuevoTipo); // Cambia el tipo de tile en el mapa
                }
            }
        }
    }

    public int getTipoTile(int pixelX, int pixelY) {
        // Convierte las coordenadas de píxel a coordenadas de tile
        int tileX = pixelX / BLOQUE_ANCHO;
        int tileY = pixelY / BLOQUE_ALTO;

        // Verifica si las coordenadas de tile están dentro de los límites del mapa
        if (tileY >= 0 && tileY < mapa.size()) {
            List<Integer> fila = mapa.get(tileY);
            if (fila != null && tileX >= 0 && tileX < fila.size()) {
                return fila.get(tileX); // Devuelve el tipo de tile
            }
        }
        // Si las coordenadas están fuera de los límites del mapa, consideramos que es "aire" o "vacío"
        // Retorna -1 para indicar que está fuera o que no es un tipo de tile válido
        return -1;
    }

    public int getEntradaX() {return entradaX;}
    public int getEntradaY() {return entradaY;}
    public int getSalidaX() {return salidaX;}
    public int getSalidaY() {return salidaY;}

}