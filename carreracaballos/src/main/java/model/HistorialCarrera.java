package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historial_carreras")
public class HistorialCarrera {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "jugador_id", nullable = false)
	private Jugador jugador;

	@Column(name = "nombre_ganador", length = 100)
	private String nombreGanador;

	@Column(name = "nombre_segundo", length = 100)
	private String nombreSegundo;

	@Column(name = "puntaje_obtenido")
	private int puntajeObtenido;

	protected HistorialCarrera() {}

	public HistorialCarrera(Jugador jugador, String ganador, String segundo, int puntaje) {
		this.jugador = jugador;
		this.nombreGanador = ganador;
		this.nombreSegundo = segundo;
		this.puntajeObtenido = puntaje;
	}

	public long getId()              { return id; }
	public Jugador getJugador()      { return jugador; }
	public String getNombreGanador() { return nombreGanador; }
	public String getNombreSegundo() { return nombreSegundo; }
	public int getPuntajeObtenido()  { return puntajeObtenido; }
}
