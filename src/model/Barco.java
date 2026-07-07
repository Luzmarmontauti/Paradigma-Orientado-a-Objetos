package model;

import view.BarcoView;
import java.util.Random;


public class Barco {

    //Atributos
    protected static final double PROF_SUPERFICIE = 0;
    protected static final int TIEMPO_ESPERA_MIN = 50;
    protected static final int TIEMPO_ESPERA_MAX = 200;
    private double posicionX;
    private String direccion;
    private double velocidad;
    private int anchoPantalla;
    private int cargasMinimas;
    private int cargasLanzadas;
    private int ticksEntreDisparos;
    private Random random = new Random();

    //Constructor
    public Barco() {
        this.posicionX = 0;
        this.velocidad = 0;
        this.cargasMinimas = 0;
        this.cargasLanzadas = 0;
        this.ticksEntreDisparos = random.nextInt(TIEMPO_ESPERA_MAX - TIEMPO_ESPERA_MIN + 1) + TIEMPO_ESPERA_MIN;
    }

  // Métodos
    public void inicializar(int anchoPantalla, String direccion, double velocidad, int cargasMinimas) {
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

   
    public CargaDeProfundidad lanzarCarga(double velocidadCaida) {
        CargaDeProfundidad bomba = new CargaDeProfundidad(posicionX, velocidadCaida);
        cargasLanzadas++;
        return bomba;
    }

 
    public boolean puedeDisparar() {
        return ticksEntreDisparos == 0;
    }

  
    public void contarTicks() {
        if (ticksEntreDisparos > 0) {
            ticksEntreDisparos -= 1;
        } else {
            ticksEntreDisparos = random.nextInt(TIEMPO_ESPERA_MAX - TIEMPO_ESPERA_MIN + 1) + TIEMPO_ESPERA_MIN;
        }
    }

    public BarcoView toView() {
        return new BarcoView((int) posicionX, (int) PROF_SUPERFICIE + 20, 50, 20);
    }


    //Getters y Setters
    public double getPosicionX() { return posicionX; }
    public String getDireccion() { return direccion; }
    public int getCargasLanzadas() { return cargasLanzadas; }
    public int getCargasMinimas() { return cargasMinimas; }

    public void setDireccion(String direccion) { this.direccion = direccion; }
    private void setPosicionX(double posicionX) { this.posicionX = posicionX; }
}
