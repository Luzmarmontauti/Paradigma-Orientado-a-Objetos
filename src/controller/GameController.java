package controller;

import model.Juego;
import model.Barco;
import model.CargaDeProfundidad;
import model.Submarino;
import java.util.List;
import java.util.Random;
import view.Vista;
import java.util.ArrayList;

public class GameController {
    private static GameController instance;
    private Juego juego;
    private static final double ANCHO_PANTALLA = 800;

    private GameController() {
        this.juego = new Juego(new Random());
        juego.iniciarPartida(ANCHO_PANTALLA);
    }

    public static GameController getInstance() {
        if (instance == null) { instance = new GameController(); }
        return instance;
    }

    public void moverTecla(String tecla) {
        juego.moverSubmarino(tecla);
    }

    public void actualizar() {
        boolean nivelSuperado = juego.verificarFinNivel();
        juego.actualizar(ANCHO_PANTALLA);
        if (nivelSuperado) { juego.pasarSiguienteNivel(); }
    }

    public boolean isJuegoTerminado() { return juego.getEstado().equals("GAME OVER"); }
    public double obtenerCoordenadaX() { return juego.getSubmarinoX(); }
    public double obtenerCoordenadaY() { return juego.getSubmarinoY(); }

    // METODOS ENLACE VISTA
    public List<Vista> getBarcosVista(){
        List<Vista> listaBarcosVista = new ArrayList<>();
        for (Barco b : juego.getBarcosActivos()) {
            listaBarcosVista.add(b.toView());
        }
        return listaBarcosVista;
    }

    public List<Vista> getCargasVista(){
        List<Vista> listaCargasVista = new ArrayList<>();
        for (CargaDeProfundidad c : juego.getCargasActivas()) {
            listaCargasVista.add(c.toView());
        }
        return listaCargasVista;
    }

    public Vista getSubmarinoVista() {
        return juego.getSubmarino().toView();
    }
    
 // AGREGÁ ESTOS MÉTODOS AL FINAL DE TU CLASE GameController

    public int getPuntaje() {
        return juego.getPuntaje();
    }

    public int getVidas() {
        return juego.getVidas();
    }

    public int getNivel() {
        return juego.getNivel();
    }

    public int getPorcentajeVida() {
        return juego.getPorcentajeVida();
    }
    public double getSubmarinoProfundidad() {
        return juego.getSubmarinoY(); // RF-25
    }

    public String checkMensajePuntos() {
        return juego.getUltimoMensajePuntos(); // RF-29
    }
}