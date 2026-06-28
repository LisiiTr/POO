package dao;

import config.JPAUtil;
import model.Jugador;

import jakarta.persistence.EntityManager;
import java.util.List;

public class JugadorDAO implements IJugadorDAO {

	public void guardar(Jugador jugador) {
		EntityManager em = JPAUtil.getInstancia().crearEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(jugador);
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	public void actualizarPuntaje(Jugador jugador) {
		EntityManager em = JPAUtil.getInstancia().crearEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(jugador);
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}
}
