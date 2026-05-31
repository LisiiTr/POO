package model;

public class CalculadorPuntaje {

	public int calcularPosicion(Corredor caballoDelJugador, Corredor ganador, Corredor segundo) {
		if (caballoDelJugador == ganador)  return 1;
		if (caballoDelJugador == segundo)  return 2;
		return 3;
	}

	public int calcularPuntaje(int posicion) {
		if (posicion == 1) return 100;
		if (posicion == 2) return 50;
		return 10;
	}
}
