package model;


/**
 * Submarino controlado por el jugador. Se mueve en las cuatro direcciones
 * dentro de los límites de profundidad y bordes laterales del área de juego.
 */
public class Submarino {

    protected static final double PROF_MIN = 300;
    protected static final double PROF_MAX = 800;
    private final int ANCHO = 50;
    private final int ALTO = 20;
    private double posicionX;
    private double profundidad;
    private double anchoPantalla;
    private double velocidad;
    
    private int puntos;
    private int puntosParaVidaExtra; 
    private int vidas;
    private int saludPorcentaje;     
    private boolean activo;
    
    
   

    // =========================================================
    // CONSTRUCTOR
    // =========================================================
    public Submarino() {
    	this.posicionX = 0;
        this.profundidad = 0;
        this.velocidad = 5.0;            
        this.puntos = 0;
        this.puntosParaVidaExtra = 0;
        this.vidas = 3;                  
        this.saludPorcentaje = 100;      
        this.activo = true;
    }

    // =========================================================
    // INICIALIZACIÓN
    // =========================================================
    public void inicializar(double anchoPantalla) {
        this.anchoPantalla = anchoPantalla;
        setPosicionX((anchoPantalla / 2) - (ANCHO / 2));
        setProfundidad((PROF_MAX + PROF_MIN) / 2);
    }

    
    public void moverIzquierda() {
        if (posicionX > 0) {
            setPosicionX(posicionX - velocidad); // Aumenté a 5 para que no se mueva tan lento
        }
    }

    public void moverDerecha() {
        if (posicionX + ANCHO < anchoPantalla) {
            setPosicionX(posicionX + velocidad);
        }
    }

    public void moverArriba() {
        if (profundidad > PROF_MIN) {
            setProfundidad(profundidad - velocidad);
        }
    }

    public void moverAbajo() {
        if (profundidad + ALTO < PROF_MAX) {
            setProfundidad(profundidad + velocidad);
        }
    }
    
    public void sumarPuntos(int cantidad) {
        this.puntos += cantidad;
        this.puntosParaVidaExtra += cantidad;

        if (this.puntosParaVidaExtra >= 500) {
            this.vidas++;
            this.puntosParaVidaExtra -= 500; 
        }
    }

    public void disminuirEnergia(int porcentaje) {
        this.saludPorcentaje -= porcentaje;
        if (this.saludPorcentaje <= 0) {
            perderUnaVida();
        }
    }

    public void perderUnaVida() {
        if (this.vidas > 0) {
            this.vidas--;
            this.saludPorcentaje = 100; // Si le quedan vidas, revive al 100%
        }
        if (this.vidas <= 0) {
            this.activo = false; // Game Over
        }
    }
    
 
    public double getCentroX() { return this.posicionX + (ANCHO / 2.0); }
    public double getCentroY() { return this.profundidad + (ALTO / 2.0); }

    // =========================================================
    // GETTERS
    // =========================================================
    public double getPosicionX()   { return posicionX; }
    public double getProfundidad() { return profundidad; }
    public int getAncho()          { return ANCHO; }
    public int getAlto()           { return ALTO; }
    public int getVidas()          { return vidas; }
    public int getSaludPorcentaje(){ return saludPorcentaje; }
    public int getPuntos()         { return puntos; }
    public boolean isActivo()      { return activo; }

    // =========================================================
    // SETTERS PRIVADOS
    // =========================================================
    public void setVelocidad(double velocidad) { this.velocidad = velocidad; }
    private void setPosicionX(double posicionX) { this.posicionX = posicionX; }
    private void setProfundidad(double profundidad) { this.profundidad = profundidad; }
    
    // =========================================================
    // CONEXIÓN CON LA VISTA
    // =========================================================
    /*public Vista toView() {
        int posX = (int) this.getPosicionX();
        int posY = (int) this.getProfundidad();
        
        // Ahora usamos las constantes de la clase
        return new Vista(posX, posY, this.ANCHO, this.ALTO);
    }*/
}