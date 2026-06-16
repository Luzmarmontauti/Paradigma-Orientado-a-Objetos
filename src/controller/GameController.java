package controller;

import model.Barco;
import model.CargaDeProfundidad;
import model.Juego;
import model.Area;
import view.BarcoView;
import view.CargaDeProfundidadView;
import view.SubmarinoView;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador principal del juego. Recibe los eventos del jugador,
 * los delega al modelo y provee la información que necesita la Vista para renderizarse.
 * Implementa el patrón Singleton para garantizar una única instancia.
 */
public class GameController {

    //Atributos
    private static GameController instance;
    private Juego juego;
    private static final Area area = new Area(600, 800);

    //Singleton
    private GameController() {
        this.juego = new Juego();
        // TODO Fase Final: no iniciar la partida acá, esperar que el jugador elija desde el menú
        juego.iniciarPartida();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    //Metodos
    public void moverTecla(String tecla) {
        juego.moverSubmarino(tecla);
    }

    public void actualizar() {
        boolean nivelSuperado = juego.verificarFinNivel();
        juego.actualizar();
        if (nivelSuperado) {
            juego.pasarSiguienteNivel();
        }
    }

    //Getters para la Vista
    public SubmarinoView getSubmarinoView() {
        return juego.getSubmarino().toView();
    }

    public List<BarcoView> getBarcoView() {
        List<BarcoView> barcosAux = new ArrayList<>();
        for ( int i = 0 ; i < juego.getBarcosActivos().size(); i++ ) {
            Barco barcoAux = juego.getBarcosActivos().get(i);
            barcosAux.add((barcoAux.toView()));
        }
        return barcosAux;
    }

    public List<CargaDeProfundidadView> getCargaView() {
        List<CargaDeProfundidadView> cargasAux = new ArrayList<>();
        for ( int i = 0 ; i < juego.getCargasActivas().size(); i++ ) {
            CargaDeProfundidad cargaAux = juego.getCargasActivas().get(i);
            cargasAux.add((cargaAux.toView()));
        }
        return cargasAux;
    }


    public int getNivel() { return juego.getNivel(); }
    public int getVidas() { return juego.getVidas(); }
    public int getPorcentajeVida() { return juego.getPorcentajeVida(); }
    public int getPuntaje() { return juego.getPuntaje(); }
    public boolean isJuegoTerminado() { return juego.getEstado().equals("GAME OVER"); }
    public int getAnchoArea() { return area.getAnchoPantalla(); }
    public int getAltoArea() { return area.getAltoPantalla(); }
}
