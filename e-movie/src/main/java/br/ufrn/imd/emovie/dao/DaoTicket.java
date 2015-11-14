/**
 * 
 */
package br.ufrn.imd.emovie.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.ufrn.imd.emovie.model.Ticket;

/**
 * @author lucas cristiano
 *
 */
public class DaoTicket extends DaoGeneric<Ticket> implements IDaoTicket {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Ticket> listByExhibitionId(Integer idExhibition) {
		EntityManager entityManager = getEntityManager();
		List<Ticket> tickets = entityManager.createQuery("FROM Ticket WHERE id_exhibition = " + idExhibition).getResultList();
		entityManager.close();
		return tickets;
	} 
	
	@SuppressWarnings("unchecked")
	public List<Ticket> getAllTokens() {
		EntityManager entityManager = getEntityManager();
		List<Ticket> tokens = entityManager.createNativeQuery("SELECT distinct token FROM ticket;").getResultList();
		entityManager.close();
		return tokens;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Ticket getByToken(String token) {
		EntityManager entityManager = getEntityManager();
		List<Ticket> tickets = entityManager.createQuery("FROM Ticket WHERE token = '" + token + "'").getResultList();
		entityManager.close();
		return tickets.size() > 0 ? tickets.get(0) : null;
	}
	
}