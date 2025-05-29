package poo.Lemmings;

public interface Habilidad{

    boolean aplicarHabilidad(Lemming lemming, Nivel nivel);
    void consumirUso();
    int getUsosRestantes();
}