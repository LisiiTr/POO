package model;

public class Corredor {
	
	private double distanciaRecorrida;
	private String nombre;
	private double velocidadBase;
	private double energiaActual; 
	private double resistencia;

	// Cuando exista la clase EstrategiaAvance, se puede agregar:
	// private EstrategiaAvance estrategiaAvance;
	
	public Corredor() {
		this.nombre = "";
		this.velocidadBase = 0;
		this.resistencia = 0;
		this.energiaActual = 0;
		this.distanciaRecorrida = 0;
	}
	
	public Corredor(String nombre, double velocidadBase, double resistencia) {
		this.nombre = nombre;
		this.velocidadBase = velocidadBase;
		this.resistencia = resistencia;
		this.energiaActual = resistencia;
		this.distanciaRecorrida = 0;
	}
	
	public void avanzar(double distanciaRestante) {
		double metrosAvanzados = velocidadBase;
		
		if (energiaActual <= 0) {
			metrosAvanzados = velocidadBase / 2;
		}
		
		if (metrosAvanzados > distanciaRestante) {
			metrosAvanzados = distanciaRestante;
		}
		
		double gastoEnergia = 1;
		
		actualizarDistanciaYEnergia(metrosAvanzados, gastoEnergia);
	}
	
	public void reiniciarEstado() {
		this.distanciaRecorrida = 0;
		this.energiaActual = resistencia;
	}
	
	public boolean llegaAMeta(double distanciaTotal) {
		return distanciaRecorrida >= distanciaTotal;
	}
	
	public void actualizarDistanciaYEnergia(double metros, double gasto) {
		this.distanciaRecorrida = this.distanciaRecorrida + metros;
		this.energiaActual = this.energiaActual - gasto;
		
		if (this.energiaActual < 0) {
			this.energiaActual = 0;
		}
	}

	public double getDistanciaRecorrida() {
		return distanciaRecorrida;
	}

	public String getNombre() {
		return nombre;
	}

	public double getEnergiaActual() {
		return energiaActual;
	}

	public double getVelocidadBase() {
		return velocidadBase;
	}

	public double getResistencia() {
		return resistencia;
	}
}
