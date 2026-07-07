package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego {

    // Atributos
    protected static final int TOTAL_BARCOS = 12;
    protected static final int MAX_SIMULTANEOS = 3;
    protected static final int TIEMPO_MIN_ESPERA = 10;
    protected static final int TIEMPO_MAX_ESPERA = 100;
    protected static final double VELOCIDAD_INICIAL = 2;
    protected static final double INCREMENTO_VELOCIDAD = 1.2;
    protected static final int CARGAS_MIN_X_BARCO = 2;
    protected static final int CARGAS_MAX_X_BARCO = 5;

    // Estado general
    private boolean juegoTerminado;
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

    // Torpedos
    private List<Torpedo> torpedosActivos;

    // Aleatoriedad
    private Random random = new Random();

    // Area de Juego
    private Area area = new Area(800, 630);

    // Constructor
    public Juego() {
        this.juegoTerminado = false;
        this.nivel = 1;
        this.vidas = 3;
        this.puntaje = 0;
        this.puntosExtraAcumulados = 0;
        this.barcosGenerados = 0;
        this.barcosActivos = new ArrayList<Barco>();
        this.velocidadBarcos = VELOCIDAD_INICIAL;
        this.cargasActivas = new ArrayList<CargaDeProfundidad>();
        this.velocidadCargas = VELOCIDAD_INICIAL;
        this.torpedosActivos = new ArrayList<Torpedo>();
    }

    // Metodos
    public void iniciarPartida() {
        this.submarino = new Submarino();
        submarino.inicializar(area.getAnchoPantalla());
        porcentajeVida = 100;
        setTicksEntreBarcos();
    }

    public void terminarPartida() {
        juegoTerminado = true;
    }

    public void actualizar() {
        List<Barco> barcosAEliminar = new ArrayList<>();

        if (barcosGenerados < TOTAL_BARCOS && barcosActivos.size() < MAX_SIMULTANEOS && puedeGenerarBarco()) {
            generarBarco();
            setTicksEntreBarcos();
        }
        disminuirContadorTicksEntreBarcos();

        for (int i = 0; i < barcosActivos.size(); i++) {
            Barco barco = barcosActivos.get(i);
            String direccion = barco.getDireccion();
            if (direccion.equals("izquierda")) {
                barco.moverIzquierda();
            } else {
                barco.moverDerecha();
            }

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
            if (carga.colisionaCon(submarino)) {
                quitarVida();
                cargasAEliminar.add(carga);
            } else if (carga.debeDetonar()) {
                procesarExplosion(carga);
                cargasAEliminar.add(carga);
            } else {
                carga.caer();
            }
        }
        cargasActivas.removeAll(cargasAEliminar);

        List<Torpedo> torpedosAEliminar = new ArrayList<>();

        for (int i = 0; i < torpedosActivos.size(); i++) {
            Torpedo torpedo = torpedosActivos.get(i);

            if (torpedo.llegoASuperficie()) {
                torpedosAEliminar.add(torpedo);
            } else {
                boolean impacto = false;
                for (int j = 0; j < barcosActivos.size(); j++) {
                    Barco barco = barcosActivos.get(j);
                    if (torpedo.colisionaCon(barco)) {
                        barcosAEliminar.add(barco);
                        torpedosAEliminar.add(torpedo);
                        agregarPuntos(50);
                        impacto = true;
                        break;
                    }
                }
                if (!impacto) {
                    torpedo.subir();
                }
            }
        }
        torpedosActivos.removeAll(torpedosAEliminar);
    }

    public void lanzarTorpedo() {
        torpedosActivos.add(submarino.lanzarTorpedo());
    }

    public void moverSubmarino(String direccion) {
        if (direccion.equals("arriba")) { submarino.moverArriba(); }
        else if (direccion.equals("abajo")) { submarino.moverAbajo(); }
        else if (direccion.equals("izquierda")) { submarino.moverIzquierda(); }
        else if (direccion.equals("derecha")) { submarino.moverDerecha(); }
    }

    public void generarBarco() {
        Barco barco = new Barco();
        int cargasMinimas = random.nextInt(CARGAS_MAX_X_BARCO - CARGAS_MIN_X_BARCO + 1) + CARGAS_MIN_X_BARCO;
        String direccion = (random.nextInt(2) == 0) ? "derecha" : "izquierda";
        barco.inicializar(area.getAnchoPantalla(), direccion, velocidadBarcos, cargasMinimas);
        barcosActivos.add(barco);
        barcosGenerados++;
    }

    public boolean puedeGenerarBarco() {
        return ticksEntreBarcos == 0;
    }

    public void disminuirContadorTicksEntreBarcos() {
        if (ticksEntreBarcos > 0) ticksEntreBarcos -= 1;
    }

    public void setTicksEntreBarcos() {
        ticksEntreBarcos = random.nextInt(TIEMPO_MAX_ESPERA - TIEMPO_MIN_ESPERA + 1) + TIEMPO_MIN_ESPERA;
    }

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

    public void agregarPuntos(int puntos) {
        puntaje += puntos;
        puntosExtraAcumulados += puntos;
        if (puntosExtraAcumulados >= 500) {
            agregarVida();
            puntosExtraAcumulados -= 500;
        }
    }

    public boolean verificarFinNivel() {
        return barcosGenerados == TOTAL_BARCOS && barcosActivos.isEmpty() && cargasActivas.isEmpty();
    }

    public void pasarSiguienteNivel() {
        nivel += 1;
        velocidadBarcos *= INCREMENTO_VELOCIDAD;
        velocidadCargas *= INCREMENTO_VELOCIDAD;
        barcosGenerados = 0;
        agregarPuntos(200);
    }

    // Getters
    public boolean isJuegoTerminado() { return juegoTerminado; }
    public int getNivel() { return nivel; }
    public int getVidas() { return vidas; }
    public int getPuntaje() { return puntaje; }
    public int getPorcentajeVida() { return porcentajeVida; }
    public List<Barco> getBarcosActivos() { return barcosActivos; }
    public List<CargaDeProfundidad> getCargasActivas() { return cargasActivas; }
    public List<Torpedo> getTorpedosActivos() { return torpedosActivos; }
    public Submarino getSubmarino() { return submarino; }
    public int getAnchoPantalla() { return area.getAnchoPantalla(); }
    public int getAltoPantalla() { return area.getAltoPantalla(); }
}