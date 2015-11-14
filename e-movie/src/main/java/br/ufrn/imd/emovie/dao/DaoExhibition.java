/**
 * 
 */
package br.ufrn.imd.emovie.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.ufrn.imd.emovie.model.Exhibition;

/**
 * @author lucas cristiano
 *
 */
public class DaoExhibition extends DaoGeneric<Exhibition> implements IDaoExhibition {

	@SuppressWarnings("unchecked")
	@Override
	public List<Exhibition> listByMovieId(Integer idMovie) {
		EntityManager entityManager = getEntityManager();
		List<Exhibition> exhibitions = entityManager.createQuery("FROM Exhibition WHERE id_movie = " + idMovie).getResultList();
		entityManager.close();
		return exhibitions;
	}
	
}