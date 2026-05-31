package config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

	private static JPAUtil instancia;
	private final EntityManagerFactory entityManagerFactory;

	private JPAUtil() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("carreracaballosPU");
	}

	public static JPAUtil getInstancia() {
		if (instancia == null) {
			instancia = new JPAUtil();
		}
		return instancia;
	}

	public EntityManager crearEntityManager() {
		return entityManagerFactory.createEntityManager();
	}
}
