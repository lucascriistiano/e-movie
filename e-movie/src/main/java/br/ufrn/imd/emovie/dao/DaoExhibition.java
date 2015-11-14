/**
 * 
 */
package br.ufrn.imd.emovie.dao;

import java.util.List;

import br.ufrn.imd.emovie.model.Exhibition;

/**
 * @author lucas cristiano
 *
 */
public class DaoExhibition extends DaoGeneric<Exhibition> implements IDaoExhibition {

	@SuppressWarnings("unchecked")
	@Override
	public List<Exhibition> listByMovieId(Integer idMovie) {
		return getEntityManager().createQuery("FROM Exhibition WHERE id_movie = " + idMovie).getResultList();
	}
	
}