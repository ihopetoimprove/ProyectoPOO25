package poo;

public class Jugador {
    private String nombre;
    private int record;

    public Jugador(){}
    public Jugador(String nombre, int puntuacion){
        this.nombre=nombre;
        this.record=puntuacion;
    }

    public void setNombre(String nombre){this.nombre = nombre;}

    public void setRecord(int record){this.record = record;}

    public String getNombre(){return nombre;}

    public int getRecord(){return record;}
}
