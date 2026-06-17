package model;

import view.CargaDeProfundidadView;
import java.util.Random;

/**
 * Carga de profundidad lanzada por un barco. Cae verticalmente
 * y detona al alcanzar su profundidad de detonación.
 */
public class CargaDeProfundidad {

    //Atributos
    protected static final int PROF_DET_MIN = 300;
    protected static final int PROF_DET_MAX = 700;
    private double posicionX;
    private double profundidad;
    private double profundidadDetonacion;
    private double velocidadCaida;
    private Random random = new Random();

    //Constructor
    public CargaDeProfundidad(double posicionX, double velocidadCaida) {
        this.posicionX = posicionX;
        this.velocidadCaida = velocidadCaida;
        this.profundidadDetonacion = random.nextInt(PROF_DET_MAX - PROF_DET_MIN + 1) + PROF_DET_MIN;
        this.profundidad = 30;
    }

    //Metodos

    /** Avanza la carga un paso hacia abajo según su velocidad de caída. */
    public void caer() {
        if (profundidad < profundidadDetonacion) {
            profundidad += velocidadCaida;
        }
    }

    /** @return true si la carga alcanzó su profundidad de detonación. */
    public boolean debeDetonar() {
        return profundidad >= profundidadDetonacion;
    }

    /**
     * Calcula la distancia entre esta carga y el submarino al momento de la explosión.
     *
     * @param sub el submarino
     * @return distancia en unidades de juego (la distancia es una linea entre las posiciones de los objetos(hipotenusa), las extensiones del objeto al eje Y, y al eje X (los catetos) y se calcula aplicando Pitágoras)
     */
    public double calcularDistancia(Submarino sub) {
        double difX = sub.getPosicionX() - posicionX;
        double difY = sub.getProfundidad() - profundidad;
        return Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
    }

    public CargaDeProfundidadView toView() {
        return new CargaDeProfundidadView((int) posicionX, (int) profundidad, 50, 20);
    }

    //Getters
    public double getPosicionX() { return posicionX; }
    public double getProfundidad() { return profundidad; }
}
