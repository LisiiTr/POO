package model;

import java.util.ArrayList;
import java.util.List;

public class Carrera {

	private Pista pista;
	private List<Corredor> corredores;
	private Jugador jugador;
	private boolean finalizada;
	private Corredor ganador;
	private Corredor segundo;
	private List<Corredor> corredoresFinalizados;

	public Carrera(Pista pista, List<Corredor> corredores, Jugador jugador) {
		this.pista = pista;
		this.corredores = new ArrayList<Corredor>();
		this.corredoresFinalizados = new ArrayList<Corredor>();
		this.jugador = jugador;
		this.finalizada = false;
		this.ganador = null;
		this.segundo = null;
		
		prepararCorredores(corredores);
	}

	public void prepararCorredores(List<Corredor> corredores) {
		this.corredores.clear();
		this.corredoresFinalizados.clear();

		for (Corredor corredor : corredores) {
			corredor.reiniciarEstado();
			this.corredores.add(corredor);
		}

		this.finalizada = false;
		this.ganador = null;
		this.segundo = null;
	}

	public void simularTurno() {
		if (this.finalizada) {
			return;
		}

		for (Corredor corredor : corredores) {
			if (!corredoresFinalizados.contains(corredor)) {

				double distanciaRestante = pista.getDistancia() - corredor.getDistanciaRecorrida();

				if (distanciaRestante < 0) {
					distanciaRestante = 0;
				}

				corredor.avanzar(distanciaRestante);

				if (corredor.llegaAMeta(pista.getDistancia())) {
					registrarLlegada(corredor);
				}
			}
		}

		verificarFinalizacion();
	}

	public void registrarLlegada(Corredor corredor) {
		if (!corredoresFinalizados.contains(corredor)) {
			corredoresFinalizados.add(corredor);
		}
	}

	public void verificarFinalizacion() {
		if (!corredoresFinalizados.isEmpty()) {
			asignarGanadorYSegundo();
			this.finalizada = true;
			
			// TODO: cuando exista CalculadorPuntaje o CarreraController,
			// se debería asignar el puntaje del jugador desde ahí.
		}
	}

	public int obtenerPosicion(Corredor corredor) {
		int posicion = 1;

		for (Corredor otroCorredor : corredores) {
			if (otroCorredor != corredor &&
				otroCorredor.getDistanciaRecorrida() > corredor.getDistanciaRecorrida()) {
				posicion++;
			}
		}

		return posicion;
	}

	public boolean estaFinalizada() {
		return finalizada;
	}

	public Corredor getGanador() {
		return ganador;
	}

	public Corredor getSegundo() {
		return segundo;
	}

	public void asignarGanadorYSegundo() {
		this.ganador = null;
		this.segundo = null;

		for (Corredor corredor : corredores) {
			if (ganador == null ||
				corredor.getDistanciaRecorrida() > ganador.getDistanciaRecorrida()) {
				
				segundo = ganador;
				ganador = corredor;
				
			} else if (segundo == null ||
					   corredor.getDistanciaRecorrida() > segundo.getDistanciaRecorrida()) {
				
				segundo = corredor;
			}
		}
	}

	public Pista getPista() {
		return pista;
	}

	public List<Corredor> getCorredores() {
		return corredores;
	}

	public Jugador getJugador() {
		return jugador;
	}

	public List<Corredor> getCorredoresFinalizados() {
		return corredoresFinalizados;
	}
}