package model;
import java.util.Random;
/**
 * Barco de superficie que se mueve horizontalmente y lanza
 * cargas de profundidad hacia el submarino.
 */
public class Barco {

    // Atributos

    protected static final double PROF_SUPERFICIE = 0;
    protected  static final int TIEMPO_ESPERA_MIN = 10;
    protected  static final int TIEMPO_ESPERA_MAX = 100;
    private double posicionX;
    private String direccion;
    private double velocidad;
    private double anchoPantalla;
    private int cargasMinimas;
    private int cargasLanzadas;
    private int ticksEntreDisparos;

    // Constructor

    public Barco() {
        this.posicionX = 0;
        this.velocidad = 0;
        this.cargasMinimas = 0;
        this.cargasLanzadas = 0;
    }

    // Comportamiento

    /**
     * Configura el barco para su recorrido: asigna velocidad, dirección y cargas mínimas,
     * y lo posiciona en el borde de entrada según la dirección recibida.
     * @param anchoPantalla  ancho del área de juego
     * @param direccion      "derecha" o "izquierda", decidido por el Juego aleatoriamente
     * @param velocidad      velocidad de desplazamiento horizontal
     * @param cargasMinimas  mínimo de cargas que debe lanzar antes de poder retirarse
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

    /**
     * Desplaza el barco hacia la izquierda según su velocidad.
     */
    public void moverIzquierda() {
        double x = getPosicionX();
        setPosicionX(x - velocidad);
    }

    /**
     * Desplaza el barco hacia la derecha según su velocidad.
     */
    public void moverDerecha() {
        double x = getPosicionX();
        setPosicionX(x + velocidad);
    }

    /**
     * Indica si el barco llegó al extremo horizontal de la pantalla.
     * @return true si alcanzó el borde
     */
    public boolean haAlcanzadoExtremo() {
        double x = getPosicionX();
        return x <= 0 || x >= anchoPantalla;
    }

    /**
     * Crea, inicializa y devuelve una nueva carga de profundidad lanzada desde este barco.
     * Incrementa el contador de cargas lanzadas.
     * @param velocidadCaida        unidades que desciende la carga por tick
     * @param profundidadDetonacion profundidad a la que explotará la carga, definida por el Juego
     * @return la carga de profundidad creada
     */
    public CargaDeProfundidad lanzarCarga(double velocidadCaida, double profundidadDetonacion) {
        CargaDeProfundidad bomba = new CargaDeProfundidad();
        bomba.inicializar(posicionX, velocidadCaida, profundidadDetonacion);
        cargasLanzadas ++;
        return bomba;
    }

    public boolean puedeDisparar() {
        return ticksEntreDisparos == 0;
    }

    public void contarTicks(Random random) {
        if (ticksEntreDisparos > 0) { ticksEntreDisparos -= 1; }

        ticksEntreDisparos = random.nextInt(TIEMPO_ESPERA_MAX - TIEMPO_ESPERA_MIN + 1) - TIEMPO_ESPERA_MIN;
    }

    // Getters y Setters

    public double getAnchoPantalla() {
        return anchoPantalla;
    }

    public int getCargasLanzadas() {
        return cargasLanzadas;
    }

    public int getCargasMinimas() {
        return cargasMinimas;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public double getPosicionX() {
        return posicionX;
    }

    private void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    public double getVelocidad() {
        return velocidad;
    }
}
