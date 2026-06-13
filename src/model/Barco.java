package model;

import java.util.Random;

public class Barco {
    protected static final double PROF_SUPERFICIE = 0; 
    protected static final int TIEMPO_ESPERA_MIN = 10;
    protected static final int TIEMPO_ESPERA_MAX = 100;
    private final int ANCHO = 50;
    private final int ALTO = 20;

    private double posicionX;
    private String direccion;
    private double velocidad;
    private double anchoPantalla;
    private int cargasMinimas;
    private int cargasLanzadas;
    private int ticksEntreDisparos;

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

    public void avanzar() {
        if (direccion.equalsIgnoreCase("derecha")) {
            setPosicionX(posicionX + velocidad);
        } else {
            setPosicionX(posicionX - velocidad);
        }
    }

    public boolean haCompletadoRecorrido() {
        if (direccion.equalsIgnoreCase("derecha")) {
            return posicionX > anchoPantalla;
        } else {
            return posicionX < -ANCHO;
        }
    }

    public CargaDeProfundidad lanzarCarga(double velocidadCaida, double profundidadDetonacion) {
        CargaDeProfundidad bomba = new CargaDeProfundidad();
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

    public double getPosicionX()     { return posicionX; }
    public double getProfundidad()   { return PROF_SUPERFICIE; } 
    public String getDireccion()     { return direccion; }
    public double getVelocidad()     { return velocidad; }
    public int getAncho()            { return ANCHO; }
    public int getAlto()             { return ALTO; }
    public int getCargasLanzadas()   { return cargasLanzadas; }
    public int getCargasMinimas()    { return cargasMinimas; }

    public void setDireccion(String direccion) { this.direccion = direccion; }
    private void setPosicionX(double posicionX) { this.posicionX = posicionX; }

    // TRADUCTOR PARA LA VISTA
    public view.Vista toView() {
        int posX = (int) this.getPosicionX();
        int posY = (int) this.getProfundidad(); 
        return new view.Vista(posX, posY, this.ANCHO, this.ALTO);
    }
}