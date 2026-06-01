package controller;

import model.Juego;


public class GameController {
    // TODO: Agregar singleton

    // Atributos
    private Juego juego;

    // Constructor  
    public GameController() {
        this.juego = new Juego();
    }

    // Comportamiento

    /**
     * Procesa la tecla presionada por el jugador y mueve el submarino o cambia el estado.
     * @param tecla tecla presionada (ej: "ACTIVAR", "TERMINAR")
     */
    public void moverTecla(String tecla) {
        switch (tecla.toLowerCase()) {
            case "activar":
                mostrarMensaje("Juego activado.");
                this.juego.iniciarPartida();
                break;
            case "terminar":
                mostrarMensaje("Juego terminado.");
                this.juego.terminarPartida();
                break;
            default: 
                System.out.println("Parámetro no reconocido. Intente de nuevo. Los válidos son: Activar o Terminar.");
        }
    }

   
    public void actualizar() {
        if (juego.getEstado() != null && juego.getEstado().equalsIgnoreCase("JUGANDO")) {
            
            juego.actualizar(); 
            
            juego.verificarColisiones();
            
            if (!juego.estaVivo()) {
                juego.terminarPartida();
                mostrarMensaje("GAME OVER: El submarino ha sido destruido. Tu tripulación y tú descansan en el silencio de las profundidades.");
            }
            
            else if (juego.verificarFinNivel()) {
                juego.pasarSiguienteNivel();
                mostrarMensaje("¡FELICIDADES! Avanzaste al nivel " + juego.getNivel());
                
                juego.agregarVida();
                mostrarMensaje("¡HEMOS GANADO UNA VIDA POR COMPLETAR EL NIVEL!");
            }
            
            notificarVista();
        }
    }

    public void notificarVista() {
        mostrarNivel(juego.getNivel());
        mostrarPuntaje(juego.getPuntaje());
        mostrarVidas(juego.getVidas());
        mostrarProfundidad(juego.getSubmarinoY());
    }

    public void mostrarPuntaje(int puntaje) {
        System.out.println("   SCORE: " + puntaje + " pts");
    }

    public void mostrarVidas(int vidas) {
        System.out.print("   VIDAS: ");
        for (int i = 0; i < vidas; i++) {
            System.out.print("♥ ");
        }
        System.out.println(" (" + vidas + ")");
    }

 
    public void mostrarNivel(int nivel) {
        System.out.println("\n================================================");
        System.out.println("                ESTADO DEL NIVEL " + nivel);
        System.out.println("================================================");
    }

    
    public void mostrarProfundidad(double profundidad) {
        System.out.println("   PROFUNDIDAD: " + String.format("%.2f", profundidad) + " metros");
    }

    
    public void mostrarMensaje(String msg) {
        System.out.println("\n💥 [SISTEMA]: " + msg.toUpperCase() + " 💥\n");
    }

    public void renderizar(Juego juego) {
        System.out.println("[Renderizando mapa del juego...]");
    }
}