/**
 * 
 */
package br.ufrn.imd.emovie.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author joao
 *
 */
public class DaoMovie implements IDaoMovie {
	
	private EntityManagerFactory factory;
	
	private EntityManager em;
	
	public DaoMovie() {
		factory = Persistence.createEntityManagerFactory("e-movie");
	}

	@Override
	public void create(Object objeto) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.persist(objeto);
		
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void update(Object objeto) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.merge(objeto);
		
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void delete(Object objeto) {
		em = factory.createEntityManager();
		em.getTransaction().begin();
		
		em.remove(objeto);
		
		em.getTransaction().commit();
		em.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAll(Class<?> entity) {
		em = factory.createEntityManager();
		try {
			return em.createQuery("from " + entity.getSimpleName()).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public Object getById(Integer id, Class<?> entity) {
		em = factory.createEntityManager();
		try {
			return em.createQuery("from " + entity.getSimpleName() + " where id = " + id + ";").getResultList();
		} finally {
			em.close();
		}
	}
	
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

}
