package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Juego {
    protected static final int TOTAL_BARCOS = 12; // RF-03
    protected static final int MAX_SIMULTANEOS = 3; // RF-05
    protected static final int TIEMPO_MIN_ESPERA = 10;
    protected static final int TIEMPO_MAX_ESPERA = 100;
    protected static final double VELOCIDAD_INICIAL = 2.0; 
    protected static final double INCREMENTO_VELOCIDAD = 0.20; // +20% por nivel (RF-09 y RF-10)
    protected static final int CARGAS_MIN_X_BARCO = 2;
    protected static final int CARGAS_MAX_X_BARCO = 5;

    private String estado;
    private int nivel; // RF-24
    private Submarino submarino;

    private int barcosGenerados;
    private int ticksEntreBarcos;
    private List<Barco> barcosActivos;
    private double velocidadBarcos;

    private List<CargaDeProfundidad> cargasActivas;
    private double velocidadCargas;
    
    private double ultimaExplosionX;
    private double ultimaExplosionY;
    private String ultimoMensajePuntos = ""; // RF-29
    private Random random;

    public Juego(Random random) {
        this.estado = "MENU_PRINCIPAL";
        this.nivel = 1;
        this.barcosGenerados = 0;
        this.barcosActivos = new ArrayList<>();
        this.velocidadBarcos = VELOCIDAD_INICIAL;
        this.cargasActivas = new ArrayList<>();
        this.velocidadCargas = 3.0; 
        this.random = random;
    }

    public void iniciarPartida(double anchoPantalla) {
        this.submarino = new Submarino();
        this.submarino.inicializar(anchoPantalla);
        this.estado = "JUGANDO";
        setTicksEntreBarcos();
    }

    public void terminarPartida() { 
        this.estado = "GAME OVER"; 
    }

    public void actualizar(double anchoPantalla) {
        if (!submarino.isActivo()) {
            terminarPartida();
            return;
        }

        // Control de spawn de barcos enemigos
        if (barcosGenerados < TOTAL_BARCOS && barcosActivos.size() < MAX_SIMULTANEOS && puedeGenerarBarco()) {
            generarBarco(anchoPantalla);
            setTicksEntreBarcos(); //numero random entre 10 y 100 que son las variables min de espera y max de espera
        }
        disminuirContadorTicksEntreBarcos(); //el contador debe seguir avanzando, por eso lo dejamos fuera del if

        // Actualizar posiciones de Barcos
        List<Barco> barcosAEliminar = new ArrayList<>();
        
        for (Barco barco : barcosActivos) {
            barco.avanzar();

            if (barco.puedeDisparar() && !barco.cumplioCargasMinimas()) { //cargas minimas entre 2 y 5
                double profDetonacion = random.nextInt((int)(CargaDeProfundidad.PROF_DET_MAX - CargaDeProfundidad.PROF_DET_MIN + 1)) + CargaDeProfundidad.PROF_DET_MIN;
                cargasActivas.add(barco.lanzarCarga(velocidadCargas, profDetonacion));
            }
            
            barco.contarTicks(random);

            if (barco.haCompletadoRecorrido()) { 
                barcosAEliminar.add(barco); 
            }
        }
        barcosActivos.removeAll(barcosAEliminar);

        // Actualizar caída de cargas y detonaciones por profundidad
        List<CargaDeProfundidad> cargasAEliminar = new ArrayList<>();
        for (CargaDeProfundidad carga : cargasActivas) {
            carga.caer(); 

            if (carga.debeDetonar()) {
                procesarExplosion(carga);
                cargasAEliminar.add(carga);
            }
        }
        cargasActivas.removeAll(cargasAEliminar);
    }

    public void moverSubmarino(String direccion) {
        if (estado.equals("JUGANDO")) {
            if (direccion.equals("arriba")) { submarino.moverArriba(); }
            else if (direccion.equals("abajo")) { submarino.moverAbajo(); }
            else if (direccion.equals("izquierda")) { submarino.moverIzquierda(); }
            else if (direccion.equals("derecha")) { submarino.moverDerecha(); }
        }
    }

    public void generarBarco(double anchoPantalla) {
        Barco barco = new Barco();
        int cargasMinimas = random.nextInt(CARGAS_MAX_X_BARCO - CARGAS_MIN_X_BARCO + 1) + CARGAS_MIN_X_BARCO;
        String direccion = (random.nextInt(2) == 0) ? "derecha" : "izquierda"; 
        barco.inicializar(anchoPantalla, direccion, velocidadBarcos, cargasMinimas);
        barcosActivos.add(barco);
        barcosGenerados++;
    }

    public void procesarExplosion(CargaDeProfundidad carga) {
        this.ultimaExplosionX = carga.getPosicionX();
        this.ultimaExplosionY = carga.getProfundidadDetonacion();

        double subX = submarino.getCentroX();
        double subY = submarino.getCentroY();
        double distancia = Math.sqrt(Math.pow(subX - ultimaExplosionX, 2) + Math.pow(subY - ultimaExplosionY, 2));
        
        if (distancia < 10) { 
            this.ultimoMensajePuntos = "💥 [BOOM] Impacto crítico a " + (int)distancia + "m. ¡Se pierde una vida entera! (0 pts)";
            submarino.perderUnaVida(); 
        } else if (distancia >= 10 && distancia < 50) { 
            this.ultimoMensajePuntos = "💥 [BOOM] Explosión cercana a " + (int)distancia + "m. Energía -50% (0 pts)";
            submarino.disminuirEnergia(50); 
        } else if (distancia >= 50 && distancia <= 100) { 
            this.ultimoMensajePuntos = "💥 [BOOM] Explosión moderada a " + (int)distancia + "m. Energía -30% (+10 pts)";
            submarino.disminuirEnergia(30); 
            submarino.sumarPuntos(10);
        } else { 
            this.ultimoMensajePuntos = "💥 [BOOM] Explosión distante a " + (int)distancia + "m. Sin daños (+30 pts)";
            submarino.sumarPuntos(30); 
        }
    }

    public boolean verificarFinNivel() { 
        return barcosGenerados == TOTAL_BARCOS && barcosActivos.isEmpty() && cargasActivas.isEmpty(); 
    }

    public void pasarSiguienteNivel() {
        nivel += 1;
        // RF-09 y RF-10: Escalabilidad de velocidad del 20%
        velocidadBarcos += (velocidadBarcos * INCREMENTO_VELOCIDAD);
        velocidadCargas += (velocidadCargas * INCREMENTO_VELOCIDAD);
        barcosGenerados = 0;
        submarino.sumarPuntos(200); // RF-20: Bono por cambiar de nivel
    }

    public void disminuirContadorTicksEntreBarcos() { 
    	if (ticksEntreBarcos > 0) 
    	ticksEntreBarcos -= 1; 
    }
    public void setTicksEntreBarcos() {   // 100            -     10     = 90 + 1         10
    	ticksEntreBarcos = random.nextInt(TIEMPO_MAX_ESPERA - TIEMPO_MIN_ESPERA + 1) + TIEMPO_MIN_ESPERA; 
    	}
    public boolean puedeGenerarBarco() {
    	return ticksEntreBarcos == 0;
    	}

    // GETTERS DE CONTROL DE DATOS (Conexión directa al Submarino encapsulado)
    public String getEstado() { 
    	return estado; 
    	}
    public int getNivel() {
    	return nivel; 
    	}
    public int getVidas() {
    	return submarino.getVidas(); 
    	} 
    public int getPuntaje() {
    	return submarino.getPuntos();
    	} 
    public int getPorcentajeVida() { 
    	return submarino.getSaludPorcentaje();
    	} 
    public List<Barco> getBarcosActivos() {
    	return barcosActivos; 
    	}
    public List<CargaDeProfundidad> getCargasActivas() { 
    	return cargasActivas; 
    	}
    public double getSubmarinoX() { 
    	return submarino.getPosicionX(); 
    	}
    public double getSubmarinoY() { 
    	return submarino.getProfundidad();
    	}
    public Submarino getSubmarino() {
    	return this.submarino; 
    	}
    
    public String getUltimoMensajePuntos() { 
        String aux = this.ultimoMensajePuntos;
        this.ultimoMensajePuntos = ""; // Reset de lectura única
        return aux;
    }
}