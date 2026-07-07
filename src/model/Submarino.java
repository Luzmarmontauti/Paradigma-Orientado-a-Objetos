package model;

import view.SubmarinoView;

public class Submarino {

    // Atributos
    protected static final double PROF_MIN = 300;
    protected static final double PROF_MAX = 800;
    protected static final int ANCHO = 40;
    protected static final int ALTO = 20;
    private static final double VELOCIDAD_TORPEDO = 8;
    private double posicionX;
    private double profundidad;
    private int anchoPantalla;

    // Constructor
    public Submarino() {
        this.posicionX = 0;
        this.profundidad = 0;
    }

    // Metodos
    public void inicializar(int anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
        setPosicionX(anchoPantalla / 2);
        setProfundidad((PROF_MAX + PROF_MIN) / 2);
    }

    public void moverIzquierda() {
        if (posicionX > 0) setPosicionX(posicionX - 10);
    }

    public void moverDerecha() {
        if (posicionX < anchoPantalla - ANCHO) setPosicionX(posicionX + 10);
    }

    public void moverArriba() {
        if (profundidad > PROF_MIN) setProfundidad(profundidad - 10);
    }

    public void moverAbajo() {
        if (profundidad < PROF_MAX) setProfundidad(profundidad + 10);
    }

    public Torpedo lanzarTorpedo() {
        return new Torpedo(posicionX, profundidad, VELOCIDAD_TORPEDO);
    }

    public SubmarinoView toView() {
        return new SubmarinoView((int) posicionX, (int) profundidad, ANCHO, ALTO);
    }

    // Getters y Setters
    public double getPosicionX() { return posicionX; }
    public double getProfundidad() { return profundidad; }

    private void setPosicionX(double posicionX) { this.posicionX = posicionX; }
    private void setProfundidad(double profundidad) { this.profundidad = profundidad; }
}