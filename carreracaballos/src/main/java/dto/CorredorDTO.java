package dto;

public class CorredorDTO {

	private String nombre;
	private double velocidadBase;
	private double resistencia;
	private String estrategiaNombre;
	private double distanciaRecorrida;
	private boolean esDelJugador;

	public CorredorDTO(String nombre, double velocidadBase, double resistencia,
			String estrategiaNombre, double distanciaRecorrida, boolean esDelJugador) {
		this.nombre = nombre;
		this.velocidadBase = velocidadBase;
		this.resistencia = resistencia;
		this.estrategiaNombre = estrategiaNombre;
		this.distanciaRecorrida = distanciaRecorrida;
		this.esDelJugador = esDelJugador;
	}

	public String getNombre()             { return nombre; }
	public double getVelocidadBase()      { return velocidadBase; }
	public double getResistencia()        { return resistencia; }
	public String getEstrategiaNombre()   { return estrategiaNombre; }
	public double getDistanciaRecorrida() { return distanciaRecorrida; }
	public boolean isEsDelJugador()       { return esDelJugador; }
}
