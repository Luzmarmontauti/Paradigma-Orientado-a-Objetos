package model;

import java.util.Random;

/**
 * Barco de superficie que se mueve horizontalmente y lanza
 * cargas de profundidad hacia el submarino.
 */
public class Barco {

    // =========================================================
    // CONSTANTES
    // =========================================================

    protected static final double PROF_SUPERFICIE = 0;
    protected static final int TIEMPO_ESPERA_MIN = 10;
    protected static final int TIEMPO_ESPERA_MAX = 100;

    // =========================================================
    // ATRIBUTOS
    // =========================================================

    private double posicionX;
    private String direccion;
    private double velocidad;
    private double anchoPantalla;
    private int cargasMinimas;
    private int cargasLanzadas;
    private int ticksEntreDisparos;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public Barco() {
        this.posicionX = 0;
        this.velocidad = 0;
        this.cargasMinimas = 0;
        this.cargasLanzadas = 0;
    }

    // =========================================================
    // INICIALIZACIÓN
    // =========================================================

    /**
     * Configura el barco para su recorrido: asigna velocidad, dirección y cargas mínimas,
     * y lo posiciona en el borde de entrada según la dirección recibida.
     *
     * @param anchoPantalla ancho del área de juego
     * @param direccion     "derecha" entra por la izquierda, "izquierda" entra por la derecha
     * @param velocidad     velocidad de desplazamiento horizontal
     * @param cargasMinimas mínimo de cargas que debe lanzar antes de poder retirarse
     */
    public void inicializar(double anchoPantalla, String direccion, double velocidad, int cargasMinimas) {
        this.anchoPantalla = anchoPantalla;
        this.velocidad = velocidad;
        this.direccion = direccion;
        this.cargasMinimas = cargasMinimas;
        this.cargasLanzadas = 0;
        if (direccion.equalsIgnoreCase("derecha")) {
            setPosicionX(1);
        } else {
            setPosicionX(anchoPantalla - 1);
        }
    }

    // =========================================================
    // MOVIMIENTO
    // =========================================================

    /** Desplaza el barco hacia la izquierda según su velocidad. */
    public void moverIzquierda() {
        setPosicionX(getPosicionX() - velocidad);
    }

    /** Desplaza el barco hacia la derecha según su velocidad. */
    public void moverDerecha() {
        setPosicionX(getPosicionX() + velocidad);
    }

    /** @return true si el barco llegó al extremo horizontal de la pantalla. */
    public boolean haAlcanzadoExtremo() {
        return posicionX <= 0 || posicionX >= anchoPantalla;
    }

    // =========================================================
    // DISPARO
    // =========================================================

    /**
     * Crea y devuelve una nueva carga lanzada desde la posición actual del barco.
     * Incrementa el contador de cargas lanzadas.
     *
     * @param velocidadCaida        unidades que desciende la carga por tick
     * @param profundidadDetonacion profundidad a la que explotará la carga
     * @return la carga de profundidad creada
     */
    public CargaDeProfundidad lanzarCarga(double velocidadCaida, double profundidadDetonacion) {
        CargaDeProfundidad bomba = new CargaDeProfundidad();
        bomba.inicializar(posicionX, velocidadCaida, profundidadDetonacion);
        cargasLanzadas++;
        return bomba;
    }

    /** @return true si el contador de espera entre disparos llegó a 0. */
    public boolean puedeDisparar() {
        return ticksEntreDisparos == 0;
    }

    /**
     * Avanza el contador de espera entre disparos. Si llega a 0, sortea un nuevo
     * intervalo aleatorio entre TIEMPO_ESPERA_MIN y TIEMPO_ESPERA_MAX.
     *
     * @param random fuente de aleatoriedad del juego
     */
    public void contarTicks(Random random) {
        if (ticksEntreDisparos > 0) {
            ticksEntreDisparos -= 1;
        } else {
            ticksEntreDisparos = random.nextInt(TIEMPO_ESPERA_MAX - TIEMPO_ESPERA_MIN + 1) + TIEMPO_ESPERA_MIN;
        }
    }

    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================

    public double getPosicionX() { return posicionX; }
    public String getDireccion() { return direccion; }
    public double getVelocidad() { return velocidad; }
    public double getAnchoPantalla() { return anchoPantalla; }
    public int getCargasLanzadas() { return cargasLanzadas; }
    public int getCargasMinimas() { return cargasMinimas; }

    public void setDireccion(String direccion) { this.direccion = direccion; }

    private void setPosicionX(double posicionX) { this.posicionX = posicionX; }
}
