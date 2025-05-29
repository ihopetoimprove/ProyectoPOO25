package poo.Lemmings;

public class HabilidadBomba implements Habilidad{
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
