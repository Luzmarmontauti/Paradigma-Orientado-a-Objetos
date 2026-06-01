package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase central del modelo. Coordina todos los elementos del juego:
 * submarino, barcos, cargas, puntaje, vidas y nivel.
 */
public class Juego {

    // Atributos

    protected static final int TOTAL_BARCOS = 12;
    protected static final int MAX_SIMULTANEOS = 3;
    protected static final double INC_VEL = 0.2;
    private int vidas;
    private int puntaje;
    private int nivel;
    private int porcentajeVida;
    private String estado; 
    private int puntosAcumExtra;
    private int barcosGenerados;
    private List<Barco> barcosActivos;
    private List<CargaDeProfundidad> cargasActivas;
    private Submarino submarino;
    protected boolean estasVivo = true;

    // Constructor

    public Juego() {
        // TODO: definir cuántas vidas tiene el jugador al inicio
        this.vidas = 3;
        this.puntaje = 0;
        this.nivel = 1;
        this.porcentajeVida = 100;
        // TODO: definir los posibles estados del juego (MENU_PRINCIPAL, JUGANDO, GAME_OVER, etc.)
        this.estado = "MENU_PRINCIPAL";
        this.puntosAcumExtra = 0;
        this.barcosGenerados = 0;
        this.barcosActivos = new ArrayList<Barco>();
        this.cargasActivas = new ArrayList<CargaDeProfundidad>();
    }

    // Comportamiento

    public void iniciarPartida() {
    }

    /**
     * Procesa la explosión de una carga: calcula distancia al submarino
     * y aplica daño o puntaje según corresponda.
     * @param carga la carga que detonó
     */
    public void procesarExplosion(CargaDeProfundidad carga) {
    }

    /**
     * Verifica si se completaron todos los barcos del nivel actual.
     * @return true si el nivel terminó
     */
    public boolean verificarFinNivel() {
        return false;
    }

    public boolean pasarSiguienteNivel() {
    	return true;	
    }

    public void terminarPartida() {
    }

    /**
     * Agrega puntos al puntaje del jugador.
     * @param puntos cantidad de puntos a agregar
     */
    public void agregarPuntos(int puntos) {
    }

    /**
     * Aplica daño al submarino, reduciendo su porcentaje de vida.
     * @param porcentaje porcentaje de vida a restar
     */
    public void recibirDanio(int porcentaje) {
    }

    /**
     * Indica si el submarino sigue con vida.
     * @return true si el porcentaje de vida es mayor a cero
     */
    public boolean estaVivo() {
        return false;
    }

    public void generarBarco() {
    }

    public void verificarColisiones() {
    }

    public void actualizar() {
    }

    public void generarSubmarino() {
    }

    public void moverSubmarino() {
    }

    public void agregarVida() {
    }

    public String getEstado() {
        return null;
    }

    public double getSubmarinoX() {
        return 0;
    }

    public double getSubmarinoY() {
        return 0;
    }

    public double getVelocidadBarcos() {
        return 0;
    }

    public double getVelocidadCargas() {
        return 0;
    }

    // Getters y Setters

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas) {
        this.vidas = vidas;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPorcentajeVida() {
        return porcentajeVida;
    }

    public void setPorcentajeVida(int porcentajeVida) {
        this.porcentajeVida = porcentajeVida;
    }

    public int getBarcosGenerados() {
        return barcosGenerados;
    }

    public void setBarcosGenerados(int barcosGenerados) {
        this.barcosGenerados = barcosGenerados;
    }

    public int getPuntosAcumExtra() {
        return puntosAcumExtra;
    }

    public List<Barco> getBarcosActivos() {
        return barcosActivos;
    }

    public List<CargaDeProfundidad> getCargasActivas() {
        return cargasActivas;
    }
}
