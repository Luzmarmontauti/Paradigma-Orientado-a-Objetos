package controller;

import model.Juego;
import model.Barco;
import java.util.List;
import java.util.Random;
import view.Vista;

/**
 * Controlador principal del juego. Recibe los eventos del jugador,
 * los delega al modelo y actualiza la vista.
 * Implementa el patrón Singleton para garantizar una única instancia.
 */
public class GameController {

    // =========================================================
    // ATRIBUTOS
    // =========================================================

    private static GameController instance;
    private Juego juego;
    private static final double ANCHO_PANTALLA = 800;

    // =========================================================
    // SINGLETON
    // =========================================================

    private GameController() {
        this.juego = new Juego(new Random());
        // TODO Fase Final: no iniciar la partida acá, esperar que el jugador elija desde el menú
        juego.iniciarPartida(ANCHO_PANTALLA);
    }

    /**
     * Devuelve la única instancia del controlador. La crea si todavía no existe.
     *
     * @return instancia única de GameController
     */
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    // =========================================================
    // COMPORTAMIENTO
    // =========================================================

    /**
     * Recibe la tecla presionada por el jugador, mueve el submarino
     * e imprime la nueva posición si hubo movimiento.
     *
     * @param tecla "arriba", "abajo", "izquierda" o "derecha"
     */
    public void moverTecla(String tecla) {
        double xPrev = juego.getSubmarinoX();
        double yPrev = juego.getSubmarinoY();

        juego.moverSubmarino(tecla);

        if (xPrev != juego.getSubmarinoX() || yPrev != juego.getSubmarinoY()) {
            mostrarMensaje("Nueva posicion del submarino: (" + obtenerCoordenadaX() + ", " + obtenerCoordenadaY() + ")");
        }
    }

    /**
     * Avanza el juego un tick: mueve barcos, hace caer cargas y procesa explosiones.
     * Detecta y muestra por consola los eventos relevantes del tick.
     */
    public void actualizar() {
        int barcosPrev       = juego.getBarcosActivos().size();
        int cargasPrev       = juego.getCargasActivas().size();
        int puntajePrev      = juego.getPuntaje();
        int vidasPrev        = juego.getVidas();
        int vidaPorcentPrev  = juego.getPorcentajeVida();
        boolean nivelSuperado = juego.verificarFinNivel();

        juego.actualizar(ANCHO_PANTALLA);

        String posExp = " en X=" + String.format("%.0f", juego.getUltimaExplosionX())
                      + " Y=" + String.format("%.0f", juego.getUltimaExplosionY());

        if (juego.getBarcosActivos().size() > barcosPrev) {
            Barco nuevo = juego.getBarcosActivos().get(juego.getBarcosActivos().size() - 1);
            mostrarMensaje("Barco generado en X=" + String.format("%.0f", nuevo.getPosicionX()) + " | Direccion: " + nuevo.getDireccion());
        }
        if (juego.getCargasActivas().size() > cargasPrev) {
            mostrarMensaje("Carga lanzada | Cargas activas: " + juego.getCargasActivas().size());
        }
        if (juego.getPuntaje() - puntajePrev == 30) {
            mostrarMensaje("Carga detono lejos (>100m)" + posExp + " | +30 pts | Score: " + juego.getPuntaje());
        } else if (juego.getPuntaje() - puntajePrev == 10) {
            mostrarMensaje("Carga detono cerca (50-100m)" + posExp + " | +10 pts, danio leve | Score: " + juego.getPuntaje());
        } else if (juego.getPorcentajeVida() < vidaPorcentPrev && juego.getVidas() == vidasPrev) {
            mostrarMensaje("Carga detono muy cerca (10-50m)" + posExp + " | Danio severo | Vida: " + juego.getPorcentajeVida() + "%");
        }
        if (juego.getVidas() < vidasPrev) {
            mostrarMensaje("Impacto directo" + posExp + " | VIDA PERDIDA | Vidas: " + juego.getVidas());
        }
        if (juego.getVidas() > vidasPrev) {
            mostrarMensaje("Vida extra ganada! Vidas: " + juego.getVidas());
        }
        if (nivelSuperado) {
            juego.pasarSiguienteNivel();
            mostrarMensaje("Nivel superado! Comenzando nivel " + juego.getNivel());
        }
    }

    // =========================================================
    // VISTA
    // =========================================================

    /**
     * Muestra el estado completo del juego por consola.
     * En la Fase Final actualizará la interfaz gráfica.
     */
    public void notificarVista() {
        mostrarNivel(juego.getNivel());
        mostrarPuntaje(juego.getPuntaje());
        mostrarVidas(juego.getVidas());
        mostrarProfundidad(juego.getSubmarinoY());
        renderizar(juego);
    }

    /** Muestra la posición actual de cada barco activo. */
    public void mostrarEstadoBarcos() {
        List<Barco> barcos = juego.getBarcosActivos();
        if (barcos.isEmpty()) {
            System.out.println("   [Sin barcos activos en pantalla]");
            return;
        }
        System.out.println("   Barcos activos: " + barcos.size());
        for (int i = 0; i < barcos.size(); i++) {
            Barco b = barcos.get(i);
            System.out.println("   Barco " + (i + 1) + ": X=" + String.format("%.0f", b.getPosicionX()) + " | Direccion: " + b.getDireccion());
        }
    }

    public void mostrarPuntaje(int puntaje)         { System.out.println("   SCORE: " + puntaje + " pts"); }

    public void mostrarVidas(int vidas) {
        System.out.print("   Vidas: ");
        for (int i = 0; i < vidas; i++) System.out.print("♥ ");
        System.out.println(" (" + vidas + ")");
    }

    public void mostrarNivel(int nivel) {
        System.out.println("\n================================================");
        System.out.println("                ESTADO DEL NIVEL " + nivel);
        System.out.println("================================================");
    }

    public void mostrarProfundidad(double profundidad) {
        System.out.println("   Profundidad: " + String.format("%.2f", profundidad) + " metros");
    }

    public void mostrarMensaje(String msg)          { System.out.println("\n[SISTEMA]: " + msg.toUpperCase() + "\n"); }

    /** TODO Fase Final: reemplazar con el dibujado real en la interfaz gráfica. */
    public void renderizar(Juego juego)             { System.out.println("Renderizado del juego ok"); }

    // =========================================================
    // GETTERS
    // =========================================================

    public double obtenerCoordenadaX() { return juego.getSubmarinoX(); 
    }
    public double obtenerCoordenadaY() { return juego.getSubmarinoY(); 
    }
    public int obtenerNivel() { return juego.getNivel(); 
    }
    public int obtenerVidas() { return juego.getVidas(); 
    }
    public int obtenerPorcentajeVida() { return juego.getPorcentajeVida(); 
    }
    public boolean isJuegoTerminado() { return juego.getEstado().equals("GAME OVER"); 
    }
    
    //Conexión con vista 
   
    
     
    
}
