package dao;

import model.Jugador;

public interface IJugadorDAO {
	void guardar(Jugador jugador);
	void actualizarPuntaje(Jugador jugador);
}
