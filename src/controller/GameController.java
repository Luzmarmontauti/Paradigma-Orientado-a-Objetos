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
    //TODO: Agregar singleton
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
    	case "activar":
    		System.out.println("Juego activado.");
    		this.juego.iniciarPartida();
    	break;
    	case "terminar":
    		System.out.println("Juego terminado.");
    		this.juego.terminarPartida();
    	break;
    	default: 
    		System.out.println("Parámetro no reconocido, intente de nuevo. Los válidos son: Activar o Terminar.");
 
    	}
    	
    }

    public void actualizar() {
    	this.juego.actualizar();
    	//Si esta vivo o no, muestra dos mensajes:
    	
    	if (!this.juego.estaVivo()) {
    		this.mostrarMensaje("Motores funcionales. El submarino responde a los mandos.");
    	}
    	else {
    		this.mostrarMensaje("El submarino ha sido destruido. Tu tripulación y tú descansan ahora en el silencio de las profundidades.");
    	}
    	
    	//Al terminar el nivel
    	if(this.juego.verificarFinNivel()) {
    		this.mostrarMensaje("Ya estamos cerca de terminar la misión. ¡ÁNIMO!");
    	}
    	 
    	//Al pasar de nivel muestra mensaje
    	if (this.juego.pasarSiguienteNivel()){
    		this.mostrarMensaje("¡FELICIDADES! Avanzaste al siguiente nivel.");
    	}
    	
    	//Ganar vidas
    	
    	this.juego.agregarVida();
    	this.mostrarMensaje("¡HEMOS GANADO UNA VIDA!");
    	
    	//
    	
    	//Se actualiza todo
    	
    	this.notificarVista();	
    	
    }

    public void notificarVista() {
    	int nivelActual = this.juego.getNivel();
    	this.mostrarNivel(nivelActual);
    	
    	int puntajeActual = this.juego.getPuntaje();
    	this.mostrarPuntaje(puntajeActual);
    	
    	int vidasActual = this.juego.getVidas();
    	this.mostrarVidas(vidasActual);
    	}

    /**
     * Muestra el puntaje actual en la vista.
     * @param puntaje puntaje a mostrar
     */
    public void mostrarPuntaje(int puntaje) {
    	System.out.println("Puntaje: " + puntaje);
    }

    /**
     * Muestra la cantidad de vidas restantes en la vista.
     * @param vidas vidas a mostrar
     */
    public void mostrarVidas(int vidas) {
    	System.out.println("Vidas" + vidas);
    	
    }

    /**
     * Muestra el nivel actual en la vista.
     * @param nivel nivel a mostrar
     */
    public void mostrarNivel(int nivel) {
    	System.out.println("NIVEL ACTUAL: " + nivel);
    	
    	
    }

    /**
     * Muestra la profundidad actual del submarino en la vista.
     * @param profundidad profundidad a mostrar
     */
    public void mostrarProfundidad(double profundidad) {
    	System.out.println("Profundidad: " + profundidad); 
    }

    /**
     * Muestra un mensaje en pantalla (explosión, nivel completado, game over, etc.).
     * @param msg mensaje a mostrar
     * TODO: definir los mensajes posibles y cuándo se muestran
     * 
     * Mensajes: 
     * 1. Cuando inicia el juego, 2. cuando el submarino está activo, 3.cuando se lanza una carga 4.cuando el submarino las esquiva
     * 5.cuando gana puntos 6.cuando pierde 7.cuando pierde vidas 8.cuando gana vidas 9. cuando pasa de nivel 10.cuando muere  
     */
    public void mostrarMensaje(String msg) {
    	System.out.println("==========================================");
    	System.out.println("[¡ATENTO!]" + msg + " ");
    	System.out.println("==========================================");
    }

    /**
     * Renderiza el estado actual del juego en la vista.
     * @param juego estado del juego a renderizar
     */
    public void renderizar(Juego juego) {
    	this.renderizar(this.juego);
    }
}
