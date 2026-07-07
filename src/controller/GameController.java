package controller;
import model.Barco;
import model.CargaDeProfundidad;
import model.Torpedo;
import model.Juego;
import model.Area;
import view.BarcoView;
import view.CargaDeProfundidadView;
import view.SubmarinoView;
import view.TorpedoView;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    private static GameController instance;
    private Juego juego;

    private GameController() {
        this.juego = new Juego();
        juego.iniciarPartida();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public static void resetearInstancia() { instance = null; }

    public void moverTecla(String tecla) {
        juego.moverSubmarino(tecla);
    }

    public void lanzarTorpedo() {
        juego.lanzarTorpedo();
    }

    public void actualizar() {
        boolean nivelSuperado = juego.verificarFinNivel();
        juego.actualizar();
        if (nivelSuperado) {
            juego.pasarSiguienteNivel();
        }
    }

    public SubmarinoView getSubmarinoView() {
        return juego.getSubmarino().toView();
    }

    public List<BarcoView> getBarcoView() {
        List<BarcoView> barcosAux = new ArrayList<>();
        for (int i = 0; i < juego.getBarcosActivos().size(); i++) {
            Barco barcoAux = juego.getBarcosActivos().get(i);
            barcosAux.add(barcoAux.toView());
        }
        return barcosAux;
    }

    public List<CargaDeProfundidadView> getCargaView() {
        List<CargaDeProfundidadView> cargasAux = new ArrayList<>();
        for (int i = 0; i < juego.getCargasActivas().size(); i++) {
            CargaDeProfundidad cargaAux = juego.getCargasActivas().get(i);
            cargasAux.add(cargaAux.toView());
        }
        return cargasAux;
    }

    public List<TorpedoView> getTorpedoView() {
        List<TorpedoView> torpedosAux = new ArrayList<>();
        for (int i = 0; i < juego.getTorpedosActivos().size(); i++) {
            Torpedo torpedoAux = juego.getTorpedosActivos().get(i);
            torpedosAux.add(torpedoAux.toView());
        }
        return torpedosAux;
    }

    public int getNivel() { return juego.getNivel(); }
    public int getVidas() { return juego.getVidas(); }
    public int getPorcentajeVida() { return juego.getPorcentajeVida(); }
    public int getPuntaje() { return juego.getPuntaje(); }
    public boolean isJuegoTerminado() { return juego.isJuegoTerminado(); }
    public int getAnchoArea() { return juego.getAnchoPantalla(); }
    public int getAltoArea() { return juego.getAltoPantalla(); }
}