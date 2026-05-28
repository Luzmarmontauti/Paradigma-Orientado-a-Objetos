package model;

public class CargaDeProfundidad {
	
	//Variables
	
	protected static final double PROF_DET_MIN = 300;
	protected static final double PROF_DET_MAX = 700;
	private double posicionY;
	private double profundidadActual;
	private double profundidadDetonacion;
	private double velocidadCaida;
	
	//Comportamiento de negocio
	
	public void inicializar() {
		
	}
	
	public void caer() {
		
	}
	
	public void debeDetonar() {
		
		//es un boolean
		
	}
	
	public void calcularDistancia(Submarino sub) {
		
	}
	
	
	//Getters y setters


	public double getProfundidadDetonacion() {
		return profundidadDetonacion;
	}

	public void setProfundidadDetonacion(double profundidadDetonacion) {
		this.profundidadDetonacion = profundidadDetonacion;
	}

	public double getVelocidadCaida() {
		return velocidadCaida;
	}

	public void setVelocidadCaida(double velocidadCaida) {
		this.velocidadCaida = velocidadCaida;
	}

	public double getPosicionY() {
		return posicionY;
	}

	public void setPosicionY(double posicionY) {
		this.posicionY = posicionY;
	}

	public double getProfundidadActual() {
		return profundidadActual;
	}

	public void setProfundidadActual(double profundidadActual) {
		this.profundidadActual = profundidadActual;
	}
	
	//Main -> donde se prueba todo
	
	public static void main(String[] args) {

	}

}
