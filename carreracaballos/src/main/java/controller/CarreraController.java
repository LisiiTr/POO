package controller;

import dao.CarreraDAO;
import dao.JugadorDAO;
import dto.CarreraResultadoDTO;
import model.CalculadorPuntaje;
import model.Carrera;
import model.Corredor;
import model.Jugador;
import model.Pista;

public class CarreraController {

	private final JugadorDAO jugadorDAO = new JugadorDAO();
	private final CarreraDAO carreraDAO = new CarreraDAO();
	private final CalculadorPuntaje calculadorPuntaje = new CalculadorPuntaje();

	private Carrera carreraActual;

	public Jugador registrarJugador(String nombre, String mail) {
		Jugador jugador = new Jugador(nombre, mail);
		try {
			jugadorDAO.guardar(jugador);
		} catch (Exception e) {
			System.err.println("BD no disponible, jugador solo en memoria: " + e.getMessage());
		}
		return jugador;
	}

	public void iniciarCarrera(Pista pista, Corredor[] corredoresSeleccionados, Jugador jugador) {
		carreraActual = new Carrera(pista, corredoresSeleccionados, jugador);
	}

	public Pista getPistaActual() {
		return carreraActual.getPista();
	}

	public boolean simularTurno() {
		carreraActual.simularTurno();
		return carreraActual.estaFinalizada();
	}

	public CarreraResultadoDTO obtenerResultado() {
		if (carreraActual == null || !carreraActual.estaFinalizada()) return null;

		Jugador jugador = carreraActual.getJugador();
		Corredor caballoDelJugador = jugador.getCorredorSeleccionado();

		int posicion = calculadorPuntaje.calcularPosicion(caballoDelJugador, carreraActual.getGanador(), carreraActual.getSegundo());
		int puntajeGanado = calculadorPuntaje.calcularPuntaje(posicion);

		jugador.actualizarPuntaje(puntajeGanado);
		guardarResultadoEnBD(jugador, puntajeGanado);

		String nombreGanador;
		if (carreraActual.getGanador() != null) {
			nombreGanador = carreraActual.getGanador().getNombre();
		} else {
			nombreGanador = "-";
		}

		String nombreSegundo;
		if (carreraActual.getSegundo() != null) {
			nombreSegundo = carreraActual.getSegundo().getNombre();
		} else {
			nombreSegundo = "-";
		}

		return new CarreraResultadoDTO(nombreGanador, nombreSegundo, puntajeGanado, posicion);
	}

	private void guardarResultadoEnBD(Jugador jugador, int puntajeGanado) {
		try {
			jugadorDAO.actualizarPuntaje(jugador);

			String nombreGanador;
			if (carreraActual.getGanador() != null) {
				nombreGanador = carreraActual.getGanador().getNombre();
			} else {
				nombreGanador = "-";
			}

			String nombreSegundo;
			if (carreraActual.getSegundo() != null) {
				nombreSegundo = carreraActual.getSegundo().getNombre();
			} else {
				nombreSegundo = "-";
			}

			carreraDAO.guardar(jugador.getId(), nombreGanador, nombreSegundo, puntajeGanado);
		} catch (Exception e) {
			System.err.println("No se pudo guardar en BD: " + e.getMessage());
		}
	}
}
