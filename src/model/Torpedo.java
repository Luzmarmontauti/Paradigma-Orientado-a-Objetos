package model;

import view.TorpedoView;

public class Torpedo {

    // Atributos
    private static final int PROF_SUPERFICIE = 40;
    private double posicionX;
    private double profundidad;
    private double velocidadSubida;

    // Constructor
    public Torpedo(double posicionX, double profundidad, double velocidadSubida) {
        this.posicionX = posicionX;
        this.profundidad = profundidad;
        this.velocidadSubida = velocidadSubida;
    }

    // Metodos
    public void subir() {
        profundidad -= velocidadSubida;
    }

    public boolean llegoASuperficie() {
        return profundidad <= PROF_SUPERFICIE;
    }

    public boolean colisionaCon(Barco barco) {
        if (Math.abs(posicionX - barco.getPosicionX()) < 38) {
            if (profundidad <= PROF_SUPERFICIE + 20) {
                return true;
            }
        }
        return false;
    }

    public TorpedoView toView() {
        return new TorpedoView((int) posicionX, (int) profundidad, 10, 20);
    }

    // Getters
    public double getPosicionX() { return posicionX; }
    public double getProfundidad() { return profundidad; }
}