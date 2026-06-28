package model;

import dao.CorredorDAO;
import dao.ICorredorDAO;
import model.estrategias.EstrategiaEquilibrada;
import model.estrategias.EstrategiaResistente;
import model.estrategias.EstrategiaVelocista;

import java.util.List;

public class SistemaCarreras {

	private static int MAX_CORREDORES = 10;
	private static SistemaCarreras instancia;

	private Corredor[] corredoresDisponibles;
	private int cantidadCorredores;

	private Pista[] pistasDisponibles;

	private final ICorredorDAO corredorDAO;

	private SistemaCarreras(ICorredorDAO corredorDAO) {
		this.corredorDAO = corredorDAO;
		this.corredoresDisponibles = new Corredor[MAX_CORREDORES];
		this.cantidadCorredores = 0;
		inicializarCorredores();
		inicializarPistas();
	}

	public static SistemaCarreras getInstancia() {
		if (instancia == null) {
			instancia = new SistemaCarreras(new CorredorDAO());
		}
		return instancia;
	}

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
			new Pista("Pista Corta",    300),
			new Pista("Pista Clásica",  400),
			new Pista("Pista Larga",    200),
			new Pista("Pista Extrema", 100)
		};
	}

	public Pista elegirPistaAleatoria() {
		int indice = (int)(Math.random() * pistasDisponibles.length);
		return pistasDisponibles[indice];
	}

	private Corredor[] crearCorredoresDefault() {
		return new Corredor[] {
			new Corredor("Rayo",    20, 40, new EstrategiaVelocista()),
			new Corredor("Toro",    13, 80, new EstrategiaResistente()),
			new Corredor("Centro",  15, 60, new EstrategiaEquilibrada()),
			new Corredor("Veloz",   17, 50, new EstrategiaVelocista()),
			new Corredor("Firme",   14, 75, new EstrategiaResistente()),
			new Corredor("Balanza", 17, 55, new EstrategiaEquilibrada())
		};
	}

	public Corredor[] getCorredoresDisponibles() { return corredoresDisponibles; }
	public int getCantidadCorredores()           { return cantidadCorredores; }
	public Pista[] getPistasDisponibles()        { return pistasDisponibles; }
}
