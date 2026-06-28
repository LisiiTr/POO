package dto;

public class JugadorDTO {

	private String nombre;
	private double puntajeAcumulado;

	public JugadorDTO(String nombre, double puntajeAcumulado) {
		this.nombre = nombre;
		this.puntajeAcumulado = puntajeAcumulado;
	}

	public String getNombre()           { return nombre; }
	public double getPuntajeAcumulado() { return puntajeAcumulado; }
}
