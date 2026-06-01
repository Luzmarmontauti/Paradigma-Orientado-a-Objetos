package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase central del modelo. Coordina todos los elementos del juego:
 * submarino, barcos, cargas, puntaje, vidas y nivel.
 */
public class Juego {

    // Atributos

    // Constantes
    protected static final int TOTAL_BARCOS = 12;
    protected static final int MAX_SIMULTANEOS = 3;
    protected static final double VELOCIDAD_INICIAL = 1;
    protected static final double INCREMENTO_VELOCIDAD = 0.2;
    protected static final int CARGAS_MIN_X_BARCO = 2;
    protected static final int CARGAS_MAX_X_BARCO = 5;


    // Estado general del juego
    private String estado;
    private int nivel;
    private int vidas;
    private int puntaje;
    private int puntosExtraAcumulados;

    // Submarino
    private Submarino submarino;
    private int porcentajeVida;

    // Barcos
    private int barcosGenerados;
    private List<Barco> barcosActivos;
    private double velocidadBarcos;

    // Cargas
    private List<CargaDeProfundidad> cargasActivas;
    private double velocidadCargas;

    // Aleatoriedad
    private Random random;

    // Constructor

    public Juego(Random random) {
        // Estado general
        this.estado = "MENU_PRINCIPAL";
        this.nivel = 1;
        this.vidas = 3;
        this.puntaje = 0;
        this.puntosExtraAcumulados = 0;

        // Barcos
        this.barcosGenerados = 0;
        this.barcosActivos = new ArrayList<Barco>();
        this.velocidadBarcos = VELOCIDAD_INICIAL;

        // Cargas
        this.cargasActivas = new ArrayList<CargaDeProfundidad>();
        this.velocidadCargas = VELOCIDAD_INICIAL;

        // Aleatoriedad
        this.random = random;
    }

    // Comportamiento

    // --- Inicialización ---

    public void iniciarPartida(double anchoPantalla) {
        this.submarino = new Submarino();
        submarino.inicializar(anchoPantalla);
        porcentajeVida = 100;
        this.estado = "JUGANDO";
    }

    public void terminarPartida() {
        estado = "GAME OVER";
    }

    // --- Game loop ---

    public void actualizar() {
    }

    public void verificarColisiones() {
    }

    // --- Submarino ---

    public void moverSubmarino() {
    }

    public double getSubmarinoX() {
        return 0;
    }

    public double getSubmarinoY() {
        return 0;
    }

    // --- Barcos ---

    public void generarBarco(double anchoPantalla) {
        Barco barco = new Barco();
        int cargasMinimas = random.nextInt(CARGAS_MAX_X_BARCO - CARGAS_MIN_X_BARCO + 1) + CARGAS_MIN_X_BARCO;
        String direccion;
        if (random.nextInt(2) == 0) {direccion = "derecha";} else {direccion = "izquierda";}
        barco.inicializar(anchoPantalla, direccion, velocidadBarcos, cargasMinimas);
        barcosActivos.add(barco);
        barcosGenerados ++;
    }

    // --- Cargas y explosiones ---

    /**
     * Procesa la explosión de una carga: calcula distancia al submarino
     * y aplica daño o puntaje según corresponda.
     * @param carga la carga que detonó
     */
    public void procesarExplosion(CargaDeProfundidad carga) {
        double distancia = carga.calcularDistancia(submarino);
        if (distancia > 100) {
            agregarPuntos(30);
        } else if (distancia > 50) {
            agregarPuntos(10);
            recibirDanio(30);
        } else if (distancia > 10) {
            recibirDanio(50);
        } else quitarVida();
    }

    // --- Daño y vida ---

    /**
     * Indica si el submarino sigue con vida.
     * @return true si el porcentaje de vida es mayor a cero
     */
    public boolean estaVivo() {
        return vidas >= 1;
    }

    /**
     * Aplica daño al submarino, reduciendo su porcentaje de vida.
     * @param porcentajeDanio porcentaje de vida a restar
     */
    public void recibirDanio(int porcentajeDanio) {
        porcentajeVida -= porcentajeDanio;
        if (porcentajeVida <= 0) {
            quitarVida();
            porcentajeVida = 100;
        }
    }

    public void agregarVida() {
        vidas += 1;
    }

    public void quitarVida() {
        vidas -= 1;
        if (vidas == 0) terminarPartida();
    }

    // --- Puntaje ---

    /**
     * Agrega puntos al puntaje del jugador.
     *
     * @param puntos cantidad de puntos a agregar
     * @return
     */
    public void agregarPuntos(int puntos) {
        puntaje += puntos;
        puntosExtraAcumulados += puntos;
        if (puntosExtraAcumulados >= 500) {
            agregarVida();
            puntosExtraAcumulados -= 500;
        }
    }

    // --- Nivel ---

    /**
     * Verifica si se completaron todos los barcos del nivel actual.
     * @return true si el nivel terminó
     */
    public boolean verificarFinNivel() {
        return barcosGenerados == TOTAL_BARCOS && barcosActivos.isEmpty() && cargasActivas.isEmpty();
    }

    public void pasarSiguienteNivel() {
        nivel += 1;
        velocidadBarcos += INCREMENTO_VELOCIDAD;
        velocidadCargas += INCREMENTO_VELOCIDAD;
        barcosGenerados = 0;
        agregarPuntos(200);
    }

    // --- Estado ---

    public String getEstado() {
        return null;
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
        return puntosExtraAcumulados;
    }

    public List<Barco> getBarcosActivos() {
        return barcosActivos;
    }

    public List<CargaDeProfundidad> getCargasActivas() {
        return cargasActivas;
    }
}
