package model;

/**
 * Carga de profundidad lanzada por un barco. Cae verticalmente
 * y detona al alcanzar su profundidad de detonación.
 */
public class CargaDeProfundidad {

    // =========================================================
    // CONSTANTES
    // =========================================================

    protected static final double PROF_DET_MIN = 300;
    protected static final double PROF_DET_MAX = 700;

    // =========================================================
    // ATRIBUTOS
    // =========================================================

    private double posicionX;
    private double profundidad;
    private double profundidadDetonacion;
    private double velocidadCaida;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public CargaDeProfundidad() {
        this.posicionX = 0;
        this.profundidad = 0;
        this.profundidadDetonacion = 0;
        this.velocidadCaida = 0;
    }

    // =========================================================
    // INICIALIZACIÓN
    // =========================================================

    /**
     * Configura la carga para su recorrido. La profundidad inicial siempre es 0 (superficie).
     *
     * @param posicionX             posición X del barco al momento del lanzamiento
     * @param velocidadCaida        unidades que desciende por tick
     * @param profundidadDetonacion profundidad aleatoria de detonación entre PROF_DET_MIN y PROF_DET_MAX
     */
    public void inicializar(double posicionX, double velocidadCaida, double profundidadDetonacion) {
        this.posicionX = posicionX;
        this.velocidadCaida = velocidadCaida;
        this.profundidadDetonacion = profundidadDetonacion;
        this.profundidad = 0;
    }

    // =========================================================
    // COMPORTAMIENTO
    // =========================================================

    /** Avanza la carga un paso hacia abajo según su velocidad de caída. */
    public void caer() {
        if (profundidad < profundidadDetonacion) {
            profundidad += velocidadCaida;
        }
    }

    /** @return true si la carga alcanzó su profundidad de detonación. */
    public boolean debeDetonar() {
        return profundidad >= profundidadDetonacion;
    }

    /**
     * Calcula la distancia entre esta carga y el submarino al momento de la explosión.
     *
     * @param sub el submarino
     * @return distancia en unidades de juego (Pitágoras)
     */
    public double calcularDistancia(Submarino sub) {
        double difX = sub.getPosicionX() - posicionX;
        double difY = sub.getProfundidad() - profundidad;
        return Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
    }

    // =========================================================
    // GETTERS
    // =========================================================

    public double getPosicionX()             { return posicionX; }
    public double getProfundidad()           { return profundidad; }
    public double getProfundidadDetonacion() { return profundidadDetonacion; }
    public double getVelocidadCaida()        { return velocidadCaida; }
}
