package model;
import view.Vista;

/**
 * Submarino controlado por el jugador. Se mueve en las cuatro direcciones
 * dentro de los límites de profundidad y bordes laterales del área de juego.
 */
public class Submarino {

    // =========================================================
    // CONSTANTES
    // =========================================================

    protected static final double PROF_MIN = 300;
    protected static final double PROF_MAX = 800;

    // =========================================================
    // ATRIBUTOS
    // =========================================================

    private double posicionX;
    private double profundidad;
    private double anchoPantalla;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public Submarino() {
        this.posicionX = 0;
        this.profundidad = 0;
    }

    // =========================================================
    // INICIALIZACIÓN
    // =========================================================

    /**
     * Posiciona el submarino en su estado inicial: centrado horizontalmente
     * y en la profundidad media entre PROF_MIN y PROF_MAX.
     *
     * @param anchoPantalla ancho del área de juego
     */
    public void inicializar(double anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
        setPosicionX(anchoPantalla / 2);
        setProfundidad((PROF_MAX + PROF_MIN) / 2);
    }

    // =========================================================
    // MOVIMIENTO
    // =========================================================

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

    // =========================================================
    // GETTERS
    // =========================================================

    public double getPosicionX()   { return posicionX; }
    public double getProfundidad() { return profundidad; }

    // =========================================================
    // SETTERS PRIVADOS
    // =========================================================


   

    private void setPosicionX(double posicionX)     { 
    	this.posicionX = posicionX; 
    }
    private void setProfundidad(double profundidad) { 
    	this.profundidad = profundidad; 
    	}
    
    public view.Vista toView() {
    	int posX = (int) this.getPosicionX();
    	int posY = (int) this.getProfundidad();
    	int ancho = 50;
    	int alto = 20;
    	
    	return new view.Vista(posX, posY, ancho, alto);
 
    }

}
