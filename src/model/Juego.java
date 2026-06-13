package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego {
    protected static final int TOTAL_BARCOS = 12;
    protected static final int MAX_SIMULTANEOS = 3;
    protected static final int TIEMPO_MIN_ESPERA = 10;
    protected static final int TIEMPO_MAX_ESPERA = 100;
    protected static final double VELOCIDAD_INICIAL = 2; 
    protected static final double INCREMENTO_VELOCIDAD = 0.2;
    protected static final int CARGAS_MIN_X_BARCO = 2;
    protected static final int CARGAS_MAX_X_BARCO = 5;

    private String estado;
    private int nivel;
    private int vidas;
    private int puntaje;
    private int puntosExtraAcumulados;

    private Submarino submarino;
    private int porcentajeVida;

    private int barcosGenerados;
    private int ticksEntreBarcos;
    private List<Barco> barcosActivos;
    private double velocidadBarcos;

    private List<CargaDeProfundidad> cargasActivas;
    private double velocidadCargas;
    private double ultimaExplosionX;
    private double ultimaExplosionY;

    private Random random;

    public Juego(Random random) {
        this.estado = "MENU_PRINCIPAL";
        this.nivel = 1;
        this.vidas = 3;
        this.puntaje = 0;
        this.puntosExtraAcumulados = 0;
        this.barcosGenerados = 0;
        this.barcosActivos = new ArrayList<Barco>();
        this.velocidadBarcos = VELOCIDAD_INICIAL;
        this.cargasActivas = new ArrayList<CargaDeProfundidad>();
        this.velocidadCargas = 3.0; 
        this.random = random;
    }

    public void iniciarPartida(double anchoPantalla) {
        this.submarino = new Submarino();
        submarino.inicializar(anchoPantalla);
        porcentajeVida = 100;
        this.estado = "JUGANDO";
        setTicksEntreBarcos();
    }

    public void terminarPartida() { estado = "GAME OVER"; }

    public void actualizar(double anchoPantalla) {
        if (barcosGenerados < TOTAL_BARCOS && barcosActivos.size() < MAX_SIMULTANEOS && puedeGenerarBarco()) {
            generarBarco(anchoPantalla);
            setTicksEntreBarcos();
        }
        disminuirContadorTicksEntreBarcos();

        List<Barco> barcosAEliminar = new ArrayList<>();
        for (int i = 0; i < barcosActivos.size(); i++) {
            Barco barco = barcosActivos.get(i);
            barco.avanzar();

            // Modificado: Seteamos una profundidad máxima base para que la bomba caiga libremente
            int profundidadDetonacion = 750;
            if (barco.puedeDisparar() && !barco.cumplioCargasMinimas()) {
                cargasActivas.add(barco.lanzarCarga(velocidadCargas, profundidadDetonacion));
            }
            barco.contarTicks(random);

            if (barco.haCompletadoRecorrido()) { barcosAEliminar.add(barco); }
        }
        barcosActivos.removeAll(barcosAEliminar);

        List<CargaDeProfundidad> cargasAEliminar = new ArrayList<>();
        for (int i = 0; i < cargasActivas.size(); i++) {
            CargaDeProfundidad carga = cargasActivas.get(i);
            
      
            double distanciaAlSubmarino = carga.calcularDistancia(submarino);

            if (distanciaAlSubmarino < 25) { 
               
                procesarExplosion(carga);
                cargasAEliminar.add(carga);
            } else if (carga.debeDetonar()) {
              
                procesarExplosion(carga);
                cargasAEliminar.add(carga);
            } else { 
                carga.caer(); 
            }
        }
        cargasActivas.removeAll(cargasAEliminar);
    }

    public void moverSubmarino(String direccion) {
        if (direccion.equals("arriba")) { submarino.moverArriba(); }
        else if (direccion.equals("abajo")) { submarino.moverAbajo(); }
        else if (direccion.equals("izquierda")) { submarino.moverIzquierda(); }
        else if (direccion.equals("derecha")) { submarino.moverDerecha(); }
    }

    public void generarBarco(double anchoPantalla) {
        Barco barco = new Barco();
        int cargasMinimas = random.nextInt(CARGAS_MAX_X_BARCO - CARGAS_MIN_X_BARCO + 1) + CARGAS_MIN_X_BARCO;
        String direccion = (random.nextInt(2) == 0) ? "derecha" : "izquierda";
        barco.inicializar(anchoPantalla, direccion, velocidadBarcos, cargasMinimas);
        barcosActivos.add(barco);
        barcosGenerados++;
    }

    public boolean puedeGenerarBarco() { return ticksEntreBarcos == 0; }
    public void disminuirContadorTicksEntreBarcos() { if (ticksEntreBarcos > 0) ticksEntreBarcos -= 1; }
    public void setTicksEntreBarcos() { ticksEntreBarcos = random.nextInt(TIEMPO_MAX_ESPERA - TIEMPO_MIN_ESPERA + 1) + TIEMPO_MIN_ESPERA; }

    public void procesarExplosion(CargaDeProfundidad carga) {
        this.ultimaExplosionX = carga.getPosicionX();
        this.ultimaExplosionY = carga.getProfundidad();
        double distancia = carga.calcularDistancia(submarino);
        
        if (distancia < 30) { 
            quitarVida(); 
        } else if (distancia > 30 && distancia <= 75) { 
            recibirDanio(50); 
        } else if (distancia > 75 && distancia <= 120) { 
            recibirDanio(30); 
            agregarPuntos(10);
        } else { 
            agregarPuntos(30); 
        }
    }

    public boolean estaVivo() { return vidas >= 1; }
    public void recibirDanio(int porcentajeDanio) {
        porcentajeVida -= porcentajeDanio;
        if (porcentajeVida <= 0) { quitarVida(); porcentajeVida = 100; }
    }
    public void agregarVida() { vidas += 1; }
    public void quitarVida() { vidas -= 1; if (vidas == 0) terminarPartida(); }

    public void agregarPuntos(int puntos) {
        puntaje += puntos;
        puntosExtraAcumulados += puntos;
        if (puntosExtraAcumulados >= 500) { agregarVida(); puntosExtraAcumulados -= 500; }
    }

    public boolean verificarFinNivel() { return barcosGenerados == TOTAL_BARCOS && barcosActivos.isEmpty() && cargasActivas.isEmpty(); }
    public void pasarSiguienteNivel() {
        nivel += 1;
        velocidadBarcos += (velocidadBarcos * INCREMENTO_VELOCIDAD);
        velocidadCargas += (velocidadCargas * INCREMENTO_VELOCIDAD);
        barcosGenerados = 0;
        agregarPuntos(200);
    }

    public String getEstado() { return estado; }
    public int getNivel() { return nivel; }
    public int getVidas() { return vidas; }
    public int getPuntaje() { return puntaje; }
    public int getPorcentajeVida() { return porcentajeVida; }
    public List<Barco> getBarcosActivos() { return barcosActivos; }
    public List<CargaDeProfundidad> getCargasActivas() { return cargasActivas; }
    public double getSubmarinoX() { return submarino.getPosicionX(); }
    public double getSubmarinoY() { return submarino.getProfundidad(); }
    public double getUltimaExplosionX() { return ultimaExplosionX; }
    public double getUltimaExplosionY() { return ultimaExplosionY; }
    public Submarino getSubmarino() { return this.submarino; }
}