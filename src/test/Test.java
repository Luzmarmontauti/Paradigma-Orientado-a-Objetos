package test;

import controller.GameController;

/**
 * Punto de entrada de la aplicación.
 * Acá se llama a todas las funciones del controlador para probar que el juego funciona.
 */
public class Test {

    public static void main(String[] args) {

        System.out.println("================================================");
        System.out.println("       SUBMARINE ATTACK - TEST FASE C");
        System.out.println("================================================\n");

        GameController controller = GameController.getInstance();

        // Estado inicial del juego
        System.out.println("--- Estado inicial ---");
        controller.notificarVista();

        // Movimiento del submarino en las 4 direcciones
        System.out.println("\n--- Movimiento del submarino ---");
        System.out.println("Posicion inicial: X=" + controller.obtenerCoordenadaX() + "  Profundidad=" + controller.obtenerCoordenadaY() + "m");

        controller.moverTecla("arriba");
        controller.moverTecla("arriba");
        System.out.println("Despues de mover arriba x2: X=" + controller.obtenerCoordenadaX() + "  Profundidad=" + controller.obtenerCoordenadaY() + "m");

        controller.moverTecla("abajo");
        System.out.println("Despues de mover abajo x1: X=" + controller.obtenerCoordenadaX() + "  Profundidad=" + controller.obtenerCoordenadaY() + "m");

        controller.moverTecla("derecha");
        controller.moverTecla("derecha");
        controller.moverTecla("derecha");
        System.out.println("Despues de mover derecha x3: X=" + controller.obtenerCoordenadaX() + "  Profundidad=" + controller.obtenerCoordenadaY() + "m");

        controller.moverTecla("izquierda");
        System.out.println("Despues de mover izquierda x1: X=" + controller.obtenerCoordenadaX() + "  Profundidad=" + controller.obtenerCoordenadaY() + "m");

        // Simulacion del juego: ticks con barcos, cargas y explosiones
        System.out.println("\n--- Simulando ticks del juego ---");
        int nivelAnterior = controller.obtenerNivel();
        int vidasAnteriores = controller.obtenerVidas();

        for (int i = 1; i <= 10; i++) {
            controller.actualizar();

            if (controller.obtenerNivel() != nivelAnterior) {
                controller.mostrarMensaje("Paso al nivel " + controller.obtenerNivel());
                nivelAnterior = controller.obtenerNivel();
            }

            if (controller.obtenerVidas() < vidasAnteriores) {
                controller.mostrarMensaje("Vida perdida! Vidas restantes: " + controller.obtenerVidas());
                vidasAnteriores = controller.obtenerVidas();
            }

            if (controller.isJuegoTerminado()) {
                controller.mostrarMensaje("Game Over en tick " + i);
                break;
            }
        }

        // Estado final
        System.out.println("\n--- Estado final ---");
        controller.notificarVista();
        System.out.println("Posicion final submarino: X=" + controller.obtenerCoordenadaX() + "  Profundidad=" + controller.obtenerCoordenadaY() + "m");

        System.out.println("\n================================================");
        System.out.println("              FIN DEL TEST");
        System.out.println("================================================");
    }
}
