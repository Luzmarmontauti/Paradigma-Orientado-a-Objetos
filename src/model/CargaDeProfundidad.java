package model;

import view.CargaDeProfundidadView;
import java.util.Random;

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

    public void caer() {
        if (profundidad < profundidadDetonacion) {
            profundidad += velocidadCaida;
        }
    }


    public boolean debeDetonar() {
        return profundidad >= profundidadDetonacion;
    }


    public double calcularDistancia(Submarino sub) {
        double difX = sub.getPosicionX() - posicionX;
        double difY = sub.getProfundidad() - profundidad;
        return Math.sqrt(Math.pow(difX, 2) + Math.pow(difY, 2));
    }

    public boolean colisionaCon(Submarino submarino) {
        if (Math.abs(posicionX - submarino.getPosicionX()) < 38 ) {
            if (Math.abs(profundidad - submarino.getProfundidad()) < 18) {
                return true;
            }
        }
        return false;
    }

    public CargaDeProfundidadView toView() {
        return new CargaDeProfundidadView((int) posicionX, (int) profundidad, 50, 20);
    }

    //Getters
    public double getPosicionX() { return posicionX; }
    public double getProfundidad() { return profundidad; }
}
