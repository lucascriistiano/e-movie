/**
 * 
 */
package br.ufrn.imd.emovie.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.ufrn.imd.emovie.dao.exception.DaoException;

/**
 * @author joao, lucas cristiano
 *
 */
public class DaoGeneric<T> implements IDaoGeneric<T> {
	
	private Class<T> classEntity;
	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("e-movie");
	
	@SuppressWarnings("unchecked")
	public DaoGeneric() {
		this.classEntity = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public void create(T entity) throws DaoException {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		em.close();
	}

	@Override
	public void update(T entity) throws DaoException {
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		em.merge(entity);
		em.getTransaction().commit();
		em.close();
	}
	
	@Override
	public void delete(Integer id) throws DaoException {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(em.getReference(classEntity, id)); 
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}
	
	@Override
	public void delete(T entity) throws DaoException {
		EntityManager em = factory.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(entity);
			em.getTransaction().commit();
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() throws DaoException {
		EntityManager em = factory.createEntityManager();
		try {
			return em.createQuery("from " + classEntity.getSimpleName()).getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public T getById(Integer id) throws DaoException {
		EntityManager em = factory.createEntityManager();
		try {
			return em.find(classEntity, id);
		} finally {
			em.close();
		}
	}
	
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

}
