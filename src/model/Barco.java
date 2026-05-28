package model;

public class Barco {
	
	//Variables -> Atributos
	
	protected static final double PROF_SUPERFICIE = 0;
	private int posicionX;
	private String direccion;
	private double velocidad;
	
	//Constructores
	
	public Barco () {
		this.posicionX = 0; 
		this.direccion = "Izquierda"; // --> Le puse esta dirección porque el barco va de izquierda a derecha, sino, a definir la dirección
		this.velocidad = 0; //inicia con 0 la velocidad 
	}
	
	//Comportamiento de negocio -> Acciones del juego
	
	public void inicializar() {
		
	}
	
	
	public void moverIzquierda() {
		
	}
	
	public void moverDerecha() {
		
	}
	
	public void lanzarCarga(double velCaida) {
		//No sé si los parámetros se declaran así xD
	}
	
	public void obtenerPosicionX() {
		//misma duda con getters y setters 
	}
	
	public void haAlcanzadoExtremo() {
		
		//es un boolean 
		
	}
	
	public void obtenerDireccion() {
		
		//misma duda en cuanto a getters y setters
		
	}
	

	
	//Getters y setters 

	public int getPosicionX() {
		return posicionX;
	}

	public void setPosicionX(int posicionX) {
		this.posicionX = posicionX;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public void setVelocidad(double velocidad) {
		this.velocidad = velocidad;
	}
	
}
