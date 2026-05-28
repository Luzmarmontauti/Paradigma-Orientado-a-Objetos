package model;

public class Juego {
	
	//Variables
	
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
		
	//Comportamiento de negocio
	
	public void iniciarPartida() {
		
	}
	
	public void procesarExplosion(CargaDeProfundidad carga) {
				
		System.out.println("Procesando carga");
	
	}
	
	public void verificarFinNivel() {
		
	}
	
	public void pasarSiguienteNivel() {
		
	}
	
	public void terminarPartida() {
		
	}
	
	public void getEstado() {
		
	}
	
	public void getSubmarinoX() {
		
	}
	
	public void getSubmarinoY() {
		
	}
	
	public void agregarPuntos(int puntaje) {
		
	}
	
	public void recibirDanio(int porcentajeVida) {
		
	}
	
	public void estaVivo() {
		
		//Es un boolean
		
	}
	
	//En el diagrama está GET PUNTAJE -> no me dejó agregarlo porque está duplicado, así que le cambié el nombre a obtenerPuntaje
	
	public void obtenerPuntaje() {
		
	}
	
	//Lo mismo con vidas -> no me queda claro si ya esto no se declara nuevamente porque está en los getters y setters
	public void obtenerVidas() {
		
	}
	
	public void generarBarco() {
		
	}
	
	public void getVelocidadBarcos() {
		
	}
	
	public void velocidadCargas() {
		
	}
	
	public void verificarColisiones() {
		
	}
	
	public void actualizar() {
		
	}
	
	public void generarSubmarino() {
		
	}
	
	public void moverSubmarino() {
		
	}
	
	public void agregarVida() {
		
	}
	
	
	
	//Getters y Setters


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
	
	//Main -> donde se prueba el juego
	
	public static void main(String[] args) {
		
			
		

	}

}
