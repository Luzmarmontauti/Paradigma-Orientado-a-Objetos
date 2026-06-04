package model;

/**
 * Submarino controlado por el jugador. Se mueve en las cuatro direcciones
 * dentro de los límites de profundidad permitidos.
 */
public class Submarino {

    // Atributos

    protected static final double PROF_MIN = 300; //protected son modificadores de acceso, se piensa en la herencia cuando se usa este tipo de modificador. 
    protected static final double PROF_MAX = 800;
    private double posicionX;
    private double profundidad;
    private double anchoPantalla;

    // Constructor

    public Submarino() {
        this.posicionX = 0; //posicion temporal antes de que comience la partida 
        this.profundidad = 0;
    }

    // Comportamiento

    /**
     * Posiciona el submarino en su estado inicial para una partida.
     * Lo centra horizontalmente y lo ubica en la profundidad media entre PROF_MIN y PROF_MAX.
     * @param anchoPantalla  ancho del área de juego definido por el GameController
     */
    public void inicializar(double anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
        setPosicionX(anchoPantalla / 2); //400
        double profundidadMedia = (PROF_MAX + PROF_MIN) / 2;
        setProfundidad(profundidadMedia);
    }


    public void moverIzquierda() {
        double x = getPosicionX();
        if (x > 0)  setPosicionX(x - 1);
    }

    public void moverDerecha() {
        double x = getPosicionX();
        if (x < anchoPantalla) setPosicionX(x + 1);
    }

    public void moverArriba() {
        double y = getProfundidad();
        if (y > PROF_MIN) setProfundidad(y - 1);
    }

    public void moverAbajo() {
        double y = getProfundidad();
        if (y < PROF_MAX) setProfundidad(y + 1);
    }

    // Getters

    public double getPosicionX() {
        return posicionX;
    }

    public double getProfundidad() {
        return profundidad;
    }

    // Setters privados — solo para uso interno de los métodos de movimiento

    private void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    private void setProfundidad(double profundidad) {
        this.profundidad = profundidad;
    }
}
