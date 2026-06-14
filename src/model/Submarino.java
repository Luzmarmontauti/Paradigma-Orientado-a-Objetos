package model;

/**
 * Submarino controlado por el jugador. Se mueve en las cuatro direcciones
 * dentro de los límites de profundidad y bordes laterales del área de juego.
 */
public class Submarino {

    //Atributos
    protected static final double PROF_MIN = 300;
    protected static final double PROF_MAX = 800;
    private double posicionX;
    private double profundidad;
    private int anchoPantalla;

    //Constructor
    public Submarino() {
        this.posicionX = 0;
        this.profundidad = 0;
    }

    //Metodos
    /**
     * Posiciona el submarino en su estado inicial: centrado horizontalmente
     * y en la profundidad media entre PROF_MIN y PROF_MAX.
     *
     * @param anchoPantalla ancho del área de juego
     */
    public void inicializar(int anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
        setPosicionX(anchoPantalla / 2);
        setProfundidad((PROF_MAX + PROF_MIN) / 2);
    }

    /** Mueve el submarino hacia la izquierda si no alcanzó el borde. */
    public void moverIzquierda() {
        if (posicionX > 0) setPosicionX(posicionX - 1);
    }

    /** Mueve el submarino hacia la derecha si no alcanzó el borde. */
    public void moverDerecha() {
        if (posicionX < anchoPantalla) setPosicionX(posicionX + 1);
    }

    /** Sube el submarino (disminuye profundidad) si no alcanzó PROF_MIN. */
    public void moverArriba() {
        if (profundidad > PROF_MIN) setProfundidad(profundidad - 1);
    }

    /** Baja el submarino (aumenta profundidad) si no alcanzó PROF_MAX. */
    public void moverAbajo() {
        if (profundidad < PROF_MAX) setProfundidad(profundidad + 1);
    }

    //Getters y Setters
    public double getPosicionX() { return posicionX; }
    public double getProfundidad() { return profundidad; }

    private void setPosicionX(double posicionX) { this.posicionX = posicionX; }
    private void setProfundidad(double profundidad) { this.profundidad = profundidad; }
}
