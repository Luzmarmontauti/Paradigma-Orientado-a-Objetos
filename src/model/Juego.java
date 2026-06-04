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
 * Toda fuente de aleatoriedad (dirección de barcos, profundidad de detonación, cargas
 * mínimas) se maneja a través del objeto {@link Random} inyectado por constructor,
 * lo que permite tests determinísticos usando una semilla fija (new Random(42)).
 */
public class Juego {

    // Constantes
    protected static final int TOTAL_BARCOS = 12;
    protected static final int MAX_SIMULTANEOS = 3;
    protected static final int TIEMPO_MIN_ESPERA= 10;
    protected static final int TIEMPO_MAX_ESPERA = 100;
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
    private int ticksEntreBarcos;

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

    /**
     * Crea una nueva instancia del juego en estado inicial, lista para ser configurada.
     * El juego no comienza hasta que se llame a {@link #iniciarPartida(double)}.
     *
     * @param random fuente de aleatoriedad inyectada desde afuera. Usar {@code new Random()}
     *               para partidas reales y {@code new Random(semilla)} para tests determinísticos.
     */
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

    /**
     * Inicializa la partida: crea y posiciona el submarino, setea la vida al 100%
     * y cambia el estado a "JUGANDO".
     *
     * @param anchoPantalla ancho del área de juego en píxeles, recibido desde el GameController.
     *                      Se usa para centrar el submarino horizontalmente.
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

    // --- Game loop ---

    // TODO [Fase Final]: los 3 barcos se generan en 3 ticks consecutivos al inicio — agregar delay entre generaciones
    // TODO [Fase Final]: cada barco lanza una carga por tick — agregar frecuenciaDisparo y contador de ticks por barco
    /**
     * Avanza el estado del juego un tick. Es la unidad atómica del game loop.
     * En cada tick ocurre lo siguiente en orden:
     * 1. Si hay lugar (menos de 3 barcos activos) y quedan barcos por generar, genera uno nuevo.
     * 2. Mueve cada barco activo según su dirección.
     * 3. Cada barco lanza una carga de profundidad con profundidad de detonación aleatoria
     *    entre 300m y 700m (usando random.nextInt(401) + 300).
     * 4. Si un barco llegó al extremo: si ya lanzó sus cargas mínimas, se retira;
     *    si no, invierte su dirección y sigue.
     * 5. Elimina los barcos retirados de la lista activa.
     * 6. Hace caer cada carga activa. Si una carga debe detonar, procesa la explosión
     *    y la elimina de la lista.
     *
     * @param anchoPantalla ancho del área de juego, necesario para generar nuevos barcos.
     */
    public void actualizar(double anchoPantalla) {
        //TODO: revisar para que no se gener un barco en cada tick
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

            int profundidadDetonacion = random.nextInt(700 - 300 + 1) + 300;

            //LOGICA PARA GENERAR BOMBAS CADA X TIEMPO Y NO UNA DETRAS DE OTRA SUANDO LOS METODOS DE BARCO PUEDEDISPARAR Y TICKSENTREDISPARO

            if (barco.puedeDisparar()) {
                cargasActivas.add(barco.lanzarCarga(velocidadCargas, profundidadDetonacion));
            }
            barco.contarTicks(random);


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
        barcosAEliminar.clear();

        List<CargaDeProfundidad> cargasAEliminar = new ArrayList<>();

        for (int i = 0; i < cargasActivas.size(); i++) {
            CargaDeProfundidad carga = cargasActivas.get(i);
            if (carga.debeDetonar()) {
                procesarExplosion(carga);
                cargasAEliminar.add(carga);
            } else { carga.caer(); }
        }
        cargasActivas.removeAll(cargasAEliminar);
        cargasAEliminar.clear();
    }

    // --- Submarino ---

    /**
     * Mueve el submarino en la dirección indicada, respetando los límites de profundidad
     * y los bordes laterales definidos en {@link Submarino}.
     * Es llamado por el GameController cuando el jugador presiona una tecla.
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
     * @return posición horizontal actual del submarino en píxeles.
     */
    public double getSubmarinoX() {
        return submarino.getPosicionX();
    }

    /**
     * @return profundidad actual del submarino en metros (eje Y, crece hacia abajo).
     */
    public double getSubmarinoY() {
        return submarino.getProfundidad();
    }

    // --- Barcos ---

    /**
     * Crea un nuevo barco, lo inicializa con dirección aleatoria y cargas mínimas aleatorias,
     * y lo agrega a la lista de barcos activos.
     *
     * La dirección se decide con random.nextInt(2): 0 = derecha, 1 = izquierda.
     *
     * Las cargas mínimas se calculan con random.nextInt(MAX - MIN + 1) + MIN:
     * - random.nextInt(n) genera valores de 0 a n-1.
     * - El +1 asegura que el valor máximo esté incluido en el rango.
     * - Sumar MIN corre el rango al intervalo deseado [CARGAS_MIN_X_BARCO, CARGAS_MAX_X_BARCO].
     *
     * @param anchoPantalla ancho del área de juego, para posicionar el barco en el borde correcto.
     */
    public void generarBarco(double anchoPantalla) {
        Barco barco = new Barco();
        int cargasMinimas = random.nextInt(CARGAS_MAX_X_BARCO - CARGAS_MIN_X_BARCO + 1) + CARGAS_MIN_X_BARCO;
        String direccion;
        if (random.nextInt(2) == 0) { direccion = "derecha"; } else { direccion = "izquierda"; }
        barco.inicializar(anchoPantalla, direccion, velocidadBarcos, cargasMinimas);
        barcosActivos.add(barco);
        barcosGenerados++;
    }

    public boolean puedeGenerarBarco() {
        return ticksEntreBarcos == 0;
    }

    public void disminuirContadorTicksEntreBarcos() {
        if (ticksEntreBarcos > 0) { ticksEntreBarcos -= 1; }
    }

    public void setTicksEntreBarcos() {
        ticksEntreBarcos = random.nextInt(TIEMPO_MAX_ESPERA - TIEMPO_MIN_ESPERA + 1 ) + TIEMPO_MIN_ESPERA;
    }

    // --- Cargas y explosiones ---

    /**
     * Procesa la explosión de una carga al detonar. Calcula la distancia entre la carga
     * y el submarino usando Pitágoras (implementado en {@link CargaDeProfundidad#calcularDistancia})
     * y aplica daño o puntaje según los criterios de la consigna:
     *
     * - Distancia > 100m: +30 puntos, sin daño.
     * - Distancia entre 50m y 100m: +10 puntos, -30% de vida.
     * - Distancia entre 10m y 50m: 0 puntos, -50% de vida.
     * - Distancia menor a 10m: 0 puntos, se pierde una vida completa.
     *
     * @param carga la carga que detonó en este tick.
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
        } else {
            quitarVida();
        }
    }

    // --- Daño y vida ---

    /**
     * Indica si el jugador todavía tiene vidas restantes.
     *
     * @return true si el jugador tiene al menos una vida, false si el juego terminó.
     */
    public boolean estaVivo() {
        return vidas >= 1;
    }

    /**
     * Aplica daño al submarino reduciendo su porcentaje de vida.
     * Si el porcentaje llega a 0 o menos, se pierde una vida y la barra se resetea a 100%.
     *
     * @param porcentajeDanio porcentaje de vida a restar (ej: 30 o 50).
     */
    public void recibirDanio(int porcentajeDanio) {
        porcentajeVida -= porcentajeDanio;
        if (porcentajeVida <= 0) {
            quitarVida();
            porcentajeVida = 100;
        }
    }

    /**
     * Suma una vida extra al jugador.
     * Se llama automáticamente desde {@link #agregarPuntos} al acumular 500 puntos.
     */
    public void agregarVida() {
        vidas += 1;
    }

    /**
     * Resta una vida al jugador. Si las vidas llegan a 0, termina la partida.
     * Se llama cuando una carga explota a menos de 10m o cuando el porcentaje de vida llega a 0.
     */
    public void quitarVida() {
        vidas -= 1;
        if (vidas == 0) terminarPartida();
    }

    // --- Puntaje ---

    /**
     * Agrega puntos al puntaje total y al contador de puntos acumulados para vida extra.
     * Si el contador alcanza o supera los 500 puntos, se otorga una vida extra y
     * el excedente se mantiene en el contador (ej: 512 puntos → vida extra + 12 puntos restantes).
     *
     * @param puntos cantidad de puntos a agregar (ej: 30, 10, o 200 al pasar de nivel).
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
     * Verifica si el nivel actual fue completado.
     * El nivel se completa cuando se generaron todos los barcos de la serie (12),
     * no quedan barcos activos en pantalla, y no quedan cargas activas sin detonar.
     *
     * @return true si el nivel terminó, false si todavía hay actividad.
     */
    public boolean verificarFinNivel() {
        return barcosGenerados == TOTAL_BARCOS && barcosActivos.isEmpty() && cargasActivas.isEmpty();
    }

    /**
     * Avanza al siguiente nivel: incrementa el contador de nivel, aumenta en un 20%
     * la velocidad de los barcos y de las cargas, resetea el contador de barcos generados
     * y otorga 200 puntos por pasar de nivel.
     */
    public void pasarSiguienteNivel() {
        nivel += 1;
        velocidadBarcos += INCREMENTO_VELOCIDAD;
        velocidadCargas += INCREMENTO_VELOCIDAD;
        barcosGenerados = 0;
        agregarPuntos(200);
    }

    // --- Getters de estado ---

    public String getEstado() {
        return estado;
    }

    public double getVelocidadBarcos() {
        return velocidadBarcos;
    }

    public double getVelocidadCargas() {
        return velocidadCargas;
    }

    // --- Getters ---

    public int getVidas() {
        return vidas;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public int getNivel() {
        return nivel;
    }

    public int getPorcentajeVida() {
        return porcentajeVida;
    }

    public int getBarcosGenerados() {
        return barcosGenerados;
    }

    public int getPuntosExtraAcumulados() {
        return puntosExtraAcumulados;
    }

    public List<Barco> getBarcosActivos() {
        return barcosActivos;
    }

    public List<CargaDeProfundidad> getCargasActivas() {
        return cargasActivas;
    }
}
