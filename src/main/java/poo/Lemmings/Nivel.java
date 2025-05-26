package poo.Lemmings;

import poo.ObjetoGrafico;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Nivel extends ObjetoGrafico {

    List<List<Integer>> mapa;
    private int entradaX, entradaY;
    private int salidaX, salidaY;
    private BufferedImage imagenTerrenoSolido;
    private BufferedImage imagenTerrenoDestructible;
    private BufferedImage imagenEntrada;
    private BufferedImage imagenSalida;
    private final int BLOQUE_ANCHO = 32;
    private final int BLOQUE_ALTO = 32;
    private int totalLemmings;
    private int lemmingsASalvar;
    private int tiempoLimite;

    public Nivel(String archivoNivel) {
        super(0, 0); // La posición del nivel en la pantalla puede ser 0,0 por ahora
        cargarImagenesTerreno();
        cargarNivelDesdeArchivo("/src/main/resources/niveles/" + archivoNivel); // Asume que los archivos .txt están en /recursos/niveles/
    }

    private void cargarImagenesTerreno() {
        try {
            imagenTerrenoSolido = ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/terreno_solido.png"));
            imagenTerrenoDestructible = ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/terreno_destructible.png"));
            imagenEntrada = ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/entrada.png"));
            imagenSalida = ImageIO.read(getClass().getClassLoader().getResourceAsStream("imagenes/salida.png"));
        } catch (IOException e) {
            System.err.println("Error al cargar imágenes de terreno o entrada/salida: " + e.getMessage());
            // Opcional: Asignar imágenes de fallback (ej. cuadrados de color)
            // para que el juego no falle si una imagen no carga.
        } catch (IllegalArgumentException e) {
            System.err.println("Recurso de imagen no encontrado. Asegúrate de que las rutas son correctas y las imágenes existen: " + e.getMessage());
        }
    }

    private void cargarNivelDesdeArchivo(String rutaArchivo) {
        mapa = new ArrayList<>(); // Inicializa el mapa para este nivel

        String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" + currentDir);
        rutaArchivo = currentDir + rutaArchivo;

        //getClass().getClassLoader().getResourceAsStream(rutaArchivo));
        try {

            String stringConfig = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            String linea;
            boolean leyendoMapa = false;

//            while ((linea = reader.readLine()) != null) {
//                linea = linea.trim(); // Eliminar espacios al inicio y fin de la línea
//
//                if (linea.isEmpty()) {
//                    continue; // Saltar líneas vacías
//                }
//
//                if (linea.startsWith("#")) { // Líneas de configuración (metadatos del nivel)
//                    if (linea.startsWith("#LEMMINGS_TOTAL:")) {
//                        totalLemmings = Integer.parseInt(linea.split(":")[1].trim());
//                    } else if (linea.startsWith("#LEMMINGS_SALVAR:")) {
//                        lemmingsASalvar = Integer.parseInt(linea.split(":")[1].trim());
//                    } else if (linea.startsWith("#TIEMPO_LIMITE:")) {
//                        tiempoLimite = Integer.parseInt(linea.split(":")[1].trim());
//                    } else if (linea.startsWith("#ENTRADA:")) {
//                        String[] coords = linea.split(":")[1].trim().split(",");
//                        entradaX = Integer.parseInt(coords[0].trim());
//                        entradaY = Integer.parseInt(coords[1].trim());
//                    } else if (linea.startsWith("#SALIDA:")) {
//                        String[] coords = linea.split(":")[1].trim().split(",");
//                        salidaX = Integer.parseInt(coords[0].trim());
//                        salidaY = Integer.parseInt(coords[1].trim());
//                    } else if (linea.startsWith("#MAPA")) { // Indica el comienzo de los datos del mapa
//                        leyendoMapa = true;
//                    }
//                } else if (leyendoMapa) { // Líneas del mapa en sí
//                    List<Integer> fila = new ArrayList<>();
//                    for (char c : linea.toCharArray()) {
//                        // Convierte el carácter a su valor numérico (ej. '1' a 1)
//                        fila.add(Character.getNumericValue(c));
//                    }
//                    mapa.add(fila);
//                }
//            }
            // System.out.println("Nivel '" + rutaArchivo + "' cargado. Dimensiones: " + getAnchoEnTiles() + "x" + getAltoEnTiles());

        } catch (IOException e) {
            System.err.println("Error al leer el archivo de nivel '" + rutaArchivo + "': " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Error de formato numérico en el archivo de nivel '" + rutaArchivo + "': " + e.getMessage());
        } catch (NullPointerException e) {
            System.err.println("El recurso del archivo de nivel '" + rutaArchivo + "' no fue encontrado.");
        }
    }

    @Override
    public void dibujar(Graphics2D g) {
        // Dibujar el terreno tile por tile
        if (mapa == null) {
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
                            imagenADibujar = imagenTerrenoSolido;
                            break;
                        case 2:
                            imagenADibujar = imagenTerrenoDestructible;
                            break;
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
        } else {
            // Mensaje de depuración si el mapa está vacío o nulo
            System.out.println("ADVERTENCIA: El mapa del nivel está vacío o no se cargó correctamente. No se dibujará el terreno.");
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("ERROR: MAPA NO CARGADO!", 100, 100);
        }

        // Dibujar la imagen de la entrada
        if (imagenEntrada != null) {
            g.drawImage(imagenEntrada, entradaX - BLOQUE_ANCHO / 2, entradaY - BLOQUE_ALTO, BLOQUE_ANCHO, BLOQUE_ALTO, null);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(entradaX - 10, entradaY - 20, 20, 20);
            g.setColor(Color.WHITE);
            g.drawString("E", entradaX - 5, entradaY - 5);
        }

        // Dibujar la imagen de la salida
        if (imagenSalida != null) {
            g.drawImage(imagenSalida, salidaX - BLOQUE_ANCHO / 2, salidaY - BLOQUE_ALTO, BLOQUE_ANCHO, BLOQUE_ALTO, null);
        } else {
            g.setColor(Color.GREEN);
            g.fillRect(salidaX - 10, salidaY - 20, 20, 20);
            g.setColor(Color.BLACK);
            g.drawString("S", salidaX - 5, salidaY - 5);
        }
    }
}
