package model;

/**
 * Carga de profundidad lanzada por un barco. Cae verticalmente
 * y detona al alcanzar su profundidad de detonación.
 */
public class CargaDeProfundidad {

    // Atributos

    protected static final double PROF_DET_MIN = 300;
    protected static final double PROF_DET_MAX = 700;
    private double posicionY;
    private double profundidadActual;
    private double profundidadDetonacion;
    private double velocidadCaida;

    // Constructor

    public CargaDeProfundidad() {
        this.posicionY = 0;
        this.profundidadActual = 0;
        this.profundidadDetonacion = 0;
        this.velocidadCaida = 0;
    }

    // Comportamiento

    public void inicializar() {
    }

    public void caer() {
    }

    /**
     * Indica si la carga debe detonar en su posición actual.
     * @return true si debe detonar
     */
    public boolean debeDetonar() {
        return false;
    }

    /**
     * Calcula la distancia entre esta carga y el submarino al momento de la explosión.
     * @param sub el submarino
     * @return distancia en unidades de juego
     */
    public double calcularDistancia(Submarino sub) {
        return 0;
    }

    // Getters y Setters

    public double getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(double posicionY) {
        this.posicionY = posicionY;
    }

    public double getProfundidadActual() {
        return profundidadActual;
    }

    public void setProfundidadActual(double profundidadActual) {
        this.profundidadActual = profundidadActual;
    }

    public double getProfundidadDetonacion() {
        return profundidadDetonacion;
    }

    public void setProfundidadDetonacion(double profundidadDetonacion) {
        this.profundidadDetonacion = profundidadDetonacion;
    }

    public double getVelocidadCaida() {
        return velocidadCaida;
    }

    public void setVelocidadCaida(double velocidadCaida) {
        this.velocidadCaida = velocidadCaida;
    }
}
