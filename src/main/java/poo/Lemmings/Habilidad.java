package poo.Lemmings;

public interface Habilidad {

    boolean aplicarHabilidad(Lemming lemming, Nivel nivel);
    String getNombre();
    boolean consumirUso();
    int getUsosRestantes();
    void setUsos(int usos);
}