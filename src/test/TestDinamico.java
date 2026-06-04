package test;

import controller.GameController;
import java.util.Scanner;

public class TestDinamico {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        GameController controller = GameController.getInstance();

        System.out.println("================================================");
        System.out.println("     SUBMARINE ATTACK - TEST DINÁMICO GRUPO 14. ");
        System.out.println("================================================");
        System.out.println("Comandos disponibles:");
        System.out.println(" 'arriba'    : Mover Submarino hacia arriba");
        System.out.println(" 'abajo'     : Mover Submarino hacia abajo");
        System.out.println(" 'izquierda' : Mover Submarino a la izquierda");
        System.out.println(" 'derecha'   : Mover Submarino a la derecha");
        System.out.println(" 'tick'      : No moverte, solo avanzar el tiempo (tick)");
        System.out.println(" 'salir'           : Abandonar partida");
        System.out.println("================================================\n");

        controller.notificarVista();

  
        while (!controller.isJuegoTerminado()) {
            
            System.out.println("\n------------------------------------------------");
            System.out.print("¿Qué deseas hacer? (Mover: Arriba, abajo, izquierda, derecha o salir): ");
            String comando = teclado.nextLine().trim().toLowerCase();

           
            if (comando.equals("salir")) {
                System.out.println("Partida abandonada por el usuario.");
                break;
            }

            boolean comandoValido = true;
            if (comando.equals("arriba") || comando.equals("arriba")) {
                controller.moverTecla("arriba");
            } else if (comando.equals("abajo") || comando.equals("abajo")) {
                controller.moverTecla("abajo");
            } else if (comando.equals("izquierda") || comando.equals("izquierda")) {
                controller.moverTecla("izquierda");
            } else if (comando.equals("derecha") || comando.equals("derecha")) {
                controller.moverTecla("derecha");
            } else if (comando.equals("tick") || comando.equals("tick")) {
                System.out.println("[Te quedas quieto esperando el impacto...]");
            } else {
                System.out.println("Comando inválido, intente las opciones válidas.");
                comandoValido = false;
            }

            if (comandoValido) {
                int nivelAnterior = controller.obtenerNivel();
                int vidasAnteriores = controller.obtenerVidas();
                controller.actualizar();

                if (controller.obtenerNivel() != nivelAnterior) {
                    controller.mostrarMensaje("¡FELICIDADES! has pasado al nivel" + controller.obtenerNivel());
                }
                if (controller.obtenerVidas() < vidasAnteriores && !controller.isJuegoTerminado()) {
                    controller.mostrarMensaje("¡Alerta! Una bomba explotó cerca. Vidas restantes: " + controller.obtenerVidas());
                }

                controller.notificarVista();
            }
        }

        System.out.println("\n================================================");
        System.out.println("                  GAME OVER");
        System.out.println("================================================");
        System.out.println("Nivel alcanzado: " + controller.obtenerNivel());
        System.out.println("Puntaje total  : " + controller.obtenerPuntaje());
        System.out.println("================================================");
        
        teclado.close();
    }
}