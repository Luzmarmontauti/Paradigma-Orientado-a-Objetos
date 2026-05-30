package model;

/**
 * Barco de superficie que se mueve horizontalmente y lanza
 * cargas de profundidad hacia el submarino.
 */
public class Barco {

    // Atributos

    protected static final double PROF_SUPERFICIE = 0;
    private int posicionX;
    private String direccion;
    private double velocidad;

    // Constructor

    public Barco() {
        this.posicionX = 0;
        // TODO: definir si la dirección inicial debe venir como parámetro o siempre arranca igual
        this.direccion = "Izquierda";
        this.velocidad = 0;
    }

    // Comportamiento

    public void inicializar() {
    }
    //probando una rama nueva

    public void moverIzquierda() {
    }

    public void moverDerecha() {
    }

    /**
     * Crea y devuelve una nueva carga de profundidad lanzada desde este barco.
     * @param velCaida velocidad a la que cae la carga
     * @return la carga de profundidad creada
     */
    public CargaDeProfundidad lanzarCarga(double velCaida) {
        return null;
    }

    /**
     * Indica si el barco llegó al extremo horizontal de la pantalla.
     * @return true si alcanzó el borde
     */
    public boolean haAlcanzadoExtremo() {
        return false;
    }

    // Getters y Setters

    public int getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(int posicionX) {
        this.posicionX = posicionX;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }
}
