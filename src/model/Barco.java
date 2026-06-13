package model;

import java.util.Random;


public class Barco {
    protected static final double PROF_SUPERFICIE = 0; // RF-07
    protected static final int TIEMPO_ESPERA_MIN = 10;
    protected static final int TIEMPO_ESPERA_MAX = 100;
    private final int ANCHO = 50;
    private final int ALTO = 20;

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
        this.ticksEntreDisparos = 0;
    }


    public void inicializar(double anchoPantalla, String direccion, double velocidad, int cargasMinimas) {
        this.anchoPantalla = anchoPantalla;
        this.velocidad = velocidad;
        this.direccion = direccion;
        this.cargasMinimas = cargasMinimas;
        this.cargasLanzadas = 0;
        this.ticksEntreDisparos = TIEMPO_ESPERA_MIN;

        if (direccion.equalsIgnoreCase("derecha")) {
            setPosicionX(-ANCHO);
        } else {
            setPosicionX(anchoPantalla); 
        }
    }

    // =========================================================
    // MOVIMIENTO
    // =========================================================
    
    /**
     * Mueve el barco en la dirección correspondiente según su velocidad.
     * Reemplaza los métodos individuales para que el Controlador solo use "actualizar"
     */
    public void avanzar() {
        if (direccion.equalsIgnoreCase("derecha")) {
            setPosicionX(posicionX + velocidad);
        } else {
            setPosicionX(posicionX - velocidad);
        }
    }

    /** * @return true si el barco ya abandonó completamente el área visible de la pantalla.
     */
    public boolean haCompletadoRecorrido() {
        if (direccion.equalsIgnoreCase("derecha")) {
            return posicionX > anchoPantalla;
        } else {
            return posicionX < -ANCHO;
        }
    }

    // =========================================================
    // DISPARO (RF-12, RF-13)
    // =========================================================
    
    /**
     * El barco genera una carga desde su propia posición horizontal (RF-13).
     */
    public CargaDeProfundidad lanzarCarga(double velocidadCaida, double profundidadDetonacion) {
        CargaDeProfundidad bomba = new CargaDeProfundidad();
        
        // Tomamos el centro del barco para que la bomba caiga desde el medio del casco
        double centroBarcoX = this.posicionX + (ANCHO / 2.0);
        
        bomba.inicializar(centroBarcoX, velocidadCaida, profundidadDetonacion);
        cargasLanzadas++;
        return bomba;
    }

    public boolean puedeDisparar() {
        return ticksEntreDisparos == 0;
    }

    public void contarTicks(Random random) {
        if (ticksEntreDisparos > 0) {
            ticksEntreDisparos -= 1;
        } else {
            ticksEntreDisparos = random.nextInt(TIEMPO_ESPERA_MAX - TIEMPO_ESPERA_MIN + 1) + TIEMPO_ESPERA_MIN;
        }
    }

    
    public boolean cumplioCargasMinimas() {
        return cargasLanzadas >= cargasMinimas;
    }

    // =========================================================
    // GETTERS Y SETTERS
    // =========================================================
    public double getPosicionX()     { return posicionX; }
    public double getProfundidad()   { return PROF_SUPERFICIE; } 
    public String getDireccion()     { return direccion; }
    public double getVelocidad()     { return velocidad; }
    public int getAncho()            { return ANCHO; }
    public int getAlto()             { return ALTO; }
    public int getCargasLanzadas()   { return cargasLanzadas; }
    public int getCargasMinimas()    { return cargasMinimas; }

    private void setPosicionX(double posicionX) { 
        this.posicionX = posicionX; 
    }
}