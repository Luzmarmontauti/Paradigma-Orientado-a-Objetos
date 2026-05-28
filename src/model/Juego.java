package model;
import java.util.ArrayList;
import java.util.List;

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
	private List<Barco> barcosActivos;
	private List<CargaDeProfundidad> cargasActivas;
	
	
	//Constructor
	
	public Juego() {
		this.vidas = 1; //A definir, no sé cuántas vidas debería tener al empezar el juego
		this.puntaje = 0;
		this.nivel = 1; //Empieza en el nivel 1
		this.porcentajeVida = 100;
		this.estado = "MENU_PRINCIPAL"; //Cuando abrimos el juego, qué es lo primero que debemos ver? ->tenemos que definir esto
		this.puntosAcumExtra = 0; 
		this.barcosGenerados = 0;
		
		//Creamos las listas vacías de los barcos
		
		this.barcosActivos = new ArrayList<Barco>();
		this.cargasActivas = new ArrayList<CargaDeProfundidad>();
		
	}
	
		
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
	

	public int getPorcentajeVida() {
		return porcentajeVida;
	}

	public void setPorcentajeVida(int porcentajeVida) {
		this.porcentajeVida = porcentajeVida;
	}

	public int getBarcosGenerados() {
		return barcosGenerados;
	}

	public void setBarcosGenerados(int barcosGenerados) {
		this.barcosGenerados = barcosGenerados;
	}

	public List<Barco> getBarcosActivos() {
		return barcosActivos;
	}

	public List<CargaDeProfundidad> getCargasActivas() {
		return cargasActivas;
	}
	

	public int getPuntosAcumExtra() {
		return puntosAcumExtra;
	}
	
}
