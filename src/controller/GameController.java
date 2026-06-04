package controller;

import model.Juego;
import java.util.Random;

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
        juego.moverSubmarino(tecla);
    }

    /**
     * Avanza el juego un tick: mueve barcos, hace caer cargas, procesa explosiones.
     * Si el nivel se completó, pasa al siguiente. Al final actualiza la vista.
     */
    public void actualizar() {
        juego.actualizar(ANCHO_PANTALLA);
        if (juego.verificarFinNivel()) {
            juego.pasarSiguienteNivel();
        }
        notificarVista();
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
    
    public int obtenerPuntaje() {
    	return juego.getPuntaje();    }

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

    /**
     * Renderiza el estado del juego en pantalla.
     * TODO Fase Final: reemplazar con el dibujado real en la interfaz gráfica.
     */
    public void renderizar(Juego juego) {
        System.out.println("Renderizado del juego ok");
    }
}
