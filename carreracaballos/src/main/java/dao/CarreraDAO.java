package dao;

import config.JPAUtil;
import model.HistorialCarrera;
import model.Jugador;

import jakarta.persistence.EntityManager;

public class CarreraDAO {

	public void guardar(long jugadorId, String nombreGanador, String nombreSegundo, int puntaje) {
		EntityManager em = JPAUtil.getInstancia().crearEntityManager();
		try {
			em.getTransaction().begin();
			Jugador jugador = em.getReference(Jugador.class, jugadorId);
			em.persist(new HistorialCarrera(jugador, nombreGanador, nombreSegundo, puntaje));
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}
}
