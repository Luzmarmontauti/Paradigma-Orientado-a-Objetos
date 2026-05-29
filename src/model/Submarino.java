package model;

/**
 * Submarino controlado por el jugador. Se mueve en las cuatro direcciones
 * dentro de los límites de profundidad permitidos.
 */
public class Submarino {

    // Atributos

    protected static final double PROF_MIN = 300;
    protected static final double PROF_MAX = 800;
    private double posicionX;
    private double profundidad;

    // Constructor

    public Submarino() {
        this.posicionX = 0;
        this.profundidad = 0;
    }

    // Comportamiento

    public void inicializar() {
    }

    public void moverIzquierda() {
    }

    public void moverDerecha() {
    }

    public void moverArriba() {
    }

    public void moverAbajo() {
    }

    // Getters y Setters

    public double getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    public double getProfundidad() {
        return profundidad;
    }

    public void setProfundidad(double profundidad) {
        this.profundidad = profundidad;
    }
}
