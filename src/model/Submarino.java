package model;

import view.Vista; // Único import necesario para el método toView

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
    
    // Agregamos las dimensiones como atributos del modelo
    private final int ANCHO = 50;
    private final int ALTO = 20;

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
    public void inicializar(double anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
        // Lo centramos teniendo en cuenta su propio ancho
        setPosicionX((anchoPantalla / 2) - (ANCHO / 2));
        setProfundidad((PROF_MAX + PROF_MIN) / 2);
    }

    // =========================================================
    // MOVIMIENTO (Corregido con límites reales)
    // =========================================================
    public void moverIzquierda() {
        if (posicionX > 0) {
            setPosicionX(posicionX - 5); // Aumenté a 5 para que no se mueva tan lento
        }
    }

    public void moverDerecha() {
        // No dejamos que pase del ancho de la pantalla menos su propio tamaño
        if (posicionX + ANCHO < anchoPantalla) {
            setPosicionX(posicionX + 5);
        }
    }

    public void moverArriba() {
        if (profundidad > PROF_MIN) {
            setProfundidad(profundidad - 5);
        }
    }

    public void moverAbajo() {
        // No dejamos que pase de la profundidad máxima menos su propio alto
        if (profundidad + ALTO < PROF_MAX) {
            setProfundidad(profundidad + 5);
        }
    }

    // =========================================================
    // GETTERS
    // =========================================================
    public double getPosicionX()   { return posicionX; }
    public double getProfundidad() { return profundidad; }
    public int getAncho()          { return ANCHO; }
    public int getAlto()           { return ALTO; }

    // =========================================================
    // SETTERS PRIVADOS
    // =========================================================
    private void setPosicionX(double posicionX) { 
        this.posicionX = posicionX; 
    }
    private void setProfundidad(double profundidad) { 
        this.profundidad = profundidad; 
    }
    
    // =========================================================
    // CONEXIÓN CON LA VISTA
    // =========================================================
    public Vista toView() {
        int posX = (int) this.getPosicionX();
        int posY = (int) this.getProfundidad();
        
        // Ahora usamos las constantes de la clase
        return new Vista(posX, posY, this.ANCHO, this.ALTO);
    }
}