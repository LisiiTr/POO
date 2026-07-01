package controller;

import dao.CorredorDAO;
import dao.ICarreraDAO;
import dao.ICorredorDAO;
import dao.IJugadorDAO;
import dto.CarreraResultadoDTO;
import dto.CorredorDTO;
import dto.JugadorDTO;
import dto.PistaDTO;
import model.Carrera;
import model.Corredor;
import model.ICalculadorPuntaje;
import model.Jugador;
import model.Pista;
import model.estrategias.EstrategiaEquilibrada;
import model.estrategias.EstrategiaResistente;
import model.estrategias.EstrategiaVelocista;

import java.util.List;

public class CarreraController {

	private static final int MAX_CORREDORES = 10;

	private final IJugadorDAO jugadorDAO;
	private final ICarreraDAO carreraDAO;
	private final ICalculadorPuntaje calculadorPuntaje;
	private final ICorredorDAO corredorDAO;

	private Corredor[] corredoresDisponibles;
	private int cantidadCorredores;
	private Pista[] pistasDisponibles;

	public CarreraController(IJugadorDAO jugadorDAO, ICarreraDAO carreraDAO, ICalculadorPuntaje calculadorPuntaje) {
		this.jugadorDAO = jugadorDAO;
		this.carreraDAO = carreraDAO;
		this.calculadorPuntaje = calculadorPuntaje;
		this.corredorDAO = new CorredorDAO();
		this.corredoresDisponibles = new Corredor[MAX_CORREDORES];
		this.cantidadCorredores = 0;
		inicializarCorredores();
		inicializarPistas();
	}

	private Jugador jugador;
	private Corredor[] corredoresCarrera;
	private Carrera carreraActual;

	private void inicializarCorredores() {
		Corredor[] defaults = crearCorredoresDefault();

		try {
			corredorDAO.inicializarSiVacio(defaults, defaults.length);

			List<Corredor> deDB = corredorDAO.listarTodos();
			for (Corredor corredor : deDB) {
				if (cantidadCorredores < MAX_CORREDORES) {
					corredoresDisponibles[cantidadCorredores++] = corredor;
				}
			}
		} catch (Exception e) {
			System.err.println("BD no disponible, usando corredores por defecto: " + e.getMessage());
			for (Corredor corredor : defaults) {
				if (cantidadCorredores < MAX_CORREDORES) {
					corredoresDisponibles[cantidadCorredores++] = corredor;
				}
			}
		}
	}

	private void inicializarPistas() {
		pistasDisponibles = new Pista[] {
			new Pista("Pista Corta",    200),
			new Pista("Pista Clásica",  250),
			new Pista("Pista Larga",    300),
			new Pista("Pista Extrema", 350)
		};
	}

	private Pista elegirPistaAleatoria() {
		int indice = (int)(Math.random() * pistasDisponibles.length);
		return pistasDisponibles[indice];
	}

	private Corredor[] crearCorredoresDefault() {
		return new Corredor[] {
			new Corredor("Rayo",    20, 50, new EstrategiaVelocista()),
			new Corredor("Toro",    13, 80, new EstrategiaResistente()),
			new Corredor("Centro",  15, 60, new EstrategiaEquilibrada()),
			new Corredor("Veloz",   17, 50, new EstrategiaVelocista()),
			new Corredor("Firme",   14, 75, new EstrategiaResistente()),
			new Corredor("Balanza", 17, 55, new EstrategiaEquilibrada())
		};
	}

	public JugadorDTO registrarJugador(String nombre, String mail) {
		jugador = new Jugador(nombre, mail);
		try {
			jugadorDAO.guardar(jugador);
		} catch (Exception e) {
			System.err.println("BD no disponible, jugador solo en memoria: " + e.getMessage());
		}
		return new JugadorDTO(jugador.getNombre(), jugador.getPuntajeAcumulado());
	}

	public CorredorDTO[] prepararCarrera() {
		Corredor[] disponibles = corredoresDisponibles;
		int total = cantidadCorredores;

		Corredor[] copia = new Corredor[total];
		for (int i = 0; i < total; i++) copia[i] = disponibles[i];

		for (int i = 0; i < 4; i++) {
			int j = i + (int)(Math.random() * (total - i));
			Corredor tmp = copia[i]; 
			copia[i] = copia[j]; 
			copia[j] = tmp;
		}

		corredoresCarrera = new Corredor[4];
		for (int i = 0; i < 4; i++) {
			corredoresCarrera[i] = copia[i];
		}

		return toCorredorDTOArray(corredoresCarrera, -1);
	}

	public PistaDTO iniciarCarrera(int indiceCorredorSeleccionado) {
		jugador.seleccionarCorredor(corredoresCarrera[indiceCorredorSeleccionado]);

		Pista pista = elegirPistaAleatoria();
		carreraActual = new Carrera(pista, corredoresCarrera, jugador);

		return new PistaDTO(pista.getNombre(), pista.getDistanciaTotal());
	}

	public boolean simularTurno() {
		carreraActual.simularTurno();
		return carreraActual.estaFinalizada();
	}

	public CorredorDTO[] getCorredoresEstado() {
		int indiceJugador = indiceCorredorDelJugador();
		return toCorredorDTOArray(corredoresCarrera, indiceJugador);
	}

	public CorredorDTO getLider() {
		if (corredoresCarrera == null) return null;
		Corredor lider = null;
		for (Corredor c : corredoresCarrera) {
			if (lider == null || c.getDistanciaRecorrida() > lider.getDistanciaRecorrida()) {
				lider = c;
			}
		}
		
		if (lider == null) {
			return null;
		}
		boolean esDelJugador = false;
		
		if (jugador != null && lider == jugador.getCorredorSeleccionado()){
			esDelJugador = true;
		}
		return new CorredorDTO(lider.getNombre(), lider.getVelocidadBase(), lider.getResistencia(),
				lider.getEstrategiaNombre(), lider.getDistanciaRecorrida(), esDelJugador);
	}

	public CarreraResultadoDTO obtenerResultado() {
		if (carreraActual == null || !carreraActual.estaFinalizada()) return null;

		Corredor caballoDelJugador = jugador.getCorredorSeleccionado();
		int posicion = calculadorPuntaje.calcularPosicion(caballoDelJugador, carreraActual.getGanador(), carreraActual.getSegundo());
		int puntajeGanado = calculadorPuntaje.calcularPuntaje(posicion);

		jugador.actualizarPuntaje(puntajeGanado);
		guardarResultadoEnBD(puntajeGanado);

		String nombreGanador = carreraActual.getGanador().getNombre();
		String nombreSegundo = carreraActual.getSegundo().getNombre();

		return new CarreraResultadoDTO(
				nombreGanador,
				nombreSegundo,
				puntajeGanado,
				posicion,
				caballoDelJugador.getNombre(),
				jugador.getPuntajeAcumulado());
	}

	private void guardarResultadoEnBD(int puntajeGanado) {
		try {
			jugadorDAO.actualizarPuntaje(jugador);
			String nombreGanador = carreraActual.getGanador().getNombre();
			String nombreSegundo = carreraActual.getSegundo().getNombre();
			carreraDAO.guardar(jugador.getId(), nombreGanador, nombreSegundo, puntajeGanado);
		} catch (Exception e) {
			System.err.println("No se pudo guardar en BD: " + e.getMessage());
		}
	}

	private int indiceCorredorDelJugador() {
		if (jugador == null || jugador.getCorredorSeleccionado() == null) return -1;
		for (int i = 0; i < corredoresCarrera.length; i++) {
			if (corredoresCarrera[i] == jugador.getCorredorSeleccionado()) return i;
		}
		return -1;
	}

	private CorredorDTO[] toCorredorDTOArray(Corredor[] corredores, int indiceJugador) {
		CorredorDTO[] dtos = new CorredorDTO[corredores.length];
		for (int i = 0; i < corredores.length; i++) {
			Corredor c = corredores[i];
			dtos[i] = new CorredorDTO(
					c.getNombre(),
					c.getVelocidadBase(),
					c.getResistencia(),
					c.getEstrategiaNombre(),
					c.getDistanciaRecorrida(),
					i == indiceJugador);
		}
		return dtos;
	}
}
