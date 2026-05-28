package model;

public class Juego {
	
	protected static final int TOTAL_BARCOS = 12;
	protected static final int MAX_SIMULTANEOS = 3;
	protected static final double INC_VEL = 0.2;
	private int vidas;
	private int puntaje;
	private int nivel;
	private int porcentajeVida;
	private String estado;
	private int puntosAcumExtra;
	private int barcosGenerados;
	

	public static void main(String[] args) {
		
			
		

	}


	public int getVidas() {
		return vidas;
	}


	public void setVidas(int vidas) {
		this.vidas = vidas;
	}


	public int getPuntaje() {
		return puntaje;
	}


	public void setPuntaje(int puntaje) {
		this.puntaje = puntaje;
	}


	public int getNivel() {
		return nivel;
	}


	public void setNivel(int nivel) {
		this.nivel = nivel;
	}

}
