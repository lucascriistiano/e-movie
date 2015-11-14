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
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.persist(entity);
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	@Override
	public void update(T entity) throws DaoException {
		EntityManager entityManager = getEntityManager();
		entityManager.getTransaction().begin();
		entityManager.merge(entity);
		entityManager.getTransaction().commit();
		entityManager.close();
	}
	
	@Override
	public void delete(Integer id) throws DaoException {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(entityManager.getReference(classEntity, id)); 
			entityManager.getTransaction().commit();
		} finally {
			entityManager.close();
		}
	}
	
	@Override
	public void delete(T entity) throws DaoException {
		EntityManager entityManager = getEntityManager();
		try {
			entityManager.getTransaction().begin();
			entityManager.remove(entity);
			entityManager.getTransaction().commit();
		} finally {
			entityManager.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() throws DaoException {
		EntityManager entityManager = getEntityManager();
		try {
			return entityManager.createQuery("from " + classEntity.getSimpleName()).getResultList();
		} finally {
			entityManager.close();
		}
	}

	@Override
	public T getById(Integer id) throws DaoException {
		EntityManager entityManager = getEntityManager();
		try {
			return entityManager.find(classEntity, id);
		} finally {
			entityManager.close();
		}
	}
	
	public EntityManager getEntityManager() {
		return factory.createEntityManager();
	}

}
