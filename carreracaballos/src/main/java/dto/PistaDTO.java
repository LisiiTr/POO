package dto;

public class PistaDTO {

	private String nombre;
	private double distanciaTotal;

	public PistaDTO(String nombre, double distanciaTotal) {
		this.nombre = nombre;
		this.distanciaTotal = distanciaTotal;
	}

	public String getNombre()         { return nombre; }
	public double getDistanciaTotal() { return distanciaTotal; }
}
