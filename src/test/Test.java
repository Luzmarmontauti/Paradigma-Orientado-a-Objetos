package test;

import controller.GameController;

/**
 * Punto de entrada de la aplicación.
 * Ejercita todas las funciones del controlador y simula una partida completa por consola.
 */
public class Test {

    public static void main(String[] args) {

        System.out.println("================================================");
        System.out.println("       SUBMARINE ATTACK - TEST FASE C");
        System.out.println("================================================\n");

        GameController controller = GameController.getInstance();

        // --- Estado inicial ---
        System.out.println("--- Estado inicial ---");
        controller.notificarVista();

        // --- Movimiento del submarino en las 4 direcciones ---
        System.out.println("\n--- Movimiento del submarino ---");
        System.out.println("Posicion inicial: X=" + controller.obtenerCoordenadaX() + "  Profundidad=" + controller.obtenerCoordenadaY() + "m\n");

        controller.moverTecla("arriba");
        controller.moverTecla("arriba");
        controller.moverTecla("arriba");
        controller.moverTecla("derecha");
        controller.moverTecla("derecha");
        controller.moverTecla("abajo");
        controller.moverTecla("izquierda");

        // --- Simulacion del juego ---
        System.out.println("\n--- Simulando partida (hasta 200 ticks o Game Over) ---\n");

        for (int tick = 1; tick <= 200; tick++) {
            controller.actualizar();

            if (controller.isJuegoTerminado()) {
                controller.mostrarMensaje("Game Over en tick " + tick);
                break;
            }
        }

        // --- Estado final ---
        System.out.println("\n--- Estado final ---");
        controller.notificarVista();
        System.out.println("Posicion final submarino: X=" + controller.obtenerCoordenadaX() + "  Profundidad=" + controller.obtenerCoordenadaY() + "m");

        System.out.println("\n================================================");
        System.out.println("              FIN DEL TEST");
        System.out.println("================================================");
    }
}
