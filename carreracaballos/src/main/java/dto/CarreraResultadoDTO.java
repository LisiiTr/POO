package dto;

public class CarreraResultadoDTO {

	private String nombreGanador;
	private String nombreSegundo;
	private int puntajeObtenido;
	private int posicionJugador;
	private String nombreCorredorJugador;
	private double puntajeAcumuladoJugador;

	public CarreraResultadoDTO(String nombreGanador, String nombreSegundo, int puntajeObtenido,
			int posicionJugador, String nombreCorredorJugador, double puntajeAcumuladoJugador) {
		this.nombreGanador = nombreGanador;
		this.nombreSegundo = nombreSegundo;
		this.puntajeObtenido = puntajeObtenido;
		this.posicionJugador = posicionJugador;
		this.nombreCorredorJugador = nombreCorredorJugador;
		this.puntajeAcumuladoJugador = puntajeAcumuladoJugador;
	}

	public String getNombreGanador()           { return nombreGanador; }
	public String getNombreSegundo()           { return nombreSegundo; }
	public int getPuntajeObtenido()            { return puntajeObtenido; }
	public int getPosicionJugador()            { return posicionJugador; }
	public String getNombreCorredorJugador()   { return nombreCorredorJugador; }
	public double getPuntajeAcumuladoJugador() { return puntajeAcumuladoJugador; }
}
