package dao;

import model.Corredor;

import java.util.List;

public interface ICorredorDAO {
	void inicializarSiVacio(Corredor[] corredores, int cantidad);
	List<Corredor> listarTodos();
	long contar();
}
