/**
 * 
 */
package br.ufrn.imd.emovie.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.ufrn.imd.emovie.model.User;

/**
 * @author joao, lucas cristiano
 *
 */
public class DaoUser extends DaoGeneric<User> implements IDaoUser {

	@Override
	@SuppressWarnings("unchecked")
	public User checkLogin(User user) {
		EntityManager entityManager = getEntityManager();
		List<User> users = 	entityManager.createQuery("FROM User WHERE email = :email AND password = :senha")
							.setParameter("email", user.getEmail())
							.setParameter("senha", user.getPassword())
							.getResultList();
		entityManager.close();
		return users.size() > 0 ? users.get(0) : null;
	}
	
}
