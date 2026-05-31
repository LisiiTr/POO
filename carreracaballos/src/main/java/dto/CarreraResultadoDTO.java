package dto;

public class CarreraResultadoDTO {

	private final String nombreGanador;
	private final String nombreSegundo;
	private final int puntajeObtenido;
	private final int posicionJugador;

	public CarreraResultadoDTO(String nombreGanador, String nombreSegundo, int puntajeObtenido, int posicionJugador) {
		this.nombreGanador = nombreGanador;
		this.nombreSegundo = nombreSegundo;
		this.puntajeObtenido = puntajeObtenido;
		this.posicionJugador = posicionJugador;
	}

	public String getNombreGanador()  { return nombreGanador; }
	public String getNombreSegundo()  { return nombreSegundo; }
	public int getPuntajeObtenido()   { return puntajeObtenido; }
	public int getPosicionJugador()   { return posicionJugador; }
}
