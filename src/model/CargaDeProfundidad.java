package model;

public class CargaDeProfundidad {
    protected static final double PROF_DET_MIN = 300;
    protected static final double PROF_DET_MAX = 700;

    private double posicionX;
    private double profundidad;
    private double profundidadDetonacion;
    private double velocidadCaida;

    public CargaDeProfundidad() {
        this.posicionX = 0;
        this.profundidad = 0;
        this.profundidadDetonacion = 0;
        this.velocidadCaida = 0;
    }

    public void inicializar(double posicionX, double velocidadCaida, double profundidadDetonacion) {
        this.posicionX = posicionX;
        this.velocidadCaida = velocidadCaida;
        this.profundidadDetonacion = profundidadDetonacion;
        this.profundidad = 0;
    }

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

    public double getPosicionX()             { return posicionX; }
    public double getProfundidad()           { return profundidad; }
    public double getProfundidadDetonacion() { return profundidadDetonacion; }
    public double getVelocidadCaida()        { return velocidadCaida; }
    
    // TRADUCTOR PARA LA VISTA
    public view.Vista toView() {
        int posX = (int) this.getPosicionX();
        int posY = (int) this.getProfundidad();
        return new view.Vista(posX, posY, 20, 10);
    }
}