package model;

public class Carrera {

	private static  int CANTIDAD_CORREDORES = 4;

	private Pista pista;
	private Corredor[] corredores;
	private Jugador jugador;
	private boolean finalizada;
	private Corredor ganador;
	private Corredor segundo;

	public Carrera(Pista pista, Corredor[] corredoresSeleccionados, Jugador jugador) {
		this.pista = pista;
		this.jugador = jugador;
		this.corredores = new Corredor[CANTIDAD_CORREDORES];

		for (int i = 0; i < CANTIDAD_CORREDORES; i++) {
			corredoresSeleccionados[i].reiniciarEstado();
			this.corredores[i] = corredoresSeleccionados[i];
		}
	}

	public void simularTurno() {
		if (finalizada) return;

		for (Corredor corredor : corredores) {
			double distanciaRestante = pista.getDistanciaTotal() - corredor.getDistanciaRecorrida();
			if (distanciaRestante > 0) {
				corredor.avanzar(distanciaRestante);
			}
		}

		for (Corredor corredor : corredores) {
			if (corredor.llegaAMeta(pista.getDistanciaTotal())) {
				finalizada = true;
				determinarPodio();
				return;
			}
		}
	}

	private void determinarPodio() {
		for (Corredor corredor : corredores) {
			if (ganador == null || corredor.getDistanciaRecorrida() > ganador.getDistanciaRecorrida()) {
				segundo = ganador;
				ganador = corredor;
			} else if (segundo == null || corredor.getDistanciaRecorrida() > segundo.getDistanciaRecorrida()) {
				segundo = corredor;
			}
		}
	}

	public boolean estaFinalizada()      { return finalizada; }
	public Corredor getGanador()         { return ganador; }
	public Corredor getSegundo()         { return segundo; }
	public Pista getPista()              { return pista; }
	public Corredor[] getCorredores()    { return corredores; }
	public int getCantidadCorredores()   { return CANTIDAD_CORREDORES; }
	public Jugador getJugador()          { return jugador; }
}
