package dao;

import config.JPAUtil;
import model.Corredor;

import jakarta.persistence.EntityManager;
import java.util.List;

public class CorredorDAO {

	public List<Corredor> listarTodos() {
		EntityManager em = JPAUtil.getInstancia().crearEntityManager();
		try {
			return em.createQuery("SELECT c FROM Corredor c ORDER BY c.id", Corredor.class)
					 .getResultList();
		} finally {
			em.close();
		}
	}

	public long contar() {
		EntityManager em = JPAUtil.getInstancia().crearEntityManager();
		try {
			return em.createQuery("SELECT COUNT(c) FROM Corredor c", Long.class)
					 .getSingleResult();
		} finally {
			em.close();
		}
	}

	public void inicializarSiVacio(Corredor[] corredoresDefault, int cantidad) {
		if (contar() > 0) return;

		EntityManager em = JPAUtil.getInstancia().crearEntityManager();
		try {
			em.getTransaction().begin();
			for (int i = 0; i < cantidad; i++) {
				em.persist(corredoresDefault[i]);
			}
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			if (em.getTransaction().isActive()) em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}
	}
}
