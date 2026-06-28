package model;

public interface ICalculadorPuntaje {
	int calcularPosicion(Corredor caballoDelJugador, Corredor ganador, Corredor segundo);
	int calcularPuntaje(int posicion);
}
