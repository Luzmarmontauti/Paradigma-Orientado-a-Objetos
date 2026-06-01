package controller;

import model.Juego;
import view.Vista;

import java.util.Random;

/**
 * Controlador principal del juego. Recibe los eventos del jugador,
 * los delega al modelo y actualiza la vista.
 */
public class GameController {
    //TODO: Agregar singleton
    // Atributos

    private Juego juego;
    private Vista vista;

    // Constructor

    public GameController() {
        this.juego = new Juego(new Random());
    }

    // Comportamiento

    /**
     * Procesa la tecla presionada por el jugador y mueve el submarino.
     * @param tecla tecla presionada (ej: "ARRIBA", "ABAJO", "IZQUIERDA", "DERECHA")
     */
    public void moverTecla(String tecla) {
    }

    public void actualizar() {
    }

    public void notificarVista() {
    }

    /**
     * Muestra el puntaje actual en la vista.
     * @param puntaje puntaje a mostrar
     */
    public void mostrarPuntaje(int puntaje) {
        System.out.println("   SCORE: " + puntaje + " pts");
    }

    /**
     * Muestra la cantidad de vidas restantes en la vista.
     * @param vidas vidas a mostrar
     */
    public void mostrarVidas(int vidas) {
        System.out.print("   Vidas: ");
        for (int i = 0; i < vidas; i++) {
            System.out.print("♥ ");
        }
        System.out.println(" (" + vidas + ")");
    }

    /**
     * Muestra el nivel actual en la vista.
     * @param nivel nivel a mostrar
     */
    public void mostrarNivel(int nivel) {
        System.out.println("\n================================================");
        System.out.println("                ESTADO DEL NIVEL " + nivel);
        System.out.println("================================================");
    }

    /**
     * Muestra la profundidad actual del submarino en la vista.
     * @param profundidad profundidad a mostrar
     */
    public void mostrarProfundidad(double profundidad) {
        System.out.println("   Profundidad: " + String.format("%.2f", profundidad) + " metros");
    }

    /**
     * Muestra un mensaje en pantalla (explosión, nivel completado, game over, etc.).
     * @param msg mensaje a mostrar
     * TODO: definir los mensajes posibles y cuándo se muestran
     */
    public void mostrarMensaje(String msg) {
        System.out.println("\n[SISTEMA]: " + msg.toUpperCase() + "\n");
    }

    /**
     * Renderiza el estado actual del juego en la vista.
     * @param juego estado del juego a renderizar
     */
    public void renderizar(Juego juego) {
    }
}
