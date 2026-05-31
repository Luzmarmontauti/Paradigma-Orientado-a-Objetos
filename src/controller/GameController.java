package controller;

import model.Juego;
/*import model.Barco;
import model.CargaDeProfundidad;
import model.Submarino;
import movimiento.Area;*/

/**
 * Controlador principal del juego. Recibe los eventos del jugador,
 * los delega al modelo y actualiza la vista.
 */
public class GameController {
	
    // Atributos

    private Juego juego;
    //private Vista vista;

    // Constructor  

    public GameController() {
        this.juego = new Juego();
    }

    // Comportamiento

    /**
     * Procesa la tecla presionada por el jugador y mueve el submarino.
     * @param tecla tecla presionada (ej: "ARRIBA", "ABAJO", "IZQUIERDA", "DERECHA")
     */
    public void moverTecla(String tecla) {
    	
    	switch (tecla.toLowerCase()) {
    	case "Activar":
    		System.out.println("Juego activado.");
    		this.juego.iniciarPartida();
    	break;
    	case "Terminar":
    		System.out.println("Juego terminado.");
    		this.juego.terminarPartida();
    	break;
    	default: 
    		System.out.println("Parámetro no reconocido, intente de nuevo. Los válidos son: Activar o Pausar.");
 
    	}
    	
    }

    public void actualizar() {
    	
    	
    }

    public void notificarVista() {
    	//queda a definicion
    }

    /**
     * Muestra el puntaje actual en la vista.
     * @param puntaje puntaje a mostrar
     */
    public void mostrarPuntaje(int puntaje) {
    }

    /**
     * Muestra la cantidad de vidas restantes en la vista.
     * @param vidas vidas a mostrar
     */
    public void mostrarVidas(int vidas) {
    	System.out.println("===================================");
    	System.out.println("----------VIDAS----------");
    	System.out.println("===================================");
    	System.out.println("En este nivel tienes vidas: " + vidas);
    	
    }

    /**
     * Muestra el nivel actual en la vista.
     * @param nivel nivel a mostrar
     */
    public void mostrarNivel(int nivel) {
    	System.out.println("===================================");
    	System.out.println("----------NIVEL----------");
    	System.out.println("===================================");
    	System.out.println("NIVEL ACTUAL: " + nivel);
    	
    	
    }

    /**
     * Muestra la profundidad actual del submarino en la vista.
     * @param profundidad profundidad a mostrar
     */
    public void mostrarProfundidad(double profundidad) {
    	
    }

    /**
     * Muestra un mensaje en pantalla (explosión, nivel completado, game over, etc.).
     * @param msg mensaje a mostrar
     * TODO: definir los mensajes posibles y cuándo se muestran
     */
    public void mostrarMensaje(String msg) {
    }

    /**
     * Renderiza el estado actual del juego en la vista.
     * @param juego estado del juego a renderizar
     */
    public void renderizar(Juego juego) {
    }
}
