package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Clase central del modelo. Actúa como orquestador de todos los elementos del juego:
 * el submarino del jugador, los barcos enemigos, las cargas de profundidad, el puntaje,
 * las vidas y el nivel actual.
 *
 * Implementa el game loop a través del método {@link #actualizar(double)}, que avanza
 * el estado del juego un tick cada vez que es invocado. El GameController es responsable
 * de llamar a este método en cada ciclo del juego.
 *
 * Toda fuente de aleatoriedad se maneja a través del objeto {@link Random} inyectado
 * por constructor, lo que permite tests determinísticos usando una semilla fija.
 */
public class Juego {

    //Atributos
    protected static final int TOTAL_BARCOS = 12;
    protected static final int MAX_SIMULTANEOS = 3;
    protected static final int TIEMPO_MIN_ESPERA = 10;
    protected static final int TIEMPO_MAX_ESPERA = 100;
    protected static final double VELOCIDAD_INICIAL = 20;
    protected static final double INCREMENTO_VELOCIDAD = 0.2;
    protected static final int CARGAS_MIN_X_BARCO = 2;
    protected static final int CARGAS_MAX_X_BARCO = 5;

    // Estado general
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
    private int ticksEntreBarcos;
    private List<Barco> barcosActivos;
    private double velocidadBarcos;

    // Cargas
    private List<CargaDeProfundidad> cargasActivas;
    private double velocidadCargas;
    private double ultimaExplosionX;
    private double ultimaExplosionY;

    // Aleatoriedad
    private Random random = new Random();


    //Constructor
    /**
     * Crea una nueva instancia del juego en estado inicial, lista para ser configurada.
     * El juego no comienza hasta que se llame a {@link #iniciarPartida(double)}.
     */
    public Juego() {
        this.estado = "MENU_PRINCIPAL";
        this.nivel = 1;
        this.vidas = 3;
        this.puntaje = 0;
        this.puntosExtraAcumulados = 0;
        this.barcosGenerados = 0;
        this.barcosActivos = new ArrayList<Barco>();
        this.velocidadBarcos = VELOCIDAD_INICIAL;
        this.cargasActivas = new ArrayList<CargaDeProfundidad>();
        this.velocidadCargas = VELOCIDAD_INICIAL;
    }


    //Metodos
    /**
     * Inicializa la partida: crea y posiciona el submarino, setea la vida al 100%
     * y cambia el estado a "JUGANDO".
     *
     * @param anchoPantalla ancho del área de juego en píxeles. Se usa para centrar el submarino.
     */
    public void iniciarPartida(double anchoPantalla) {
        this.submarino = new Submarino();
        submarino.inicializar(anchoPantalla);
        porcentajeVida = 100;
        this.estado = "JUGANDO";
        setTicksEntreBarcos();
    }

    /**
     * Finaliza la partida cambiando el estado a "GAME OVER".
     * Es llamado automáticamente cuando el jugador pierde su última vida.
     */
    public void terminarPartida() {
        estado = "GAME OVER";
    }


    /**
     * Avanza el estado del juego un tick. En cada tick ocurre en orden:
     * 1. Si el contador de espera llegó a 0 y hay lugar, genera un nuevo barco.
     * 2. Mueve cada barco activo y lanza una carga si su frecuencia de disparo lo permite.
     * 3. Si un barco llegó al extremo: si ya lanzó sus cargas mínimas se retira, si no invierte dirección.
     * 4. Hace caer cada carga activa. Si una carga debe detonar, procesa la explosión.
     *
     * @param anchoPantalla ancho del área de juego, necesario para generar nuevos barcos.
     */
    public void actualizar(double anchoPantalla) {
        if (barcosGenerados < TOTAL_BARCOS && barcosActivos.size() < MAX_SIMULTANEOS && puedeGenerarBarco()) {
            generarBarco(anchoPantalla);
            setTicksEntreBarcos();
        }
        disminuirContadorTicksEntreBarcos();

        List<Barco> barcosAEliminar = new ArrayList<>();

        for (int i = 0; i < barcosActivos.size(); i++) {
            Barco barco = barcosActivos.get(i);
            String direccion = barco.getDireccion();
            if (direccion.equals("izquierda")) { barco.moverIzquierda(); } else { barco.moverDerecha(); }

            if (barco.puedeDisparar()) {
                cargasActivas.add(barco.lanzarCarga(velocidadCargas));
            }
            barco.contarTicks();

            if (barco.haAlcanzadoExtremo()) {
                if (barco.getCargasLanzadas() >= barco.getCargasMinimas()) {
                    barcosAEliminar.add(barco);
                } else if (barco.getDireccion().equals("izquierda")) {
                    barco.setDireccion("derecha");
                } else {
                    barco.setDireccion("izquierda");
                }
            }
        }
        barcosActivos.removeAll(barcosAEliminar);

        List<CargaDeProfundidad> cargasAEliminar = new ArrayList<>();

        for (int i = 0; i < cargasActivas.size(); i++) {
            CargaDeProfundidad carga = cargasActivas.get(i);
            if (carga.debeDetonar()) {
                procesarExplosion(carga);
                cargasAEliminar.add(carga);
            } else { carga.caer(); }
        }
        cargasActivas.removeAll(cargasAEliminar);
    }

    /**
     * Mueve el submarino en la dirección indicada, respetando los límites del área de juego.
     *
     * @param direccion "arriba", "abajo", "izquierda" o "derecha".
     */
    public void moverSubmarino(String direccion) {
        if (direccion.equals("arriba")) { submarino.moverArriba(); }
        else if (direccion.equals("abajo")) { submarino.moverAbajo(); }
        else if (direccion.equals("izquierda")) { submarino.moverIzquierda(); }
        else if (direccion.equals("derecha")) { submarino.moverDerecha(); }
    }

    /**
     * Crea un nuevo barco con dirección y cargas mínimas aleatorias y lo agrega a la lista activa.
     *
     * @param anchoPantalla ancho del área de juego, para posicionar el barco en el borde de entrada.
     */
    public void generarBarco(double anchoPantalla) {
        Barco barco = new Barco();
        int cargasMinimas = random.nextInt(CARGAS_MAX_X_BARCO - CARGAS_MIN_X_BARCO + 1) + CARGAS_MIN_X_BARCO;
        String direccion = (random.nextInt(2) == 0) ? "derecha" : "izquierda";
        barco.inicializar(anchoPantalla, direccion, velocidadBarcos, cargasMinimas);
        barcosActivos.add(barco);
        barcosGenerados++;
    }

    /** @return true si el contador de espera entre barcos llegó a 0. */
    public boolean puedeGenerarBarco() {
        return ticksEntreBarcos == 0;
    }

    /** Descuenta el contador de espera entre barcos, sin bajar de 0. */
    public void disminuirContadorTicksEntreBarcos() {
        if (ticksEntreBarcos > 0) ticksEntreBarcos -= 1;
    }

    /** Resetea el contador de espera entre barcos con un valor aleatorio entre los límites definidos. */
    public void setTicksEntreBarcos() {
        ticksEntreBarcos = random.nextInt(TIEMPO_MAX_ESPERA - TIEMPO_MIN_ESPERA + 1) + TIEMPO_MIN_ESPERA;
    }

    /**
     * Procesa la explosión de una carga al detonar. Calcula la distancia al submarino
     * y aplica daño o puntaje:
     * - Distancia > 100m: +30 puntos.
     * - Distancia 50-100m: +10 puntos, -30% de vida.
     * - Distancia 10-50m: -50% de vida.
     * - Distancia menor a 10m: se pierde una vida completa.
     *
     * @param carga la carga que detonó en este tick.
     */
    public void procesarExplosion(CargaDeProfundidad carga) {
        this.ultimaExplosionX = carga.getPosicionX();
        this.ultimaExplosionY = carga.getProfundidad();
        double distancia = carga.calcularDistancia(submarino);
        if (distancia > 100) {
            agregarPuntos(30);
        } else if (distancia > 50) {
            agregarPuntos(10);
            recibirDanio(30);
        } else if (distancia > 10) {
            recibirDanio(50);
        } else {
            quitarVida();
        }
    }

    /** @return true si el jugador tiene al menos una vida. */
    public boolean estaVivo() {
        return vidas >= 1;
    }

    /**
     * Aplica daño al submarino. Si el porcentaje llega a 0, se pierde una vida y se resetea a 100%.
     *
     * @param porcentajeDanio porcentaje de vida a restar (30 o 50).
     */
    public void recibirDanio(int porcentajeDanio) {
        porcentajeVida -= porcentajeDanio;
        if (porcentajeVida <= 0) {
            quitarVida();
            porcentajeVida = 100;
        }
    }

    /** Suma una vida extra. Se llama automáticamente al acumular 500 puntos. */
    public void agregarVida() {
        vidas += 1;
    }

    /** Resta una vida. Si las vidas llegan a 0, termina la partida. */
    public void quitarVida() {
        vidas -= 1;
        if (vidas == 0) terminarPartida();
    }

    /**
     * Agrega puntos al puntaje total. Cada 500 puntos acumulados otorga una vida extra,
     * y el excedente se mantiene en el contador.
     *
     * @param puntos cantidad de puntos a agregar.
     */
    public void agregarPuntos(int puntos) {
        puntaje += puntos;
        puntosExtraAcumulados += puntos;
        if (puntosExtraAcumulados >= 500) {
            agregarVida();
            puntosExtraAcumulados -= 500;
        }
    }

    /**
     * @return true si el nivel se completó: todos los barcos generados, sin barcos ni cargas activas.
     */
    public boolean verificarFinNivel() {
        return barcosGenerados == TOTAL_BARCOS && barcosActivos.isEmpty() && cargasActivas.isEmpty();
    }

    /**
     * Avanza al siguiente nivel: incrementa el nivel, aumenta las velocidades en 20%,
     * resetea el contador de barcos y otorga 200 puntos.
     */
    public void pasarSiguienteNivel() {
        nivel += 1;
        velocidadBarcos += INCREMENTO_VELOCIDAD;
        velocidadCargas += INCREMENTO_VELOCIDAD;
        barcosGenerados = 0;
        agregarPuntos(200);
    }

    //Getters
    public String getEstado() { return estado; }
    public int getNivel() { return nivel; }
    public int getVidas() { return vidas; }
    public int getPuntaje() { return puntaje; }
    public int getPorcentajeVida() { return porcentajeVida; }
    public int getPuntosExtraAcumulados() { return puntosExtraAcumulados; }
    public int getBarcosGenerados() { return barcosGenerados; }
    public double getVelocidadBarcos() { return velocidadBarcos; }
    public double getVelocidadCargas() { return velocidadCargas; }
    public double getSubmarinoX() { return submarino.getPosicionX(); }
    public double getSubmarinoY() { return submarino.getProfundidad(); }
    public double getUltimaExplosionX() { return ultimaExplosionX; }
    public double getUltimaExplosionY() { return ultimaExplosionY; }
    public List<Barco> getBarcosActivos() { return barcosActivos; }
    public List<CargaDeProfundidad> getCargasActivas() { return cargasActivas; }
}
