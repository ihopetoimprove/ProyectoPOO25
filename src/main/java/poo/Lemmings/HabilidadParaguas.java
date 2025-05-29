package poo.Lemmings;

public class HabilidadParaguas implements Habilidad{
    private int cantidad;

    @Override
    public boolean aplicarHabilidad(Lemming lemming, Nivel nivel) {
        return false;
    }

    @Override
    public void consumirUso() {

    }

    @Override
    public int getUsosRestantes() {
        return 0;
    }
}
