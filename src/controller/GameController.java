package controller;

import model.Juego;
import model.Barco;
import java.util.Random;
import java.util.List;

/**
 * Controlador principal del juego. Recibe los eventos del jugador,
 * los delega al modelo y actualiza la vista.
 * Implementa el patrón Singleton para garantizar una única instancia.
 */
public class GameController {

    // Atributos
    private static GameController instance;
    private Juego juego;
    private static final double ANCHO_PANTALLA = 800;

    // Constructor privado — usar getInstance() para obtener la instancia
    private GameController() {
        this.juego = new Juego(new Random());
        // TODO Fase Final: no iniciar la partida acá, esperar que el jugador elija desde el menú
        juego.iniciarPartida(ANCHO_PANTALLA);
    }

    // Singleton

    /**
     * Devuelve la única instancia del controlador. La crea si todavía no existe.
     * @return instancia única de GameController
     */
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    // Comportamiento

    /**
     * Recibe la tecla presionada por el jugador y mueve el submarino en esa dirección.
     * @param tecla dirección del movimiento: "arriba", "abajo", "izquierda" o "derecha"
     */
    public void moverTecla(String tecla) {
        double submarinoXPrev = juego.getSubmarinoX();
        double submarinoYPrev = juego.getSubmarinoY();

        juego.moverSubmarino(tecla);

        if (submarinoXPrev != juego.getSubmarinoX() || submarinoYPrev != juego.getSubmarinoY()) {

            mostrarMensaje("Nueva posicion del submarino: (" + obtenerCoordenadaX() + ", " + obtenerCoordenadaY() + ")");
        }

    }

    /**
     * Avanza el juego un tick: mueve barcos, hace caer cargas, procesa explosiones.
     * Si el nivel se completó, pasa al siguiente. Al final actualiza la vista.
     */
    public void actualizar() {
        int barcosPrev = juego.getBarcosActivos().size();
        int cargasPrev = juego.getCargasActivas().size();
        int puntajePrev = juego.getPuntaje();
        int vidasPrev = juego.getVidas();
        int porcentajeVidaPrev = juego.getPorcentajeVida();
        boolean nivelSuperado = juego.verificarFinNivel();

        juego.actualizar(ANCHO_PANTALLA);

        String posExplosion = " en X=" + String.format("%.0f", juego.getUltimaExplosionX()) + " Y=" + String.format("%.0f", juego.getUltimaExplosionY());

        if (juego.getBarcosActivos().size() > barcosPrev) {
            Barco nuevo = juego.getBarcosActivos().get(juego.getBarcosActivos().size() - 1);
            mostrarMensaje("Barco generado en X=" + String.format("%.0f", nuevo.getPosicionX()) + " | Direccion: " + nuevo.getDireccion());
        }
        if (juego.getCargasActivas().size() > cargasPrev) {
            mostrarMensaje("Carga lanzada | Cargas activas: " + juego.getCargasActivas().size());
        }
        if (juego.getPuntaje() - puntajePrev == 30) {
            mostrarMensaje("Carga detono lejos (>100m)" + posExplosion + " | +30 pts | Score: " + juego.getPuntaje());
        } else if (juego.getPuntaje() - puntajePrev == 10) {
            mostrarMensaje("Carga detono cerca (50-100m)" + posExplosion + " | +10 pts, danio leve | Score: " + juego.getPuntaje());
        } else if (juego.getPorcentajeVida() < porcentajeVidaPrev && juego.getVidas() == vidasPrev) {
            mostrarMensaje("Carga detono muy cerca (10-50m)" + posExplosion + " | Danio severo | Vida: " + juego.getPorcentajeVida() + "%");
        }
        if (juego.getVidas() < vidasPrev) {
            mostrarMensaje("Impacto directo" + posExplosion + " | VIDA PERDIDA | Vidas: " + juego.getVidas());
        }
        if (juego.getVidas() > vidasPrev) {
            mostrarMensaje("Vida extra ganada! Vidas: " + juego.getVidas());
        }
        if (nivelSuperado) {
            juego.pasarSiguienteNivel();
            mostrarMensaje("Nivel superado! Comenzando nivel " + juego.getNivel());
        }

        //notificarVista();
    }

    /**
     * Muestra el estado actual del juego por consola.
     * En la Fase Final va a actualizar la interfaz gráfica.
     */
    public void notificarVista() {
        mostrarNivel(juego.getNivel());
        mostrarPuntaje(juego.getPuntaje());
        mostrarVidas(juego.getVidas());
        mostrarProfundidad(juego.getSubmarinoY());
        renderizar(juego);
    }

    // Getters para el test

    /** @return posición horizontal actual del submarino */
    public double obtenerCoordenadaX() {
        return juego.getSubmarinoX();
    }

    /** @return profundidad actual del submarino en metros */
    public double obtenerCoordenadaY() {
        return juego.getSubmarinoY();
    }

    /** @return nivel actual del juego */
    public int obtenerNivel() {
        return juego.getNivel();
    }

    /** @return vidas restantes del jugador */
    public int obtenerVidas() {
        return juego.getVidas();
    }

    /** @return true si el juego terminó (sin vidas) */
    public boolean isJuegoTerminado() {
        return juego.getEstado().equals("GAME OVER");
    }

    /** @return porcentaje de vida actual del submarino */
    public int obtenerPorcentajeVida() {
        return juego.getPorcentajeVida();
    }

    // Métodos de display

    /** Muestra el puntaje actual. */
    public void mostrarPuntaje(int puntaje) {
        System.out.println("   SCORE: " + puntaje + " pts");
    }

    /** Muestra las vidas restantes con corazones. */
    public void mostrarVidas(int vidas) {
        System.out.print("   Vidas: ");
        for (int i = 0; i < vidas; i++) {
            System.out.print("♥ ");
        }
        System.out.println(" (" + vidas + ")");
    }

    /** Muestra el nivel actual con un separador visual. */
    public void mostrarNivel(int nivel) {
        System.out.println("\n================================================");
        System.out.println("                ESTADO DEL NIVEL " + nivel);
        System.out.println("================================================");
    }

    /** Muestra la profundidad actual del submarino. */
    public void mostrarProfundidad(double profundidad) {
        System.out.println("   Profundidad: " + String.format("%.2f", profundidad) + " metros");
    }

    /** Muestra un mensaje del sistema en mayúsculas. */
    public void mostrarMensaje(String msg) {
        System.out.println("\n[SISTEMA]: " + msg.toUpperCase() + "\n");
    }

    /** Muestra la posición actual de cada barco activo en pantalla. */
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

    /**
     * Renderiza el estado del juego en pantalla.
     * TODO Fase Final: reemplazar con el dibujado real en la interfaz gráfica.
     */
    public void renderizar(Juego juego) {
        System.out.println("Renderizado del juego ok");
    }
}
