package model;

public class Barco {
	
	protected static final double PROF_SUPERFICIE = 0;
	private int posicionX;
	private String direccion;
	private double velocidad;

	public static void main(String[] args) {
		

	}

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
