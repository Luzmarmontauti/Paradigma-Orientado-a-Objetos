package model;

/**
 * Carga de profundidad lanzada por un barco. Cae verticalmente
 * y detona al alcanzar su profundidad de detonación.
 */
public class CargaDeProfundidad {

    // Atributos

    protected static final double PROF_DET_MIN = 300;
    protected static final double PROF_DET_MAX = 700;
    private double posicionX;
    private double profundidad;
    private double profundidadDetonacion;
    private double velocidadCaida;

    // Constructor

    public CargaDeProfundidad() {
        this.posicionX = 0;
        this.profundidad = 0;
        this.profundidadDetonacion = 0;
        this.velocidadCaida = 0;
    }

    // Comportamiento

    /**
     * Configura la carga para su recorrido: fija su posición horizontal (heredada del barco
     * que la lanzó), la velocidad de caída y la profundidad a la que detonará.
     * La profundidad inicial siempre es 0 (superficie).
     * @param posicionX             posición X del barco al momento del lanzamiento
     * @param velocidadCaida        unidades que desciende por tick
     * @param profundidadDetonacion profundidad aleatoria de detonación, entre PROF_DET_MIN y PROF_DET_MAX
     */
    public void inicializar(double posicionX, double velocidadCaida, double profundidadDetonacion) {
        this.posicionX = posicionX;
        this.velocidadCaida = velocidadCaida;
        this.profundidadDetonacion = profundidadDetonacion;
        setProfundidad(0);
    }

    /**
     * Avanza la carga un paso hacia abajo según su velocidad de caída.
     * Se llama una vez por tick mientras la carga no haya detonado.
     */
    public void caer() {
        double y = getProfundidad();
        if (profundidad < profundidadDetonacion) {
            y = y + velocidadCaida;
            setProfundidad(y);
        }
    }
    
    /**
     * Indica si la carga debe detonar en su posición actual.
     * @return true si debe detonar
     */
    public boolean debeDetonar() {
        double y = getProfundidad();
        return y >= profundidadDetonacion;
    }

    /**
     * Calcula la distancia entre esta carga y el submarino al momento de la explosión.
     * @param sub el submarino
     * @return distancia en unidades de juego
     */
    public double calcularDistancia(Submarino sub) {
        double difX = sub.getPosicionX() - getPosicionX();
        double difY = sub.getProfundidad() - getProfundidad();
        double distanciaEntreObjetos = Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
        return distanciaEntreObjetos;
    }


    // Getters

    public double getPosicionX() {
        return posicionX;
    }

    public double getProfundidad() {
        return profundidad;
    }

    public double getProfundidadDetonacion() {
        return profundidadDetonacion;
    }

    public double getVelocidadCaida() {
        return velocidadCaida;
    }

    // Setters privados — solo para uso interno

    private void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    private void setProfundidad(double profundidad) {
        this.profundidad = profundidad;
    }
}